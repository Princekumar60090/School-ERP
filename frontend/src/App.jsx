import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import ProtectedRoute from './components/ProtectedRoute';
import Layout from './components/Layout';
import Login from './pages/Login';
import Register from './pages/Register';
import ForgotPassword from './pages/ForgotPassword';
import Dashboard from './pages/Dashboard';
import StudentList from './pages/students/StudentList';

// Placeholder components for other pages
const Students = () => <div className="card"><h1>Students Module</h1><p>Full CRUD for Students will be implemented here.</p></div>;
const Teachers = () => <div className="card"><h1>Teachers Module</h1><p>Full CRUD for Teachers will be implemented here.</p></div>;
const Staff = () => <div className="card"><h1>Staff Module</h1><p>Full CRUD for Staff will be implemented here.</p></div>;
const Classes = () => <div className="card"><h1>Classes Module</h1><p>Full CRUD for Classes will be implemented here.</p></div>;
const Attendance = () => <div className="card"><h1>Attendance Module</h1><p>Attendance tracking system.</p></div>;
const Fees = () => <div className="card"><h1>Fees Module</h1><p>Fee structure and payments.</p></div>;
const Library = () => <div className="card"><h1>Library Module</h1><p>Library management system.</p></div>;

function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/forgot-password" element={<ForgotPassword />} />
          
          <Route path="/" element={<ProtectedRoute><Layout /></ProtectedRoute>}>
            <Route index element={<Dashboard />} />
            <Route path="students" element={<StudentList />} />
            <Route path="teachers" element={<Teachers />} />
            <Route path="staff" element={<Staff />} />
            <Route path="classes" element={<Classes />} />
            <Route path="attendance" element={<Attendance />} />
            <Route path="fees" element={<Fees />} />
            <Route path="library" element={<Library />} />
          </Route>

          <Route path="*" element={<Navigate to="/" replace />} />
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  );
}

export default App;
