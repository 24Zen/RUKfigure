import AdminDashboard from "./components/AdminDashboard";
import "antd/dist/reset.css";
import Login from "./pages/Login";

function App() {
  const token = localStorage.getItem("token");
  if (!token) return <Login />;

  return (
    <div>
      <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center" }}>
        <h1>RUKfigure Admin Dashboard</h1>
        <button
          onClick={() => {
            localStorage.removeItem("token");
            location.reload();
          }}
        >
          Logout
        </button>
      </div>
      <AdminDashboard />
    </div>
  );
}

export default App;
