import { useState } from "react";
import { Card, Form, Input, Button, message } from "antd";
import http from "../lib/http";

export default function Login() {
  const [loading, setLoading] = useState(false);

  const onFinish = async (values: { username: string; password: string }) => {
    setLoading(true);
    try {
      const res = await http.post("/api/auth/login", values);
      localStorage.setItem("token", res.data.token);
      message.success(`Welcome ${res.data.username}`);
      location.href = "/";
    } catch (e: any) {
      message.error(e?.response?.data?.message || "Invalid credentials");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ minHeight: "100vh", display: "grid", placeItems: "center" }}>
      <Card title="Admin Login" style={{ width: 360 }}>
        <Form layout="vertical" onFinish={onFinish}>
          <Form.Item name="username" label="Email or Username" rules={[{ required: true }]}>
            <Input autoFocus />
          </Form.Item>
          <Form.Item name="password" label="Password" rules={[{ required: true }]}>
            <Input.Password />
          </Form.Item>
          <Button type="primary" htmlType="submit" block loading={loading}>
            Log in
          </Button>
        </Form>
      </Card>
    </div>
  );
}
