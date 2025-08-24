import React from "react";
import AdminTable from "./components/AdminTable";
import "antd/dist/reset.css";
import './App.css';

function App() {
  return (
    <div style={{ padding: 24 }}>
      <h1>Admin Management</h1>
      <AdminTable />
    </div>
  );
}


export default App;
