import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.tsx'

createRoot(document.getElementById('root')!).render(
  // เอา StrictMode ออกระหว่าง dev จะช่วยให้ useEffect ไม่ยิงซ้ำ
  <App />
)
// import React from "react";   // ลบ