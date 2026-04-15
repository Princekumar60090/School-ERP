import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { 
  Lock, 
  User, 
  AlertCircle, 
  ArrowRight, 
  ChevronRight,
  Shield, 
  GraduationCap, 
  Briefcase,
  Mail 
} from 'lucide-react';
import { motion, AnimatePresence } from 'framer-motion';

const Register = () => {
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [role, setRole] = useState('STUDENT');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  
  const { register } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    const result = await register(username, email, password, role);
    if (result.success) {
      navigate('/');
    } else {
      setError(result.message);
    }
    setLoading(false);
  };

  const roles = [
    { id: 'STUDENT', label: 'Student', icon: GraduationCap, desc: 'View grades and attendance' },
    { id: 'TEACHER', label: 'Teacher', icon: Briefcase, desc: 'Manage classes and marks' },
    { id: 'ADMIN', label: 'Admin', icon: Shield, desc: 'Full system control' },
  ];

  return (
    <div className="auth-container">
      <div className="auth-background">
        <div className="blob blob-1"></div>
        <div className="blob blob-2"></div>
      </div>

      <motion.div 
        initial={{ opacity: 0, y: 20 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.6, ease: "easeOut" }}
        className="register-card"
      >
        <div className="register-content">
          <header className="auth-header">
            <motion.div 
              whileHover={{ rotate: 10 }}
              className="logo-mark"
            >
              S
            </motion.div>
            <h1 className="heading-xl">Create Account</h1>
            <p className="subtitle">Select your role and start your journey</p>
          </header>

          <AnimatePresence>
            {error && (
              <motion.div 
                initial={{ opacity: 0, height: 0 }}
                animate={{ opacity: 1, height: 'auto' }}
                exit={{ opacity: 0, height: 0 }}
                className="error-box"
              >
                <AlertCircle size={20} />
                <span>{error}</span>
              </motion.div>
            )}
          </AnimatePresence>

          <form onSubmit={handleSubmit} className="auth-form">
            <div className="role-selector">
              {roles.map((r) => (
                <div 
                  key={r.id}
                  onClick={() => setRole(r.id)}
                  className={`role-option ${role === r.id ? 'active' : ''}`}
                >
                  <div className="role-icon-box">
                    <r.icon size={20} />
                  </div>
                  <div className="role-info">
                    <span className="role-label">{r.label}</span>
                    <span className="role-desc">{r.desc}</span>
                  </div>
                  {role === r.id && (
                    <motion.div layoutId="active-role" className="active-indicator" />
                  )}
                </div>
              ))}
            </div>

            <div className="form-fields">
              <div className="field-group">
                <label>Username</label>
                <div className="input-wrapper">
                  <User size={18} className="field-icon" />
                  <input 
                    type="text" 
                    className="input-field"
                    value={username} 
                    onChange={(e) => setUsername(e.target.value)}
                    placeholder="Enter unique username"
                    required
                  />
                </div>
              </div>

              <div className="field-group">
                <label>Email Address</label>
                <div className="input-wrapper">
                  <Mail size={18} className="field-icon" />
                  <input 
                    type="email" 
                    className="input-field"
                    value={email} 
                    onChange={(e) => setEmail(e.target.value)}
                    placeholder="Enter your email"
                    required
                  />
                </div>
              </div>

              <div className="field-group">
                <label>Password</label>
                <div className="input-wrapper">
                  <Lock size={18} className="field-icon" />
                  <input 
                    type="password" 
                    className="input-field"
                    value={password} 
                    onChange={(e) => setPassword(e.target.value)}
                    placeholder="Create security password"
                    required
                  />
                </div>
              </div>
            </div>

            <button type="submit" disabled={loading} className="auth-button">
              {loading ? (
                <div className="spinner"></div>
              ) : (
                <>
                  <span>Create My Account</span>
                  <ChevronRight size={20} />
                </>
              )}
            </button>
          </form>

          <footer className="auth-footer">
            <p>Already have an account? <Link to="/login" className="login-link">Sign in here</Link></p>
          </footer>
        </div>
      </motion.div>

      <style jsx>{`
        .auth-container {
          min-height: 100vh;
          display: flex;
          align-items: center;
          justify-content: center;
          padding: 40px 20px;
          background-color: var(--bg-main);
          position: relative;
          overflow: hidden;
        }

        .auth-background {
          position: absolute;
          inset: 0;
          z-index: 0;
        }

        .blob {
          position: absolute;
          width: 500px;
          height: 500px;
          filter: blur(80px);
          opacity: 0.15;
          border-radius: var(--radius-full);
        }

        .blob-1 {
          background: var(--primary);
          top: -100px;
          right: -100px;
        }

        .blob-2 {
          background: var(--accent);
          bottom: -100px;
          left: -100px;
        }

        .register-card {
          width: 100%;
          max-width: 520px;
          background: var(--bg-surface);
          border-radius: var(--radius-lg);
          box-shadow: var(--shadow-lg);
          position: relative;
          z-index: 10;
          overflow: hidden;
          border: 1px solid var(--border-light);
        }

        .register-content {
          padding: 48px;
        }

        .auth-header {
          text-align: center;
          margin-bottom: 32px;
        }

        .logo-mark {
          width: 56px;
          height: 56px;
          background: linear-gradient(135deg, var(--primary) 0%, var(--accent) 100%);
          color: white;
          border-radius: var(--radius-md);
          display: flex;
          align-items: center;
          justify-content: center;
          font-weight: 800;
          font-size: 28px;
          margin: 0 auto 20px;
          box-shadow: 0 8px 16px rgba(99, 102, 241, 0.2);
        }

        .subtitle {
          color: var(--text-muted);
          margin-top: 8px;
          font-weight: 500;
        }

        .error-box {
          background: #fef2f2;
          border: 1px solid #fee2e2;
          color: #ef4444;
          padding: 12px 16px;
          border-radius: var(--radius-md);
          margin-bottom: 24px;
          display: flex;
          align-items: center;
          gap: 12px;
          font-size: 14px;
          font-weight: 500;
          overflow: hidden;
        }

        .role-selector {
          display: grid;
          grid-template-columns: 1fr;
          gap: 12px;
          margin-bottom: 32px;
        }

        .role-option {
          position: relative;
          padding: 12px 16px;
          background: var(--bg-muted);
          border: 1px solid var(--border-main);
          border-radius: var(--radius-md);
          display: flex;
          align-items: center;
          gap: 16px;
          cursor: pointer;
          transition: all 0.3s ease;
        }

        .role-option:hover {
          background: var(--bg-surface);
          border-color: var(--primary);
        }

        .role-option.active {
          background: white;
          border-color: var(--primary);
          box-shadow: var(--shadow-md);
        }

        .role-icon-box {
          width: 40px;
          height: 40px;
          background: white;
          border: 1px solid var(--border-main);
          border-radius: var(--radius-sm);
          display: flex;
          align-items: center;
          justify-content: center;
          color: var(--text-muted);
          transition: all 0.3s ease;
        }

        .role-option.active .role-icon-box {
          background: var(--primary);
          color: white;
          border-color: var(--primary);
        }

        .role-info {
          display: flex;
          flex-direction: column;
        }

        .role-label {
          font-weight: 700;
          font-size: 15px;
        }

        .role-desc {
          font-size: 12px;
          color: var(--text-muted);
        }

        .active-indicator {
          position: absolute;
          right: 16px;
          width: 8px;
          height: 8px;
          background: var(--primary);
          border-radius: var(--radius-full);
          box-shadow: 0 0 10px var(--primary);
        }

        .form-fields {
          display: grid;
          grid-template-columns: 1fr;
          gap: 20px;
          margin-bottom: 32px;
        }

        .field-group label {
          display: block;
          font-size: 14px;
          font-weight: 600;
          color: var(--text-main);
          margin-bottom: 8px;
          margin-left: 2px;
        }

        .input-wrapper {
          position: relative;
        }

        .field-icon {
          position: absolute;
          left: 16px;
          top: 50%;
          transform: translateY(-50%);
          color: var(--text-dim);
          z-index: 10;
        }

        .input-wrapper .input-field {
          padding-left: 48px;
        }

        .auth-footer {
          margin-top: 32px;
          text-align: center;
          font-size: 14px;
          color: var(--text-muted);
          font-weight: 500;
        }

        .login-link {
          color: var(--primary);
          font-weight: 700;
          text-decoration: none;
        }

        .login-link:hover {
          text-decoration: underline;
        }

        .spinner {
          width: 24px;
          height: 24px;
          border: 3px solid rgba(255,255,255,0.3);
          border-top-color: white;
          border-radius: 50%;
          animation: spin 0.8s linear infinite;
        }

        @keyframes spin {
          to { transform: rotate(360deg); }
        }

        @media (max-width: 480px) {
          .register-content { padding: 32px 24px; }
          .heading-xl { font-size: 24px; }
        }
      `}</style>
    </div>
  );
};

export default Register;
