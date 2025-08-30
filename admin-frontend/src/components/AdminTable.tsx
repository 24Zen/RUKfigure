import { Modal } from "antd";
import React, { useCallback, useEffect, useMemo, useState } from "react";
import { Table, Button, Popconfirm, Form, Input, message, Space } from "antd";
import type { ColumnsType } from "antd/es/table";
import type { AxiosError } from "axios";
import http from "../lib/http";

interface Admin {
  id: number;
  username: string;
  email: string;
  password?: string;
}

interface AdminTableProps {
  onDataChange?: () => void; // ให้ Dashboard อัปเดตสถิติ
}

const ADMIN_URL = `/api/admins`;

const AdminTable: React.FC<AdminTableProps> = ({ onDataChange }) => {
  const [admins, setAdmins] = useState<Admin[]>([]);
  const [loading, setLoading] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [editingAdmin, setEditingAdmin] = useState<Admin | null>(null);
  const [form] = Form.useForm();

  // โหลดตาราง
  const fetchAdmins = useCallback(async () => {
    setLoading(true);
    try {
      const res = await http.get<Admin[]>(ADMIN_URL);
      setAdmins(res.data);
      onDataChange?.(); // อัปเดตสถิติด้านบน
    } catch (e: unknown) {
      const err = e as AxiosError<{ message?: string }>;
      console.error(err);
      message.error(err.response?.data?.message ?? "Failed to fetch admins");
    } finally {
      setLoading(false);
    }
  }, [onDataChange]);

  useEffect(() => {
    fetchAdmins();
  }, [fetchAdmins]);

  // เปิด/ปิด Modal เพิ่ม/แก้ไข
  const openModal = useCallback(
    (admin?: Admin) => {
      if (admin) {
        setEditingAdmin(admin);
        form.setFieldsValue({
          username: admin.username,
          email: admin.email,
          password: "",
        });
      } else {
        setEditingAdmin(null);
        form.resetFields();
      }
      setIsModalOpen(true);
    },
    [form]
  );

  // บันทึก Add/Edit
  const handleOk = useCallback(async () => {
    try {
      const values = await form.validateFields(); // { username, email, password? }

      if (editingAdmin) {
        // ไม่ส่ง password ถ้าไม่ได้กรอก (กันเผลอเซ็ตเป็นค่าว่าง)
        const payload: Partial<Admin> = {
          username: values.username,
          email: values.email,
        };
        if (values.password?.trim()) payload.password = values.password;

        const res = await http.put(`${ADMIN_URL}/${editingAdmin.id}`, payload);
        if (res.status < 200 || res.status >= 300) throw new Error(`Unexpected status ${res.status}`);
        message.success("Admin updated");
      } else {
        const res = await http.post(ADMIN_URL, values);
        if (res.status < 200 || res.status >= 300) throw new Error(`Unexpected status ${res.status}`);
        message.success("Admin added");
      }

      setIsModalOpen(false);
      await fetchAdmins();
    } catch (e: unknown) {
      // validation error จาก antd form → ไม่ต้องโชว์ซ้ำ
      if (typeof e === "object" && e && "errorFields" in (e as Record<string, unknown>)) return;

      const err = e as AxiosError<{ message?: string }>;
      console.error(err);
      const code = err.response?.status;
      if (code === 409) message.error("Username ซ้ำ");
      else message.error(err.response?.data?.message ?? "Operation failed");
    }
  }, [editingAdmin, fetchAdmins, form]);

  // ลบด้วย Popconfirm (กันเคส modal โผล่ไม่ขึ้น)
  const handleDelete = useCallback(
    async (id: number) => {
      try {
        const res = await http.delete(`${ADMIN_URL}/${id}`);
        if (res.status !== 200 && res.status !== 204) throw new Error(`Unexpected status ${res.status}`);

        // ตัดออกทันทีให้เห็นผล
        setAdmins(prev => prev.filter(a => a.id !== id));

        // sync กับเซิร์ฟเวอร์ + อัปเดตสถิติ
        await fetchAdmins();
        onDataChange?.();

        message.success("Admin deleted");
      } catch (e: unknown) {
        const err = e as AxiosError<{ message?: string }>;
        console.error(err);
        const code = err.response?.status;
        if (code === 404) message.error("ไม่พบรายการ (อาจถูกลบไปแล้ว)");
        else message.error(err.response?.data?.message ?? "Delete failed");
      }
    },
    [fetchAdmins, onDataChange]
  );

  // คอลัมน์ตาราง
  const columns: ColumnsType<Admin> = useMemo(
    () => [
      { title: "ID", dataIndex: "id", key: "id", width: 80 },
      {
        title: "Username",
        dataIndex: "username",
        key: "username",
        sorter: (a, b) => a.username.localeCompare(b.username),
      },
      {
        title: "Email",
        dataIndex: "email",
        key: "email",
        sorter: (a, b) => a.email.localeCompare(b.email),
      },
      {
        title: "Actions",
        key: "actions",
        width: 200,
        render: (_, record) => (
          <Space>
            <Button onClick={() => openModal(record)}>Edit</Button>
            <Popconfirm
              title={`Delete admin id ${record.id}?`}
              description="This action cannot be undone."
              okText="Yes"
              cancelText="No"
              onConfirm={() => handleDelete(record.id)}
            >
              <Button danger>Delete</Button>
            </Popconfirm>
          </Space>
        ),
      },
    ],
    [handleDelete, openModal]
  );

  return (
    <>
      <Button type="primary" onClick={() => openModal()} style={{ marginBottom: 16 }}>
        Add Admin
      </Button>

      <Table
        dataSource={admins}
        rowKey="id"
        columns={columns}
        loading={loading}
        pagination={{ pageSize: 5 }}
        scroll={{ x: 640 }}
      />

      <Form form={form} layout="vertical" preserve={false}>
        <Form.Item noStyle>
          {/* ใช้ Modal ของ antd ผ่าน props open/ok/cancel จาก state */}
        </Form.Item>
      </Form>

      {/* Modal แยกไว้ด้านล่างให้ชัดเจน */}
      <Modal
        title={editingAdmin ? "Edit Admin" : "Add Admin"}
        open={isModalOpen}
        onOk={handleOk}
        onCancel={() => setIsModalOpen(false)}
        destroyOnClose
        width={520}
      >
        <Form form={form} layout="vertical" preserve={false}>
          <Form.Item
            name="username"
            label="Username"
            rules={[{ required: true, message: "Username is required" }]}
          >
            <Input autoComplete="off" />
          </Form.Item>

          <Form.Item
            name="password"
            label={editingAdmin ? "Password (leave blank = keep old)" : "Password"}
            rules={editingAdmin ? [] : [{ required: true, message: "Password is required" }]}
          >
            <Input.Password autoComplete="new-password" />
          </Form.Item>

          <Form.Item
            name="email"
            label="Email"
            rules={[
              { required: true, message: "Email is required" },
              { type: "email", message: "Invalid email" },
            ]}
          >
            <Input autoComplete="off" />
          </Form.Item>
        </Form>
      </Modal>
    </>
  );
};

export default AdminTable;
