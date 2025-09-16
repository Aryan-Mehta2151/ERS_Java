import React, { useState } from "react";
import "../Styles/ReferModal.css";

const ReferModal = ({ onClose, onSubmit, applicantId }) => {
  const [message, setMessage] = useState("");

  const handleSubmit = () => {
    if (message.trim() === "") return;
    onSubmit(applicantId, message);
  };

  return (
    <div className="modal-overlay">
      <div className="modal">
        <h3>Refer Applicant</h3>
        <textarea
          placeholder="Enter your message to HR"
          value={message}
          onChange={(e) => setMessage(e.target.value)}
        ></textarea>
        <div className="modal-buttons">
          <button className="cancel-btn" onClick={onClose}>Cancel</button>
          <button className="confirm-btn" onClick={handleSubmit}>Submit</button>
        </div>
      </div>
    </div>
  );
};

export default ReferModal;
