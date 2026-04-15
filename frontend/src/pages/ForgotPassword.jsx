import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { authAPI } from '../api/api';
import { 
  Mail, 
  ShieldCheck, 
  Lock, 
  ArrowRight, 
  ArrowLeft, 
  Loader2, 
  CheckCircle2,
  KeyRound,
  Fingerprint
} from 'lucide-react';
import { motion, AnimatePresence } from 'framer-motion';

const ForgotPassword = () => {
  const [step, setStep] = useState(1); // 1: Email, 2: OTP, 3: New Password, 4: Success
  const [email, setEmail] = useState('');
  const [otp, setOtp] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [successMsg, setSuccessMsg] = useState('');

  const navigate = useNavigate();

  const handleSendOtp = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');
    try {
      await authAPI.forgotPassword(email);
      setSuccessMsg('Recovery code sent successfully!');
      setStep(2);
    } catch (err) {
      setError(err.response?.data || 'Email address not found in our records.');
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
      setSuccessMsg('Verification successful!');
      setStep(3);
    } catch (err) {
      setError(err.response?.data || 'Invalid recovery code. Please check and try again.');
    } finally {
      setLoading(false);
    }
  };

  const handleResetPassword = async (e) => {
    e.preventDefault();
    if (newPassword.length < 6) {
      setError('Password must be at least 6 characters long');
      return;
    }
    if (newPassword !== confirmPassword) {
      setError('Passwords do not match');
      return;
    }
    setLoading(true);
    setError('');
    try {
      await authAPI.resetPassword(email, otp, newPassword);
      setStep(4);
    } catch (err) {
      setError(err.response?.data || 'Session expired. Please start over.');
    } finally {
      setLoading(false);
    }
  };

  const stepVariants = {
    initial: { opacity: 0, x: 20 },
    animate: { opacity: 1, x: 0 },
    exit: { opacity: 0, x: -20 },
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
        className="forgot-password-card"
      >
        {/* Progress Bar */}
        <div className="progress-container">
          <div className={`progress-bar step-${step}`}></div>
        </div>

        <div className="card-inner">
          <header className="auth-header">
            <motion.div 
              initial={{ rotate: -10 }}
              animate={{ rotate: 0 }}
              className="logo-mark"
            >
              {step === 1 && <Fingerprint size={28} />}
              {step === 2 && <ShieldCheck size={28} />}
              {step === 3 && <KeyRound size={28} />}
              {step === 4 && <CheckCircle2 size={28} />}
            </motion.div>
            
            <h1 className="heading-xl">
              {step === 1 && "Forgot Password?"}
              {step === 2 && "Verification"}
              {step === 3 && "Secure Account"}
              {step === 4 && "All Set!"}
            </h1>
            <p className="subtitle">
              {step === 1 && "Enter your email to receive a secure recovery code"}
              {step === 2 && `We've sent a 6-digit code to ${email}`}
              {step === 3 && "Create a strong new password for your account"}
              {step === 4 && "Your password has been reset successfully"}
            </p>
          </header>

          <AnimatePresence mode="wait">
            {error && (
              <motion.div 
                initial={{ opacity: 0, y: -10 }}
                animate={{ opacity: 1, y: 0 }}
                exit={{ opacity: 0, y: -10 }}
                className="error-alert"
              >
                <span>{error}</span>
              </motion.div>
            )}
            {successMsg && !error && step < 4 && (
              <motion.div 
                initial={{ opacity: 0, y: -10 }}
                animate={{ opacity: 1, y: 0 }}
                exit={{ opacity: 0, y: -10 }}
                className="success-alert"
              >
                <span>{successMsg}</span>
              </motion.div>
            )}
          </AnimatePresence>

          <div className="form-container">
            <AnimatePresence mode="wait">
              {step === 1 && (
                <motion.form 
                  key="step1"
                  variants={stepVariants}
                  initial="initial"
                  animate="animate"
                  exit="exit"
                  onSubmit={handleSendOtp}
                  className="auth-form"
                >
                  <div className="field-group">
                    <label>Email Address</label>
                    <div className="input-wrapper">
                      <Mail size={18} className="field-icon" />
                      <input 
                        type="email" 
                        value={email} 
                        onChange={(e) => setEmail(e.target.value)}
                        placeholder="name@school.com"
                        className="input-field"
                        required
                      />
                    </div>
                  </div>
                  <button type="submit" disabled={loading} className="auth-button primary">
                    {loading ? <Loader2 className="spin" /> : (
                      <>
                        <span>Send Code</span>
                        <ArrowRight size={20} />
                      </>
                    )}
                  </button>
                </motion.form>
              )}

              {step === 2 && (
                <motion.form 
                  key="step2"
                  variants={stepVariants}
                  initial="initial"
                  animate="animate"
                  exit="exit"
                  onSubmit={handleVerifyOtp}
                  className="auth-form"
                >
                  <div className="field-group text-center">
                    <label>6-Digit OTP</label>
                    <div className="otp-input-container">
                      <ShieldCheck size={20} className="field-icon-overlay" />
                      <input 
                        type="text" 
                        maxLength="6"
                        value={otp} 
                        onChange={(e) => setOtp(e.target.value.replace(/\D/g, ''))}
                        placeholder="000000"
                        className="otp-field"
                        required
                      />
                    </div>
                  </div>
                  <button type="submit" disabled={loading} className="auth-button primary">
                    {loading ? <Loader2 className="spin" /> : (
                      <>
                        <span>Verify Code</span>
                        <ArrowRight size={20} />
                      </>
                    )}
                  </button>
                  <button type="button" onClick={() => setStep(1)} className="auth-button ghost">
                    <ArrowLeft size={18} />
                    <span>Change Email</span>
                  </button>
                </motion.form>
              )}

              {step === 3 && (
                <motion.form 
                  key="step3"
                  variants={stepVariants}
                  initial="initial"
                  animate="animate"
                  exit="exit"
                  onSubmit={handleResetPassword}
                  className="auth-form"
                >
                  <div className="form-stack">
                    <div className="field-group">
                      <label>New Password</label>
                      <div className="input-wrapper">
                        <Lock size={18} className="field-icon" />
                        <input 
                          type="password" 
                          value={newPassword} 
                          onChange={(e) => setNewPassword(e.target.value)}
                          placeholder="Min. 6 characters"
                          className="input-field"
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
                          value={confirmPassword} 
                          onChange={(e) => setConfirmPassword(e.target.value)}
                          placeholder="Repeat password"
                          className="input-field"
                          required
                        />
                      </div>
                    </div>
                  </div>
                  <button type="submit" disabled={loading} className="auth-button primary">
                    {loading ? <Loader2 className="spin" /> : (
                      <>
                        <span>Save Password</span>
                        <ArrowRight size={20} />
                      </>
                    )}
                  </button>
                </motion.form>
              )}

              {step === 4 && (
                <motion.div 
                  key="step4"
                  variants={stepVariants}
                  initial="initial"
                  animate="animate"
                  className="success-view"
                >
                  <div className="success-lottie">
                     <CheckCircle2 size={64} />
                  </div>
                  <button onClick={() => navigate('/login')} className="auth-button primary">
                    <span>Back to Sign In</span>
                    <ArrowRight size={20} />
                  </button>
                </motion.div>
              )}
            </AnimatePresence>
          </div>

          <footer className="auth-footer">
            <Link to="/login" className="back-link">
              <ArrowLeft size={16} />
              <span>Cancel recovery</span>
            </Link>
          </footer>
        </div>
      </motion.div>

      <style jsx>{`
        .forgot-password-card {
          width: 100%;
          max-width: 460px;
          background: rgba(255, 255, 255, 0.85);
          backdrop-filter: blur(20px);
          border-radius: 24px;
          box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.1);
          border: 1px solid rgba(255, 255, 255, 0.5);
          overflow: hidden;
          position: relative;
          z-index: 10;
        }

        .progress-container {
          height: 4px;
          background: #f1f5f9;
          width: 100%;
        }

        .progress-bar {
          height: 100%;
          background: linear-gradient(to right, var(--primary), var(--accent));
          transition: width 0.4s cubic-bezier(0.4, 0, 0.2, 1);
        }

        .progress-bar.step-1 { width: 25%; }
        .progress-bar.step-2 { width: 50%; }
        .progress-bar.step-3 { width: 75%; }
        .progress-bar.step-4 { width: 100%; }

        .card-inner {
          padding: 40px;
        }

        .logo-mark {
          width: 64px;
          height: 64px;
          background: white;
          border-radius: 18px;
          display: flex;
          align-items: center;
          justify-content: center;
          margin: 0 auto 24px;
          color: var(--primary);
          box-shadow: 0 10px 15px -3px rgba(99, 102, 241, 0.2);
        }

        .auth-header {
          text-align: center;
          margin-bottom: 32px;
        }

        .heading-xl {
          font-size: 28px;
          font-weight: 800;
          color: #1e293b;
          margin-bottom: 8px;
        }

        .subtitle {
          color: #64748b;
          font-size: 15px;
          line-height: 1.5;
        }

        .error-alert {
          background: #fef2f2;
          border: 1px solid #fee2e2;
          color: #dc2626;
          padding: 12px;
          border-radius: 12px;
          font-size: 14px;
          text-align: center;
          margin-bottom: 24px;
        }

        .success-alert {
          background: #f0fdf4;
          border: 1px solid #dcfce7;
          color: #16a34a;
          padding: 12px;
          border-radius: 12px;
          font-size: 14px;
          text-align: center;
          margin-bottom: 24px;
        }

        .form-stack {
          display: flex;
          flex-direction: column;
          gap: 16px;
        }

        .field-group {
          margin-bottom: 24px;
        }

        .field-group label {
          display: block;
          font-size: 14px;
          font-weight: 600;
          color: #475569;
          margin-bottom: 8px;
        }

        .input-wrapper {
          position: relative;
        }

        .field-icon {
          position: absolute;
          left: 16px;
          top: 50%;
          transform: translateY(-50%);
          color: #94a3b8;
        }

        .input-field {
          width: 100%;
          padding: 14px 16px 14px 48px;
          background: white;
          border: 1.5px solid #e2e8f0;
          border-radius: 14px;
          font-size: 16px;
          transition: all 0.2s;
        }

        .input-field:focus {
          border-color: var(--primary);
          box-shadow: 0 0 0 4px rgba(99, 102, 241, 0.1);
          outline: none;
        }

        .otp-input-container {
          position: relative;
          max-width: 200px;
          margin: 0 auto;
        }

        .otp-field {
          width: 100%;
          height: 60px;
          text-align: center;
          font-size: 28px;
          font-weight: 800;
          letter-spacing: 4px;
          border: 2px solid #e2e8f0;
          border-radius: 16px;
          background: white;
          color: var(--primary);
        }

        .otp-field:focus {
          border-color: var(--primary);
          outline: none;
        }

        .field-icon-overlay {
          position: absolute;
          left: -32px;
          top: 50%;
          transform: translateY(-50%);
          color: #cbd5e1;
        }

        .auth-button {
          width: 100%;
          padding: 14px;
          border-radius: 14px;
          font-size: 16px;
          font-weight: 700;
          display: flex;
          align-items: center;
          justify-content: center;
          gap: 10px;
          cursor: pointer;
          transition: all 0.2s;
          border: none;
        }

        .auth-button.primary {
          background: var(--primary);
          color: white;
          box-shadow: 0 4px 12px rgba(99, 102, 241, 0.3);
        }

        .auth-button.primary:hover {
          background: var(--primary-dark);
          transform: translateY(-2px);
          box-shadow: 0 6px 20px rgba(99, 102, 241, 0.4);
        }

        .auth-button.ghost {
          background: transparent;
          color: #64748b;
          margin-top: 12px;
          font-size: 14px;
        }

        .success-view {
          text-align: center;
          padding: 20px 0;
        }

        .success-lottie {
          margin-bottom: 32px;
          color: #10b981;
          display: flex;
          justify-content: center;
        }

        .auth-footer {
          margin-top: 32px;
          border-top: 1px solid #f1f5f9;
          padding-top: 24px;
          text-align: center;
        }

        .back-link {
          display: flex;
          align-items: center;
          justify-content: center;
          gap: 8px;
          color: #64748b;
          text-decoration: none;
          font-weight: 600;
          font-size: 14px;
          transition: color 0.2s;
        }

        .back-link:hover {
          color: var(--primary);
        }

        .spin {
          animation: spin 1s linear infinite;
        }

        @keyframes spin {
          from { transform: rotate(0deg); }
          to { transform: rotate(360deg); }
        }
      `}</style>
    </div>
  );
};

export default ForgotPassword;
