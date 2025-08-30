import React from "react";
import { Card } from "antd";
import { BarChart, Bar, XAxis, YAxis, Tooltip, ResponsiveContainer } from "recharts";

interface ChartStatsProps {
  data: { name: string; value: number }[];
}

const ChartStats: React.FC<ChartStatsProps> = ({ data }) => {
  return (
    <Card title="Overview Statistics" style={{ marginBottom: 24 }}>
      <ResponsiveContainer width="100%" height={300}>
        <BarChart data={data}>
          <XAxis dataKey="name" />
          <YAxis />
          <Tooltip />
          <Bar dataKey="value" fill="#1890ff" />
        </BarChart>
      </ResponsiveContainer>
    </Card>
  );
};

export default ChartStats;
