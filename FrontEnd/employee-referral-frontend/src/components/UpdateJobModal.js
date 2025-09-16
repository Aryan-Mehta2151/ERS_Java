import React, { useState, useEffect } from "react";
import "../Styles/UpdateJobModal.css";

const UpdateJobModal = ({ job, onUpdate, onClose }) => {
  const [formData, setFormData] = useState({
    jobRole: "",
    reqEmp: "",
    minAts: "",
    skills: "",
  });

  useEffect(() => {
    if (job) {
      setFormData({
        jobRole: job.jobRole,
        reqEmp: job.reqEmp,
        minAts: job.minAts,
        skills: job.skills.join(", "),
      });
    }
  }, [job]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const updatedJob = {
      ...job,
      jobRole: formData.jobRole,
      reqEmp: parseInt(formData.reqEmp),
      minAts: parseFloat(formData.minAts),
      skills: formData.skills.split(",").map((s) => s.trim()),
    };
    onUpdate(updatedJob);
  };

  return (
    <div className="update-modal-overlay">
      <div className="update-modal-content">
        <h3>Edit Job: {job.jobRole}</h3>
        <form onSubmit={handleSubmit}>
          <input
            type="text"
            name="jobRole"
            value={formData.jobRole}
            onChange={handleChange}
            placeholder="Job Role"
            required
          />
          <input
            type="number"
            name="reqEmp"
            value={formData.reqEmp}
            onChange={handleChange}
            placeholder="Required Employees"
            required
          />
          <input
            type="number"
            name="minAts"
            value={formData.minAts}
            onChange={handleChange}
            placeholder="Minimum ATS"
            step="0.1"
            required
          />
          <input
            type="text"
            name="skills"
            value={formData.skills}
            onChange={handleChange}
            placeholder="Skills (comma-separated)"
            required
          />
          <div className="update-modal-buttons">
            <button type="submit" className="save-btn">Save</button>
            <button type="button" className="cancel-btn" onClick={onClose}>Cancel</button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default UpdateJobModal;
