import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { Lock, User, AlertCircle, ChevronRight } from 'lucide-react';
import { motion, AnimatePresence } from 'framer-motion';

const Login = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  
  const { login } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    const result = await login(username, password);
    if (result.success) {
      navigate('/');
    } else {
      setError(result.message);
    }
    setLoading(false);
  };

  return (
    <div className="auth-container">
      <div className="auth-background">
        <div className="blob blob-1"></div>
        <div className="blob blob-2"></div>
      </div>

      <motion.div 
        initial={{ opacity: 0, scale: 0.95 }}
        animate={{ opacity: 1, scale: 1 }}
        transition={{ duration: 0.5 }}
        className="register-card"
      >
        <div className="register-content">
          <header className="auth-header">
            <div className="logo-mark">S</div>
            <h1 className="heading-xl">Welcome Back</h1>
            <p className="subtitle">Sign in to manage your school workspace</p>
          </header>

          <AnimatePresence>
            {error && (
              <motion.div 
                initial={{ opacity: 0, y: -10 }}
                animate={{ opacity: 1, y: 0 }}
                exit={{ opacity: 0, y: -10 }}
                className="error-box"
              >
                <AlertCircle size={20} />
                <span>{error}</span>
              </motion.div>
            )}
          </AnimatePresence>

          <form onSubmit={handleSubmit} className="auth-form">
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
                    placeholder="Enter your username"
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
                    placeholder="••••••••"
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
                  <span>Sign In</span>
                  <ChevronRight size={20} />
                </>
              )}
            </button>
          </form>

          <footer className="auth-footer">
            <p>Don't have an account? <Link to="/register" className="login-link">Sign up now</Link></p>
          </footer>
        </div>
      </motion.div>

      <style jsx>{`
        .auth-container {
          min-height: 100vh;
          display: flex;
          align-items: center;
          justify-content: center;
          padding: 20px;
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
          width: 600px;
          height: 600px;
          filter: blur(100px);
          opacity: 0.1;
          border-radius: 50%;
        }

        .blob-1 { background: var(--primary); top: -200px; left: -200px; }
        .blob-2 { background: var(--accent); bottom: -200px; right: -200px; }

        .register-card {
          width: 100%;
          max-width: 460px;
          background: var(--bg-surface);
          border-radius: var(--radius-lg);
          box-shadow: var(--shadow-lg);
          position: relative;
          z-index: 10;
          border: 1px solid var(--border-light);
        }

        .register-content { padding: 48px; }

        .auth-header { text-align: center; margin-bottom: 32px; }

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
        }

        .subtitle { color: var(--text-muted); font-weight: 500; font-size: 15px; }

        .error-box {
          background: #fef2f2;
          color: #ef4444;
          padding: 12px 16px;
          border-radius: var(--radius-md);
          margin-bottom: 24px;
          display: flex;
          align-items: center;
          gap: 12px;
          font-size: 14px;
          font-weight: 500;
        }

        .form-fields { display: flex; flex-direction: column; gap: 20px; margin-bottom: 32px; }

        .field-group label {
          display: block;
          font-size: 14px;
          font-weight: 600;
          margin-bottom: 8px;
          color: var(--text-main);
        }

        .input-wrapper { position: relative; }

        .field-icon {
          position: absolute;
          left: 16px;
          top: 50%;
          transform: translateY(-50%);
          color: var(--text-dim);
          z-index: 10;
        }

        .input-wrapper .input-field { padding-left: 48px; }

        .auth-footer { margin-top: 32px; text-align: center; color: var(--text-muted); font-size: 14px; }

        .login-link { color: var(--primary); font-weight: 700; text-decoration: none; }

        .login-link:hover { text-decoration: underline; }

        .spinner {
          width: 24px;
          height: 24px;
          border: 3px solid rgba(255,255,255,0.3);
          border-top-color: white;
          border-radius: 50%;
          animation: spin 0.8s linear infinite;
        }

        @keyframes spin { to { transform: rotate(360deg); } }
      `}</style>
    </div>
  );
};

export default Login;
