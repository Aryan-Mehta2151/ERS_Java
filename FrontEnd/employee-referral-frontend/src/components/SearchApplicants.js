import React, { useEffect, useState } from "react";
import axios from "axios";
import "../Styles/SearchApplicants.css";
import { useLocation } from "react-router-dom";
import ReferModal from "../components/ReferModal";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

function SearchApplicants() {
  const [applicants, setApplicants] = useState([]);
  const [loading, setLoading] = useState(true);
  const [selectedApplicant, setSelectedApplicant] = useState(null);
  const [showModal, setShowModal] = useState(false);

  const location = useLocation();
  const requiredSkills = location.state?.skills || [];
  const jobId = location.state?.jobId;

  useEffect(() => {
    const fetchApplicants = async () => {
      try {
        const res = await axios.post(
          "http://localhost:8197/api/employee/search-applicants",
          { skills: requiredSkills },
          {
            headers: {
              Authorization: `Bearer ${localStorage.getItem("token")}`,
            },
          }
        );
        setApplicants(res.data);
      } catch (err) {
        console.error("Failed to fetch applicants:", err);
      } finally {
        setLoading(false);
      }
    };

    fetchApplicants();
  }, [requiredSkills]);

  const openReferModal = (applicantId) => {
    setSelectedApplicant(applicantId);
    setShowModal(true);
  };

  const submitReferral = async (applicantId, message) => {
    const token = localStorage.getItem("token");
    try {
      await axios.post(
        `http://localhost:8197/api/employee/refer/${applicantId}?jobId=${jobId}`,
        message,
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "text/plain"
          }
        }
      );
      toast.success("Referral submitted successfully!");
    } catch (error) {
      console.error("Error sending referral:", error);
      toast.error("You might have already referred this applicant.");
    } finally {
      setShowModal(false);
      setSelectedApplicant(null);
    }
  };

  return (
    <div className="search-applicants-container">
      <h2 className="search-applicants-title">Matching Applicants</h2>

      {loading ? (
        <p className="loading-text">Loading applicants...</p>
      ) : applicants.length === 0 ? (
        <p className="no-applicants-text">No matching applicants found.</p>
      ) : (
        <div className="applicant-card-grid">
          {applicants.map((applicant) => (
            <div key={applicant.id} className="applicant-card">
              <h3>{applicant.username}</h3>
              <p><strong>Email:</strong> {applicant.email}</p>
              <p><strong>Phone:</strong> {applicant.phoneNumber}</p>
              <p><strong>Resume:</strong> <a href={applicant.resumeLink} target="_blank" rel="noreferrer">View</a></p>

              {applicant.resumeData && (
                <div className="resume-section">
                  {applicant.resumeData.summary && (
                    <p><strong>Summary:</strong> {applicant.resumeData.summary}</p>
                  )}
                  {applicant.resumeData.skills?.length > 0 && (
                    <p><strong>Skills:</strong> {applicant.resumeData.skills.join(", ")}</p>
                  )}
                  {applicant.resumeData.experience && (
                    <p><strong>Experience:</strong> {applicant.resumeData.experience}</p>
                  )}
                  {applicant.resumeData.education && (
                    <p><strong>Education:</strong> {applicant.resumeData.education}</p>
                  )}
                  {applicant.resumeData.atsScore && (
                    <p><strong>ATS Score:</strong> {applicant.resumeData.atsScore}</p>
                  )}
                  {applicant.resumeData.projects && (
                    <p><strong>Projects:</strong> {applicant.resumeData.projects}</p>
                  )}
                  {applicant.resumeData.certifications && (
                    <p><strong>Certifications:</strong> {applicant.resumeData.certifications}</p>
                  )}
                </div>
              )}

              <button className="refer-button" onClick={() => openReferModal(applicant.id)}>
                Refer Applicant
              </button>
            </div>
          ))}
        </div>
      )}

      {/* Modal */}
      {showModal && (
        <ReferModal
          applicantId={selectedApplicant}
          onSubmit={submitReferral}
          onClose={() => setShowModal(false)}
        />
      )}

      <ToastContainer position="top-center" autoClose={3000} />
    </div>
  );
}

export default SearchApplicants;
