import React from 'react';
import { 
  Users, 
  GraduationCap, 
  BookOpen, 
  TrendingUp, 
  Calendar,
  Clock
} from 'lucide-react';
import { motion } from 'framer-motion';

const StatCard = ({ title, value, icon: Icon, color, trend }) => (
  <motion.div 
    whileHover={{ y: -5 }}
    className="stat-card"
  >
    <div className="stat-content">
      <p className="stat-title">{title}</p>
      <h3 className="stat-value">{value}</h3>
      {trend && (
        <div className="stat-trend">
          <TrendingUp size={16} />
          <span>{trend} vs last month</span>
        </div>
      )}
    </div>
    <div className="stat-icon" style={{ backgroundColor: `${color}15`, color: color }}>
      <Icon size={24} />
    </div>

    <style jsx>{`
      .stat-card {
        background: var(--bg-card);
        padding: 24px;
        border-radius: 16px;
        border: 1px solid var(--border);
        display: flex;
        justify-content: space-between;
        align-items: center;
        box-shadow: var(--shadow-sm);
      }
      .stat-title {
        color: var(--text-muted);
        font-size: 14px;
        margin-bottom: 8px;
      }
      .stat-value {
        font-size: 28px;
        font-weight: 700;
      }
      .stat-icon {
        width: 56px;
        height: 56px;
        border-radius: 12px;
        display: flex;
        align-items: center;
        justify-content: center;
      }
      .stat-trend {
        display: flex;
        align-items: center;
        gap: 4px;
        font-size: 12px;
        color: #10b981;
        margin-top: 8px;
      }
    `}</style>
  </motion.div>
);

const Dashboard = () => {
  const stats = [
    { title: 'Total Students', value: '1,280', icon: GraduationCap, color: '#6366f1', trend: '+12%' },
    { title: 'Total Teachers', value: '45', icon: Users, color: '#ec4899', trend: '+2%' },
    { title: 'Active Classes', value: '18', icon: BookOpen, color: '#f59e0b', trend: '0%' },
    { title: 'Attendance Today', value: '94%', icon: Calendar, color: '#10b981', trend: '+1.5%' },
  ];

  const recentActivities = [
    { id: 1, type: 'Admission', user: 'Rahul Sharma', time: '2 hours ago', status: 'Approved' },
    { id: 2, type: 'Fee Payment', user: 'Priya Singh', time: '4 hours ago', status: 'Completed' },
    { id: 3, type: 'Exam Result', user: 'Class 10A', time: 'Yesterday', status: 'Published' },
    { id: 4, type: 'Assignment', user: 'Mr. Verma', time: 'Yesterday', status: 'Submitted' },
  ];

  return (
    <div className="dashboard-page">
      <div className="stats-grid">
        {stats.map((stat, i) => (
          <StatCard key={i} {...stat} />
        ))}
      </div>

      <div className="dashboard-grid">
        <div className="recent-activity card">
          <div className="card-header">
            <h3>Recent Activity</h3>
            <button className="text-btn">View All</button>
          </div>
          <div className="activity-list">
            {recentActivities.map((activity) => (
              <div key={activity.id} className="activity-item">
                <div className="activity-icon">
                  <Clock size={16} />
                </div>
                <div className="activity-info">
                  <p className="activity-desc">
                    <strong>{activity.type}</strong>: {activity.user}
                  </p>
                  <span className="activity-time">{activity.time}</span>
                </div>
                <span className={`status-badge ${activity.status.toLowerCase()}`}>
                  {activity.status}
                </span>
              </div>
            ))}
          </div>
        </div>

        <div className="quick-actions card">
          <h3>Quick Actions</h3>
          <div className="action-buttons">
            <button className="action-btn">New Student</button>
            <button className="action-btn">Mark Attendance</button>
            <button className="action-btn">Fee Invoice</button>
            <button className="action-btn">Exam Schedule</button>
          </div>
        </div>
      </div>

      <style jsx>{`
        .stats-grid {
          display: grid;
          grid-template-columns: repeat(auto-fit, minmax(240px, 1fr));
          gap: 24px;
          margin-bottom: 32px;
        }

        .dashboard-grid {
          display: grid;
          grid-template-columns: 2fr 1fr;
          gap: 24px;
        }

        .card-header {
          display: flex;
          justify-content: space-between;
          align-items: center;
          margin-bottom: 24px;
        }

        .text-btn {
          background: none;
          border: none;
          color: var(--primary);
          font-weight: 500;
          cursor: pointer;
        }

        .activity-item {
          display: flex;
          align-items: center;
          gap: 16px;
          padding: 16px 0;
          border-bottom: 1px solid var(--border);
        }

        .activity-item:last-child {
          border-bottom: none;
        }

        .activity-icon {
          width: 32px;
          height: 32px;
          border-radius: 50%;
          background: var(--bg-main);
          display: flex;
          align-items: center;
          justify-content: center;
          color: var(--text-muted);
        }

        .activity-info {
          flex: 1;
        }

        .activity-desc {
          font-size: 14px;
          margin-bottom: 4px;
        }

        .activity-time {
          font-size: 12px;
          color: var(--text-muted);
        }

        .status-badge {
          font-size: 12px;
          padding: 4px 12px;
          border-radius: 20px;
          font-weight: 500;
        }

        .status-badge.approved { background: #d1fae5; color: #059669; }
        .status-badge.completed { background: #dcfce7; color: #15803d; }
        .status-badge.published { background: #dbeafe; color: #2563eb; }
        .status-badge.submitted { background: #fef3c7; color: #d97706; }

        .action-buttons {
          margin-top: 24px;
          display: flex;
          flex-direction: column;
          gap: 12px;
        }

        .action-btn {
          width: 100%;
          padding: 12px;
          background: var(--bg-main);
          border: 1px solid var(--border);
          border-radius: 8px;
          text-align: left;
          font-weight: 500;
          cursor: pointer;
          transition: all 0.2s;
        }

        .action-btn:hover {
          background: var(--primary);
          color: white;
          border-color: var(--primary);
        }
      `}</style>
    </div>
  );
};

export default Dashboard;
