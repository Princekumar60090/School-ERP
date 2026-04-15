import React from 'react';
import { NavLink } from 'react-router-dom';
import { 
  LayoutDashboard, 
  Users, 
  UserSquare2, 
  BookOpen, 
  CalendarCheck, 
  GraduationCap, 
  Wallet, 
  Library,
  Settings,
  LogOut
} from 'lucide-react';
import { useAuth } from '../context/AuthContext';

const Sidebar = () => {
  const { logout, user } = useAuth();

  const menuItems = [
    { name: 'Dashboard', path: '/', icon: LayoutDashboard },
    { name: 'Students', path: '/students', icon: GraduationCap },
    { name: 'Teachers', path: '/teachers', icon: UserSquare2 },
    { name: 'Staff', path: '/staff', icon: Users },
    { name: 'Classes', path: '/classes', icon: BookOpen },
    { name: 'Attendance', path: '/attendance', icon: CalendarCheck },
    { name: 'Fees', path: '/fees', icon: Wallet },
    { name: 'Library', path: '/library', icon: Library },
  ];

  return (
    <aside className="sidebar">
      <div className="sidebar-logo">
        <div className="logo-icon">S</div>
        <h3>SchoolERP</h3>
      </div>
      
      <nav className="sidebar-nav">
        {menuItems.map((item) => (
          <NavLink 
            key={item.path} 
            to={item.path}
            className={({ isActive }) => `nav-item ${isActive ? 'active' : ''}`}
          >
            <item.icon size={20} />
            <span>{item.name}</span>
          </NavLink>
        ))}
      </nav>

      <div className="sidebar-footer">
        <button onClick={logout} className="logout-btn">
          <LogOut size={20} />
          <span>Logout</span>
        </button>
      </div>

      <style jsx>{`
        .sidebar {
          width: var(--sidebar-width);
          height: 100vh;
          background: var(--bg-card);
          border-right: 1px solid var(--border);
          padding: 24px 0;
          display: flex;
          flex-direction: column;
          position: fixed;
          left: 0;
          top: 0;
          z-index: 50;
        }

        .sidebar-logo {
          padding: 0 24px;
          margin-bottom: 32px;
          display: flex;
          align-items: center;
          gap: 12px;
        }

        .logo-icon {
          width: 32px;
          height: 32px;
          background: var(--primary);
          color: white;
          display: flex;
          align-items: center;
          justify-content: center;
          border-radius: 8px;
          font-weight: bold;
        }

        .sidebar-nav {
          flex: 1;
          display: flex;
          flex-direction: column;
          gap: 4px;
          padding: 0 12px;
        }

        .nav-item {
          display: flex;
          align-items: center;
          gap: 12px;
          padding: 12px;
          text-decoration: none;
          color: var(--text-muted);
          border-radius: 8px;
          transition: all 0.2s;
        }

        .nav-item:hover {
          background-color: var(--bg-main);
          color: var(--text-main);
        }

        .nav-item.active {
          background-color: var(--primary);
          color: white;
        }

        .sidebar-footer {
          padding: 12px;
          border-top: 1px solid var(--border);
        }

        .logout-btn {
          width: 100%;
          display: flex;
          align-items: center;
          gap: 12px;
          padding: 12px;
          background: none;
          border: none;
          color: #ef4444;
          cursor: pointer;
          border-radius: 8px;
          transition: background 0.2s;
        }

        .logout-btn:hover {
          background: #fee2e2;
        }
      `}</style>
    </aside>
  );
};

export default Sidebar;
