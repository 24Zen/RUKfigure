// src/components/AdminDashboard.tsx
import { useState, useEffect, useCallback } from "react";
import { Card, Row, Col, Statistic, message } from "antd";
import { AxiosError } from "axios";
import http from "../lib/http";
import AdminTable from "./AdminTable";
import ChartStats from "./ChartStats";

function AdminDashboard() {
  const [adminCount, setAdminCount] = useState(0);
  const [comicCount, setComicCount] = useState(0);
  const [figureCount, setFigureCount] = useState(0);
  const [loading, setLoading] = useState(true);

  const fetchStats = useCallback(async () => {
    setLoading(true);
    try {
      const [adminsRes, comicsRes, figuresRes] = await Promise.all([
        http.get("/api/admins"),
        http.get("/api/comics"),
        http.get("/api/figures"),
      ]);
      setAdminCount(adminsRes.data.length ?? 0);
      setComicCount(comicsRes.data.length ?? 0);
      setFigureCount(figuresRes.data.length ?? 0);
    } catch (e: unknown) {
      const err = e as AxiosError<{ message?: string }>;
      console.error(err);
      message.error(err.response?.data?.message ?? "Failed to fetch stats");
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    fetchStats();
  }, [fetchStats]);

  return (
    <div style={{ padding: 24 }}>
      <Row gutter={16} style={{ marginBottom: 24 }}>
        <Col span={8}>
          <Card>
            <Statistic title="Total Admins" value={adminCount} loading={loading} />
          </Card>
        </Col>
        <Col span={8}>
          <Card>
            <Statistic title="Total Comics" value={comicCount} loading={loading} />
          </Card>
        </Col>
        <Col span={8}>
          <Card>
            <Statistic title="Total Figures" value={figureCount} loading={loading} />
          </Card>
        </Col>
      </Row>

      <ChartStats
        data={[
          { name: "Admins", value: adminCount },
          { name: "Comics", value: comicCount },
          { name: "Figures", value: figureCount },
        ]}
      />

      <Card title="Admin Management">
        <AdminTable onDataChange={fetchStats} />
      </Card>
    </div>
  );
}

export default AdminDashboard;
