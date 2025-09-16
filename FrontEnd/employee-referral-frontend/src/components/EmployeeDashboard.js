import React from "react";
import { useNavigate } from "react-router-dom";
import "../Styles/EmployeeDashboard.css";

const EmployeeDashboard = () => {
  const navigate = useNavigate();

  return (
    <div className="employee-dashboard">
      <h1 className="dashboard-title">Employee Dashboard</h1>

      <div className="dashboard-cards">
        <div className="dashboard-card gradient-blue" onClick={() => navigate("/employee/company-jobs")}>
          <h2>View Jobs at My Company</h2>
          <p>Explore open positions and refer applicants.</p>
        </div>

        <div className="dashboard-card gradient-green" onClick={() => navigate("/employee/my-referrals")}>
          <h2>My Referrals</h2>
          <p>Track the applicants you've referred so far.</p>
        </div>

        <div className="dashboard-card gradient-purple" onClick={() => navigate("/employee/edit-profile")}>
          <h2>Edit Profile</h2>
          <p>Update your information and settings.</p>
        </div>

        <div className="dashboard-card gradient-orange" onClick={() => navigate("/employee/messages")}>
          <h2>Messages / Notifications</h2>
          <p>Check updates from HRs and admins.</p>
        </div>
      </div>
    </div>
  );
};

export default EmployeeDashboard;
