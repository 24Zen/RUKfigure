import React, { useEffect, useState } from "react";

const API_BASE = "http://localhost:8080";
const ADMIN_URL = `${API_BASE}/api/admins`;

function AdminManagement() {
  const [admins, setAdmins] = useState([]);
  const [loading, setLoading] = useState(false);
  const [editingAdmin, setEditingAdmin] = useState(null);
  const [formData, setFormData] = useState({ username: "", email: "" });
  const [error, setError] = useState("");

  // ดึงข้อมูล Admin จาก Spring Boot API
  useEffect(() => {
    const fetchAdmins = async () => {
      setLoading(true);
      setError("");
      try {
        const res = await fetch(ADMIN_URL);
        if (!res.ok) throw new Error(`Fetch failed: ${res.status}`);
        const data = await res.json();
        setAdmins(data);
      } catch (err) {
        console.error("Error fetching admins:", err);
        setError("โหลดรายการแอดมินไม่สำเร็จ");
      } finally {
        setLoading(false);
      }
    };
    fetchAdmins();
  }, []);

  // ลบ Admin
  const handleDelete = async (id) => {
    if (!window.confirm("ยืนยันลบแอดมินคนนี้?")) return;
    try {
      const res = await fetch(`${ADMIN_URL}/${id}`, { method: "DELETE" });
      if (!res.ok && res.status !== 204) {
        throw new Error(`Delete failed: ${res.status}`);
      }
      setAdmins((prev) => prev.filter((admin) => admin.id !== id));
    } catch (err) {
      console.error("Error deleting admin:", err);
      alert("ลบไม่สำเร็จ");
    }
  };

  // เริ่มแก้ไข
  const handleEdit = (admin) => {
    setEditingAdmin(admin.id);
    setFormData({ username: admin.username ?? "", email: admin.email ?? "" });
  };

  // บันทึกการแก้ไข
  const handleSave = async (id) => {
    try {
      const res = await fetch(`${ADMIN_URL}/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(formData),
      });
      if (!res.ok) throw new Error(`Update failed: ${res.status}`);
      const updatedAdmin = await res.json();
      setAdmins((prev) => prev.map((a) => (a.id === id ? updatedAdmin : a)));
      setEditingAdmin(null);
    } catch (err) {
      console.error("Error updating admin:", err);
      alert("บันทึกไม่สำเร็จ");
    }
  };

  return (
    <div style={{ padding: "20px" }}>
      <h2>Admin Management</h2>

      {error && (
        <div style={{ marginBottom: 12, color: "crimson" }}>
          {error}
        </div>
      )}

      <table
        border="1"
        cellPadding="10"
        style={{ borderCollapse: "collapse", width: "100%" }}
      >
        <thead>
          <tr>
            <th style={{ width: 80 }}>ID</th>
            <th>Username</th>
            <th>Email</th>
            <th style={{ width: 220 }}>Actions</th>
          </tr>
        </thead>
        <tbody>
          {loading ? (
            <tr>
              <td colSpan="4">Loading…</td>
            </tr>
          ) : admins.length === 0 ? (
            <tr>
              <td colSpan="4">ไม่มีข้อมูล</td>
            </tr>
          ) : (
            admins.map((admin) => (
              <tr key={admin.id}>
                <td>{admin.id}</td>
                <td>
                  {editingAdmin === admin.id ? (
                    <input
                      value={formData.username}
                      onChange={(e) =>
                        setFormData({ ...formData, username: e.target.value })
                      }
                    />
                  ) : (
                    admin.username
                  )}
                </td>
                <td>
                  {editingAdmin === admin.id ? (
                    <input
                      value={formData.email}
                      onChange={(e) =>
                        setFormData({ ...formData, email: e.target.value })
                      }
                    />
                  ) : (
                    admin.email
                  )}
                </td>
                <td>
                  {editingAdmin === admin.id ? (
                    <>
                      <button onClick={() => handleSave(admin.id)}>
                        Save
                      </button>{" "}
                      <button onClick={() => setEditingAdmin(null)}>
                        Cancel
                      </button>
                    </>
                  ) : (
                    <>
                      <button onClick={() => handleEdit(admin)}>Edit</button>{" "}
                      <button onClick={() => handleDelete(admin.id)}>
                        Delete
                      </button>
                    </>
                  )}
                </td>
              </tr>
            ))
          )}
        </tbody>
      </table>
    </div>
  );
}

export default AdminManagement;
