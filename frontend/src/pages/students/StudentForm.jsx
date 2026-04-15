import React, { useState } from 'react';
import { studentAPI } from '../../api/api';
import { X, Save, AlertCircle } from 'lucide-react';

const StudentForm = ({ student, onClose, onSuccess }) => {
  const [formData, setFormData] = useState(student || {
    rollNo: '',
    name: '',
    fatherName: '',
    motherName: '',
    grade: 1,
    phone: '',
    email: ''
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');

    try {
      if (student?.id) {
        await studentAPI.update(student.id, formData);
      } else {
        await studentAPI.create(formData);
      }
      onSuccess();
      onClose();
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to save student');
      setLoading(false);
    }
  };

  return (
    <div className="modal-overlay">
      <div className="modal-content card animate-fade-in">
        <div className="modal-header">
          <h3>{student ? 'Edit Student' : 'Add New Student'}</h3>
          <button className="close-btn" onClick={onClose}><X size={20} /></button>
        </div>

        {error && (
          <div className="error-alert">
            <AlertCircle size={18} />
            <span>{error}</span>
          </div>
        )}

        <form onSubmit={handleSubmit} className="student-form">
          <div className="form-row">
            <div className="form-group">
              <label>Full Name</label>
              <input name="name" value={formData.name} onChange={handleChange} required />
            </div>
            <div className="form-group">
              <label>Roll Number</label>
              <input name="rollNo" value={formData.rollNo} onChange={handleChange} required />
            </div>
          </div>

          <div className="form-row">
            <div className="form-group">
              <label>Grade</label>
              <select name="grade" value={formData.grade} onChange={handleChange}>
                {[1,2,3,4,5,6,7,8,9,10,11,12].map(g => (
                  <option key={g} value={g}>Grade {g}</option>
                ))}
              </select>
            </div>
            <div className="form-group">
              <label>Email</label>
              <input type="email" name="email" value={formData.email} onChange={handleChange} required />
            </div>
          </div>

          <div className="form-row">
            <div className="form-group">
              <label>Father's Name</label>
              <input name="fatherName" value={formData.fatherName} onChange={handleChange} required />
            </div>
            <div className="form-group">
              <label>Mother's Name</label>
              <input name="motherName" value={formData.motherName} onChange={handleChange} required />
            </div>
          </div>

          <div className="form-group">
            <label>Phone Number</label>
            <input name="phone" value={formData.phone} onChange={handleChange} required maxLength="10" />
          </div>

          <div className="modal-footer">
            <button type="button" className="btn-secondary" onClick={onClose}>Cancel</button>
            <button type="submit" disabled={loading} className="btn-primary">
              <Save size={18} />
              <span>{loading ? 'Saving...' : 'Save Student'}</span>
            </button>
          </div>
        </form>
      </div>

      <style jsx>{`
        .modal-overlay {
          position: fixed;
          inset: 0;
          background: rgba(0, 0, 0, 0.5);
          backdrop-filter: blur(4px);
          display: flex;
          align-items: center;
          justify-content: center;
          z-index: 100;
          padding: 20px;
        }

        .modal-content {
          width: 100%;
          max-width: 600px;
          background: var(--bg-card);
        }

        .modal-header {
          display: flex;
          justify-content: space-between;
          align-items: center;
          margin-bottom: 24px;
        }

        .close-btn {
          background: none;
          border: none;
          cursor: pointer;
          color: var(--text-muted);
        }

        .student-form {
          display: flex;
          flex-direction: column;
          gap: 16px;
        }

        .form-row {
          display: grid;
          grid-template-columns: 1fr 1fr;
          gap: 16px;
        }

        .form-group {
          display: flex;
          flex-direction: column;
          gap: 8px;
        }

        .form-group label {
          font-size: 13px;
          font-weight: 500;
          color: var(--text-muted);
        }

        .form-group input, .form-group select {
          padding: 10px;
          border-radius: 8px;
          border: 1px solid var(--border);
          outline: none;
          background: var(--bg-main);
        }

        .modal-footer {
          display: flex;
          justify-content: flex-end;
          gap: 12px;
          margin-top: 24px;
          padding-top: 24px;
          border-top: 1px solid var(--border);
        }

        .btn-secondary {
          background: none;
          border: 1px solid var(--border);
          padding: 10px 20px;
          border-radius: 10px;
          cursor: pointer;
          font-weight: 500;
        }
      `}</style>
    </div>
  );
};

export default StudentForm;
