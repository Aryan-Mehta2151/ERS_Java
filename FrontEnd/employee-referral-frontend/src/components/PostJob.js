import React, { useState } from "react";
import axios from "axios";
import "../Styles/PostJob.css";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import confetti from 'canvas-confetti';


const PostJob = () => {
  const navigate = useNavigate();

  const [reqEmp, setReqEmp] = useState("");
  const [skills, setSkills] = useState([""]);
  const [jobRole, setJobRole] = useState("");
  const [minAts, setMinAts] = useState("");

  const handleSkillChange = (index, value) => {
    const newSkills = [...skills];
    newSkills[index] = value;
    setSkills(newSkills);
  };

  const addSkillField = () => {
    setSkills([...skills, ""]);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const jobDTO = {
      reqEmp: parseInt(reqEmp),
      skills: skills.filter((skill) => skill.trim() !== ""),
      jobRole,
      minAts: parseInt(minAts),
    };

    const token = localStorage.getItem("token");

    try {
      const response = await axios.post(
        "http://localhost:8197/api/job/create",
        jobDTO,
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        }
      );

      console.log("Job Created:", response.data);
      toast.success("🎉 Job posted successfully!");
       confetti({
            particleCount: 150,
            spread: 70,
            origin: { y: 0.6 },
          });
      setTimeout(() => navigate("/dashboard/hr"), 2000); // Delay for toast to show
    } catch (error) {
      console.error("Error creating job:", error);
      toast.error("❌ Failed to post job. Please try again.");
    }
  };

  return (
    <div className="post-job-container">
      <h2>🚀 Ready to Hire? Let's Create a Job Posting!</h2>
      <form className="post-job-form" onSubmit={handleSubmit}>
        <label>Required Employees</label>
        <input
          type="number"
          value={reqEmp}
          onChange={(e) => setReqEmp(e.target.value)}
          required
        />

        <label>Job Role</label>
        <input
          type="text"
          value={jobRole}
          onChange={(e) => setJobRole(e.target.value)}
          required
        />

        <label>Minimum ATS Score</label>
        <input
          type="number"
          value={minAts}
          onChange={(e) => setMinAts(e.target.value)}
          required
        />

        <label>Skills Required</label>
        {skills.map((skill, index) => (
          <input
            key={index}
            type="text"
            placeholder={`Skill ${index + 1}`}
            value={skill}
            onChange={(e) => handleSkillChange(index, e.target.value)}
            style={{ marginBottom: "8px" }}
          />
        ))}
        <button type="button" onClick={addSkillField}>
          ➕ Add Skill
        </button>

        <button type="submit" className="submit-btn">
          Post Job
        </button>
      </form>
    </div>
  );
};

export default PostJob;