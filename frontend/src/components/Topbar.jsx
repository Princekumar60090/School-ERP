import React from 'react';
import { Bell, Search, UserCircle } from 'lucide-react';
import { useAuth } from '../context/AuthContext';

const Topbar = ({ title }) => {
  const { user } = useAuth();

  return (
    <header className="topbar">
      <div className="topbar-left">
        <h2>{title}</h2>
      </div>
      
      <div className="topbar-right">
        <div className="search-bar">
          <Search size={18} />
          <input type="text" placeholder="Search..." />
        </div>
        
        <button className="icon-btn">
          <Bell size={20} />
          <span className="badge">3</span>
        </button>

        <div className="user-profile">
          <div className="user-info">
            <span className="user-name">{user?.username}</span>
            <span className="user-role">{user?.roles?.[0]?.replace('ROLE_', '')}</span>
          </div>
          <UserCircle size={32} strokeWidth={1.5} />
        </div>
      </div>

      <style jsx>{`
        .topbar {
          height: 70px;
          background: rgba(255, 255, 255, 0.8);
          backdrop-filter: blur(10px);
          border-bottom: 1px solid var(--border);
          display: flex;
          align-items: center;
          justify-content: space-between;
          padding: 0 32px;
          position: sticky;
          top: 0;
          z-index: 40;
        }

        .search-bar {
          display: flex;
          align-items: center;
          gap: 8px;
          background: var(--bg-main);
          padding: 8px 16px;
          border-radius: 20px;
          border: 1px solid var(--border);
        }

        .search-bar input {
          background: none;
          border: none;
          outline: none;
          width: 200px;
        }

        .topbar-right {
          display: flex;
          align-items: center;
          gap: 20px;
        }

        .icon-btn {
          background: none;
          border: none;
          cursor: pointer;
          color: var(--text-muted);
          position: relative;
        }

        .badge {
          position: absolute;
          top: -5px;
          right: -5px;
          background: #ef4444;
          color: white;
          font-size: 10px;
          padding: 2px 5px;
          border-radius: 10px;
          border: 2px solid white;
        }

        .user-profile {
          display: flex;
          align-items: center;
          gap: 12px;
          padding-left: 20px;
          border-left: 1px solid var(--border);
        }

        .user-info {
          display: flex;
          flex-direction: column;
          align-items: flex-end;
        }

        .user-name {
          font-weight: 600;
          font-size: 14px;
        }

        .user-role {
          font-size: 11px;
          color: var(--text-muted);
          text-transform: capitalize;
        }
      `}</style>
    </header>
  );
};

export default Topbar;
