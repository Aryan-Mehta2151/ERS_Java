import React from "react";
import "../Styles/HrDashboard.css";
import { useNavigate } from "react-router-dom";

const HRDashboard = () => {
  const navigate = useNavigate();

  const jobsPosted = 12;
  const applicantsReceived = 47;
  const interviewsScheduled = 9;

  return (
    <div className="hr-dashboard-container">
      <header className="hr-dashboard-header">
        <div>
          <h1>Welcome, HR Manager 👋</h1>
          <p className="hr-dashboard-subtext">
            Here’s your hiring overview. Let’s build your dream team! 💼
          </p>
        </div>
        <button className="logout-btn" onClick={() => navigate("/")}>
          Logout
        </button>
      </header>

      <section className="hr-summary-section">
        <div className="hr-summary-cards">
          <div className="hr-summary-card">
            <h3>{jobsPosted}</h3>
            <p>Jobs Posted</p>
          </div>
          <div className="hr-summary-card">
            <h3>{applicantsReceived}</h3>
            <p>Applicants Received</p>
          </div>
          <div className="hr-summary-card">
            <h3>{interviewsScheduled}</h3>
            <p>Interviews Scheduled</p>
          </div>
        </div>
      </section>

      <h2 className="section-title">Quick Actions</h2>
      <div className="hr-action-cards">
        <div className="hr-action-card" onClick={() => navigate("/post-job")}>
          <h2>📢 Post New Job</h2>
          <p>Create and manage job openings for your company.</p>
        </div>

        <div className="hr-action-card" onClick={() => navigate("/view-applicants")}>
          <h2>👥 View Applicants</h2>
          <p>Browse and filter applicants for open positions.</p>
        </div>

        <div className="hr-action-card" onClick={() => navigate("/interview-schedule")}>
          <h2>🗓️ Schedule Interviews</h2>
          <p>Coordinate interview schedules with applicants.</p>
        </div>

        <div className="hr-action-card" onClick={() => navigate("/referral-requests")}>
          <h2>🔗 Referral Requests</h2>
          <p>Manage incoming referrals from employees.</p>
        </div>

        {/* ✅ Updated Path */}
        <div className="hr-action-card" onClick={() => navigate("/posted-jobs")}>
          <h2>📋 View Posted Jobs</h2>
          <p>See all the jobs you’ve posted</p>
        </div>
      </div>
    </div>
  );
};

export default HRDashboard;
