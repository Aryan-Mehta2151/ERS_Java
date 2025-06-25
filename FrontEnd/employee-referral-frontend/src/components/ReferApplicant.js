import React from "react";
import { useLocation } from "react-router-dom";

const ReferApplicant = () => {
  const location = useLocation();
  const applicantId = location.state?.applicantId;
  const jobId = location.state?.jobId;

  return (
    <div style={{ padding: "40px", fontFamily: "Segoe UI, sans-serif" }}>
      <h2>Refer Applicant</h2>
      <p><strong>Applicant ID:</strong> {applicantId}</p>
      <p><strong>Job ID:</strong> {jobId}</p>

      {/* Add message form and submit API if needed later */}
    </div>
  );
};

export default ReferApplicant;
