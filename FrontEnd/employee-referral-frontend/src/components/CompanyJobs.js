import React, { useEffect, useState } from "react";
import axios from "axios";
import "../Styles/CompanyJobs.css";
import { useNavigate } from "react-router-dom";

const CompanyJobs = () => {
  const [jobs, setJobs] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    fetchCompanyJobs();
  }, []);

  const fetchCompanyJobs = async () => {
    try {
      const token = localStorage.getItem("token");
      const response = await axios.get("http://localhost:8197/api/job/company-jobs", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setJobs(response.data);
    } catch (error) {
      console.error("Error fetching jobs:", error);
      alert("Failed to fetch company jobs");
    }
  };

  const handleSearchApplicants = (job) => {
    navigate("/employee/search-applicants", {
      state: {
        jobId: job.id,          // ✅ this is your jobId
        skills: job.skills      // ✅ sending skills for applicant filtering
      }
    });
  };

  return (
    <div className="company-jobs-container">
      <h2 className="company-jobs-title">Jobs in Your Company</h2>
      {jobs.length === 0 ? (
        <p className="no-jobs">No jobs posted yet.</p>
      ) : (
        <div className="job-card-grid">
          {jobs.map((job) => (
            <div key={job.id} className="job-card">
              <h3>{job.jobRole}</h3>
              <p><strong>Required Employees:</strong> {job.reqEmp}</p>
              <p><strong>Minimum ATS:</strong> {job.minAts}</p>
              <p><strong>Skills:</strong> {job.skills.join(", ")}</p>
              <button className="search-btn" onClick={() => handleSearchApplicants(job)}>
                Search Applicants
              </button>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default CompanyJobs;
