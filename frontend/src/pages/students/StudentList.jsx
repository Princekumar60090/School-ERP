import React, { useState, useEffect } from 'react';
import { studentAPI } from '../../api/api';
import { Plus, Search, Edit2, Trash2 } from 'lucide-react';
import { motion } from 'framer-motion';
import StudentForm from './StudentForm';

const StudentList = () => {
  const [students, setStudents] = useState([]);
  const [loading, setLoading] = useState(true);
  const [searchTerm, setSearchTerm] = useState('');
  const [showForm, setShowForm] = useState(false);
  const [selectedStudent, setSelectedStudent] = useState(null);

  useEffect(() => {
    fetchStudents();
  }, []);

  const fetchStudents = async () => {
    try {
      const response = await studentAPI.getAll();
      setStudents(response.data);
    } catch (error) {
      console.error('Error fetching students:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleEdit = (student) => {
    setSelectedStudent(student);
    setShowForm(true);
  };

  const handleDelete = async (id) => {
    if (window.confirm('Are you sure you want to delete this student?')) {
      try {
        await studentAPI.delete(id);
        setStudents(students.filter(s => s.id !== id));
      } catch (error) {
        console.error('Error deleting student:', error);
      }
    }
  };

  const filteredStudents = students.filter(s => 
    s.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
    s.rollNo.includes(searchTerm)
  );

  return (
    <div className="students-page">
      <div className="page-header">
        <div className="search-box">
          <Search size={20} className="search-icon" />
          <input 
            type="text" 
            placeholder="Search by name or roll number..." 
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
        </div>
        <button className="btn-primary" onClick={() => { setSelectedStudent(null); setShowForm(true); }}>
          <Plus size={20} />
          <span>Add Student</span>
        </button>
      </div>

      {showForm && (
        <StudentForm 
          student={selectedStudent} 
          onClose={() => setShowForm(false)} 
          onSuccess={fetchStudents} 
        />
      )}

      <div className="card table-container">
        {loading ? (
          <div className="loading">Loading students...</div>
        ) : (
          <table className="custom-table">
            <thead>
              <tr>
                <th>Roll No</th>
                <th>Name</th>
                <th>Grade</th>
                <th>Parent Name</th>
                <th>Phone</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {filteredStudents.map((student) => (
                <tr key={student.id}>
                  <td className="font-bold">{student.rollNo}</td>
                  <td>
                    <div className="user-cell">
                      <div className="avatar">{student.name.charAt(0)}</div>
                      <div className="info">
                        <span className="name">{student.name}</span>
                        <span className="email">{student.email}</span>
                      </div>
                    </div>
                  </td>
                  <td><span className="badge-grade">Grade {student.grade}</span></td>
                  <td>{student.fatherName}</td>
                  <td>{student.phone}</td>
                  <td className="actions-cell">
                    <button className="icon-btn edit" onClick={() => handleEdit(student)}><Edit2 size={16} /></button>
                    <button onClick={() => handleDelete(student.id)} className="icon-btn delete"><Trash2 size={16} /></button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>

      <style jsx>{`
        .page-header {
          display: flex;
          justify-content: space-between;
          align-items: center;
          margin-bottom: 24px;
        }

        .search-box {
          position: relative;
          width: 400px;
        }

        .search-icon {
          position: absolute;
          left: 12px;
          top: 50%;
          transform: translateY(-50%);
          color: var(--text-muted);
        }

        .search-box input {
          width: 100%;
          padding: 10px 12px 10px 40px;
          border-radius: 10px;
          border: 1px solid var(--border);
          background: var(--bg-card);
          outline: none;
        }

        .table-container {
          overflow-x: auto;
          padding: 0;
        }

        .custom-table {
          width: 100%;
          border-collapse: collapse;
          text-align: left;
        }

        .custom-table th {
          padding: 16px 24px;
          background: var(--bg-main);
          color: var(--text-muted);
          font-weight: 600;
          font-size: 13px;
          text-transform: uppercase;
          letter-spacing: 0.05em;
        }

        .custom-table td {
          padding: 16px 24px;
          border-bottom: 1px solid var(--border);
          font-size: 14px;
        }

        .user-cell {
          display: flex;
          align-items: center;
          gap: 12px;
        }

        .avatar {
          width: 32px;
          height: 32px;
          background: var(--primary);
          color: white;
          border-radius: 50%;
          display: flex;
          align-items: center;
          justify-content: center;
          font-weight: 600;
          font-size: 12px;
        }

        .info {
          display: flex;
          flex-direction: column;
        }

        .name {
          font-weight: 600;
          color: var(--text-main);
        }

        .email {
          font-size: 12px;
          color: var(--text-muted);
        }

        .badge-grade {
          background: #e0e7ff;
          color: #4338ca;
          padding: 4px 10px;
          border-radius: 6px;
          font-size: 12px;
          font-weight: 600;
        }

        .actions-cell {
          display: flex;
          gap: 8px;
        }

        .icon-btn.edit { color: var(--primary); }
        .icon-btn.delete { color: #ef4444; }

        .icon-btn {
          padding: 6px;
          border-radius: 6px;
          background: none;
          border: none;
          cursor: pointer;
          transition: background 0.2s;
        }

        .icon-btn:hover {
          background: var(--bg-main);
        }

        .loading {
          padding: 40px;
          text-align: center;
          color: var(--text-muted);
        }
      `}</style>
    </div>
  );
};

export default StudentList;
