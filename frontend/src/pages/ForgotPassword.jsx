import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { authAPI } from '../api/api';
import { Mail, ShieldCheck, Lock, ArrowRight, ArrowLeft, Loader2, CheckCircle2 } from 'lucide-react';
import { motion, AnimatePresence } from 'framer-motion';

const ForgotPassword = () => {
  const [step, setStep] = useState(1); // 1: Email, 2: OTP, 3: New Password
  const [email, setEmail] = useState('');
  const [otp, setOtp] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [message, setMessage] = useState('');

  const navigate = useNavigate();

  const handleSendOtp = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');
    try {
      await authAPI.forgotPassword(email);
      setMessage('OTP sent to your email!');
      setStep(2);
    } catch (err) {
      setError(err.response?.data || 'Failed to send OTP. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  const handleVerifyOtp = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');
    try {
      await authAPI.verifyOtp(email, otp);
      setMessage('OTP verified! Enter new password.');
      setStep(3);
    } catch (err) {
      setError(err.response?.data || 'Invalid OTP. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  const handleResetPassword = async (e) => {
    e.preventDefault();
    if (newPassword !== confirmPassword) {
      setError('Passwords do not match');
      return;
    }
    setLoading(true);
    setError('');
    try {
      await authAPI.resetPassword(email, otp, newPassword);
      setStep(4); // Success step
    } catch (err) {
      setError(err.response?.data || 'Reset failed. Please try again.');
    } finally {
      setLoading(false);
    }
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
        className="register-card"
      >
        <div className="register-content">
          <header className="auth-header">
            <div className="logo-mark">S</div>
            <h1 className="heading-xl">Reset Password</h1>
            <p className="subtitle">
              {step === 1 && "Enter your email to receive recovery OTP"}
              {step === 2 && "Enter the 6-digit code sent to your mail"}
              {step === 3 && "Secure your account with a new password"}
            </p>
          </header>

          <AnimatePresence mode="wait">
            {error && (
              <motion.div 
                initial={{ opacity: 0, height: 0 }}
                animate={{ opacity: 1, height: 'auto' }}
                exit={{ opacity: 0, height: 0 }}
                className="error-box"
              >
                <ArrowLeft size={18} />
                <span>{error}</span>
              </motion.div>
            )}
          </AnimatePresence>

          <AnimatePresence mode="wait">
            {step === 1 && (
              <motion.form 
                key="step1"
                initial={{ opacity: 0, x: 20 }}
                animate={{ opacity: 1, x: 0 }}
                exit={{ opacity: 0, x: -20 }}
                onSubmit={handleSendOtp}
                className="auth-form"
              >
                <div className="form-fields">
                  <div className="field-group">
                    <label>Email Address</label>
                    <div className="input-wrapper">
                      <Mail size={18} className="field-icon" />
                      <input 
                        type="email" 
                        className="input-field"
                        value={email} 
                        onChange={(e) => setEmail(e.target.value)}
                        placeholder="yourname@school.com"
                        required
                      />
                    </div>
                  </div>
                </div>
                <button type="submit" disabled={loading} className="auth-button">
                  {loading ? <Loader2 className="spinner" /> : (
                    <>
                      <span>Send Recovery Code</span>
                      <ArrowRight size={20} />
                    </>
                  )}
                </button>
              </motion.form>
            )}

            {step === 2 && (
              <motion.form 
                key="step2"
                initial={{ opacity: 0, x: 20 }}
                animate={{ opacity: 1, x: 0 }}
                exit={{ opacity: 0, x: -20 }}
                onSubmit={handleVerifyOtp}
                className="auth-form"
              >
                <div className="form-fields">
                  <div className="field-group">
                    <label>Verification Code</label>
                    <div className="input-wrapper">
                      <ShieldCheck size={18} className="field-icon" />
                      <input 
                        type="text" 
                        className="input-field"
                        value={otp} 
                        onChange={(e) => setOtp(e.target.value)}
                        placeholder="6-digit OTP"
                        maxLength="6"
                        required
                      />
                    </div>
                  </div>
                </div>
                <button type="submit" disabled={loading} className="auth-button">
                  {loading ? <Loader2 className="spinner" /> : (
                    <>
                      <span>Verify OTP</span>
                      <ArrowRight size={20} />
                    </>
                  )}
                </button>
              </motion.form>
            )}

            {step === 3 && (
              <motion.form 
                key="step3"
                initial={{ opacity: 0, x: 20 }}
                animate={{ opacity: 1, x: 0 }}
                exit={{ opacity: 0, x: -20 }}
                onSubmit={handleResetPassword}
                className="auth-form"
              >
                <div className="form-fields">
                  <div className="field-group">
                    <label>New Password</label>
                    <div className="input-wrapper">
                      <Lock size={18} className="field-icon" />
                      <input 
                        type="password" 
                        className="input-field"
                        value={newPassword} 
                        onChange={(e) => setNewPassword(e.target.value)}
                        placeholder="Enter new password"
                        required
                      />
                    </div>
                  </div>
                  <div className="field-group">
                    <label>Confirm Password</label>
                    <div className="input-wrapper">
                      <Lock size={18} className="field-icon" />
                      <input 
                        type="password" 
                        className="input-field"
                        value={confirmPassword} 
                        onChange={(e) => setConfirmPassword(e.target.value)}
                        placeholder="Repeat new password"
                        required
                      />
                    </div>
                  </div>
                </div>
                <button type="submit" disabled={loading} className="auth-button">
                  {loading ? <Loader2 className="spinner" /> : (
                    <>
                      <span>Reset Password</span>
                      <ArrowRight size={20} />
                    </>
                  )}
                </button>
              </motion.form>
            )}

            {step === 4 && (
              <motion.div 
                key="success"
                initial={{ opacity: 0, scale: 0.9 }}
                animate={{ opacity: 1, scale: 1 }}
                className="success-view"
              >
                <div className="success-icon-box">
                  <CheckCircle2 size={48} className="success-icon" />
                </div>
                <h2>Success!</h2>
                <p>Your password has been reset successfully.</p>
                <button onClick={() => navigate('/login')} className="auth-button">
                  <span>Go to Login</span>
                  <ArrowRight size={20} />
                </button>
              </motion.div>
            )}
          </AnimatePresence>

          <footer className="auth-footer">
            <Link to="/login" className="back-link">
              <ArrowLeft size={16} />
              <span>Back to login</span>
            </Link>
          </footer>
        </div>
      </motion.div>

      <style jsx>{`
        .success-view {
          text-align: center;
          padding: 20px 0;
        }

        .success-icon-box {
          width: 80px;
          height: 80px;
          background: #f0fdf4;
          border-radius: 50%;
          display: flex;
          align-items: center;
          justify-content: center;
          margin: 0 auto 20px;
          color: #22c55e;
        }

        .success-view h2 {
          font-size: 24px;
          font-weight: 800;
          margin-bottom: 8px;
        }

        .success-view p {
          color: var(--text-muted);
          margin-bottom: 32px;
        }

        .back-link {
          display: flex;
          align-items: center;
          justify-content: center;
          gap: 8px;
          text-decoration: none;
          color: var(--text-muted);
          font-weight: 600;
          transition: all 0.2s;
        }

        .back-link:hover {
          color: var(--primary);
          transform: translateX(-4px);
        }

        .spinner {
          animation: spin 1s linear infinite;
        }
        @keyframes spin { from { transform: rotate(0deg); } to { transform: rotate(360deg); } }
      `}</style>
    </div>
  );
};

export default ForgotPassword;
