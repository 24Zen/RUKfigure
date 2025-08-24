import React, { useEffect, useState } from "react";
import { Table, Button, Modal, Form, Input, message, Popconfirm } from "antd";
import axios from "axios";

interface Admin {
  id: number;
  username: string;
  email: string;
  password: string;
}

const AdminTable: React.FC = () => {
  const [admins, setAdmins] = useState<Admin[]>([]);
  const [loading, setLoading] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [editingAdmin, setEditingAdmin] = useState<Admin | null>(null);

  const [form] = Form.useForm();

  const fetchAdmins = async () => {
    setLoading(true);
    try {
      const res = await axios.get("http://localhost:8080/api/admins");
      setAdmins(res.data);
    } catch (err) {
      message.error("Failed to fetch admins");
    }
    setLoading(false);
  };

  useEffect(() => {
    fetchAdmins();
  }, []);

  const handleAdd = () => {
    setEditingAdmin(null);
    form.resetFields();
    setIsModalOpen(true);
  };

  const handleEdit = (admin: Admin) => {
    setEditingAdmin(admin);
    form.setFieldsValue(admin);
    setIsModalOpen(true);
  };

  const handleDelete = async (id: number) => {
    try {
      await axios.delete(`http://localhost:8080/api/admins/${id}`);
      message.success("Admin deleted");
      fetchAdmins();
    } catch (err) {
      message.error("Failed to delete admin");
    }
  };

  const handleSubmit = async () => {
    try {
      const values = await form.validateFields();
      if (editingAdmin) {
        await axios.put(
          `http://localhost:8080/api/admins/${editingAdmin.id}`,
          values
        );
        message.success("Admin updated");
      } else {
        await axios.post("http://localhost:8080/api/admins", values);
        message.success("Admin added");
      }
      setIsModalOpen(false);
      fetchAdmins();
    } catch (err) {
      message.error("Submit failed");
    }
  };

  const columns = [
    { title: "ID", dataIndex: "id", key: "id" },
    { title: "Username", dataIndex: "username", key: "username" },
    { title: "Email", dataIndex: "email", key: "email" },
    { title: "Password", dataIndex: "password", key: "password" },
    {
      title: "Actions",
      key: "actions",
      render: (_: any, record: Admin) => (
        <>
          <Button type="link" onClick={() => handleEdit(record)}>
            Edit
          </Button>
          <Popconfirm
            title="Are you sure to delete?"
            onConfirm={() => handleDelete(record.id)}
          >
            <Button type="link" danger>
              Delete
            </Button>
          </Popconfirm>
        </>
      ),
    },
  ];

  return (
    <>
      <Button type="primary" style={{ marginBottom: 16 }} onClick={handleAdd}>
        Add Admin
      </Button>
      <Table
        rowKey="id"
        columns={columns}
        dataSource={admins}
        loading={loading}
      />

      <Modal
        title={editingAdmin ? "Edit Admin" : "Add Admin"}
        open={isModalOpen}
        onOk={handleSubmit}
        onCancel={() => setIsModalOpen(false)}
      >
        <Form form={form} layout="vertical">
          <Form.Item
            name="username"
            label="Username"
            rules={[{ required: true, message: "Please input username!" }]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            name="email"
            label="Email"
            rules={[{ required: true, message: "Please input email!" }]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            name="password"
            label="Password"
            rules={[{ required: true, message: "Please input password!" }]}
          >
            <Input.Password />
          </Form.Item>
        </Form>
      </Modal>
    </>
  );
};

export default AdminTable;
