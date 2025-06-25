import React, { useEffect, useState } from "react";
import axios from "axios";
import "../Styles/HRPostedJobs.css";
import ConfirmModal from "../components/ConfirmModal";
import UpdateJobModal from "../components/UpdateJobModal";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

const HRPostedJobs = () => {
  const [jobs, setJobs] = useState([]);
  const [error, setError] = useState("");
  const [editJob, setEditJob] = useState(null);
  const [showDeleteModal, setShowDeleteModal] = useState(false);
  const [jobToDelete, setJobToDelete] = useState(null);
  const [showUpdateModal, setShowUpdateModal] = useState(false);

  useEffect(() => {
    const fetchJobs = async () => {
      const token = localStorage.getItem("token");
      if (!token) {
        setError("Please log in to view your posted jobs.");
        return;
      }
      try {
        const response = await axios.get("http://localhost:8197/api/hr/my-posted-jobs", {
          headers: { Authorization: `Bearer ${token}` },
        });
        setJobs(response.data || []);
      } catch (error) {
        console.error("Error fetching jobs:", error);
        setError("Failed to load jobs. Please try again later.");
      }
    };
    fetchJobs();
  }, []);

  const handleEditClick = (job) => {
    setEditJob(job);
    setShowUpdateModal(true);
  };

  const handleDeleteClick = (job) => {
    setJobToDelete(job);
    setShowDeleteModal(true);
  };

  const confirmDelete = async () => {
    const token = localStorage.getItem("token");
    try {
      await axios.delete(`http://localhost:8197/api/job/delete/${jobToDelete.jobId}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setJobs(jobs.filter((job) => job.jobId !== jobToDelete.jobId));
      toast.success("Job deleted successfully.");
    } catch (error) {
      console.error("Error deleting job:", error);
      toast.error("Failed to delete job.");
    } finally {
      setShowDeleteModal(false);
      setJobToDelete(null);
    }
  };

  const cancelDelete = () => {
    setShowDeleteModal(false);
    setJobToDelete(null);
  };

  const handleUpdateJob = async (updatedJob) => {
    const token = localStorage.getItem("token");
    try {
      await axios.put(`http://localhost:8197/api/job/update/${updatedJob.jobId}`, updatedJob, {
        headers: { Authorization: `Bearer ${token}` },
      });

      setJobs((prevJobs) =>
        prevJobs.map((job) => (job.jobId === updatedJob.jobId ? updatedJob : job))
      );

      toast.success("Job updated successfully.");
      setEditJob(null);
      setShowUpdateModal(false);
    } catch (error) {
      console.error("Error updating job:", error);
      toast.error("Failed to update job.");
    }
  };

  return (
    <div className="posted-jobs-container">
      <h2>📄 Jobs You Posted</h2>
      {error && <p className="error-message">{error}</p>}
      {jobs.length === 0 && !error ? (
        <p>No jobs posted yet.</p>
      ) : (
        <div className="job-cards-wrapper">
          {jobs.map((job) => (
            <div key={job.jobId} className="job-card">
              <h3>{job.jobRole}</h3>
              <p><strong>Required Employees:</strong> {job.reqEmp}</p>
              <p><strong>Minimum ATS Score:</strong> {job.minAts}</p>
              <p><strong>Skills:</strong> {job.skills.join(", ")}</p>
              <div className="job-actions">
                <button onClick={() => handleEditClick(job)}>Update</button>
                <button onClick={() => handleDeleteClick(job)} className="delete-btn">Delete</button>
              </div>
            </div>
          ))}
        </div>
      )}

      {showUpdateModal && editJob && (
        <UpdateJobModal
          job={editJob}
          onClose={() => {
            setEditJob(null);
            setShowUpdateModal(false);
          }}
          onUpdate={handleUpdateJob}
        />
      )}

      {showDeleteModal && (
        <ConfirmModal
          message={`Are you sure you want to delete "${jobToDelete.jobRole}"?`}
          onConfirm={confirmDelete}
          onCancel={cancelDelete}
        />
      )}

      <ToastContainer position="top-center" autoClose={3000} hideProgressBar />
    </div>
  );
};

export default HRPostedJobs;
