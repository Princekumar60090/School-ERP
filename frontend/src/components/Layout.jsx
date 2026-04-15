import React from 'react';
import { Outlet, useLocation } from 'react-router-dom';
import Sidebar from './Sidebar';
import Topbar from './Topbar';

const Layout = () => {
  const location = useLocation();

  const getTitle = () => {
    const path = location.pathname.split('/')[1];
    if (!path) return 'Dashboard Overview';
    return path.charAt(0).toUpperCase() + path.slice(1);
  };

  return (
    <div className="app-container">
      <Sidebar />
      <main className="main-content">
        <Topbar title={getTitle()} />
        <div className="page-wrapper">
          <Outlet />
        </div>
      </main>

      <style jsx>{`
        .app-container {
          display: flex;
        }

        .main-content {
          flex: 1;
          margin-left: var(--sidebar-width);
          min-height: 100vh;
        }

        .page-wrapper {
          padding: 32px;
        }
      `}</style>
    </div>
  );
};

export default Layout;
