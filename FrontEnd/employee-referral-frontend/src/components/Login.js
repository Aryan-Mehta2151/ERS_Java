// Login.js
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { jwtDecode } from "jwt-decode";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "../Styles/Login.css";

const API_BASE_URL = "http://localhost:8197/api/auth";

const Login = () => {
  const navigate = useNavigate();
  const [isLogin, setIsLogin] = useState(true);
  const [selectedRole, setSelectedRole] = useState("");
  const [formData, setFormData] = useState({
    username: "",
    password: "",
    email: "",
    role: "",
    imageUrl: "",
    phoneNumber: "",
    companyName: "",
    companyCity: "",
    companyEmail: "",
    companyPincode: "",
    resumeLink: "",
    resume: null,
    designation: "",
    department: "",
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleFileChange = (e) => {
    setFormData((prev) => ({
      ...prev,
      resume: e.target.files[0],
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (isLogin) {
      await handleLogin();
    } else {
      await handleRegister();
    }
  };

  const handleLogin = async () => {
    try {
      const response = await axios.post(`${API_BASE_URL}/login`, {
        username: formData.username,
        password: formData.password,
      });

      const accessToken = response.data.accessToken;
      localStorage.setItem("token", accessToken);

      const decoded = jwtDecode(accessToken);
      let role = decoded.role || decoded.roles?.[0] || decoded.authorities?.[0];

      if (role?.startsWith("ROLE_")) {
        role = role.replace("ROLE_", "");
      }

      console.log("Login successful. Role:", role);
      toast.success("Login successful!");

      switch (role) {
        case "HR":
          navigate("/dashboard/hr");
          break;
        case "EMPLOYEE":
          navigate("/dashboard/employee");
          break;
        case "APPLICANT":
          navigate("/dashboard/applicant");
          break;
        default:
          navigate("/home");
          break;
      }
    } catch (error) {
      console.error("Login error:", error.response?.data || error.message);
      toast.error("Login failed. Please check your credentials.");
    }
  };

  const handleRegister = async () => {
    try {
      const formDataToSend = new FormData();
      Object.keys(formData).forEach((key) => {
        if (formData[key]) {
          formDataToSend.append(key, formData[key]);
        }
      });

      formDataToSend.append("role", selectedRole);

      const response = await axios.post(`${API_BASE_URL}/register`, formDataToSend, {
        headers: { "Content-Type": "multipart/form-data" },
      });

      console.log("Registration successful:", response.data);
      toast.success("Registration successful! Please log in.");
      setIsLogin(true);
    } catch (error) {
      console.error("Registration error:", error.response?.data || error.message);
      toast.error("Registration failed. Please try again.");
    }
  };

  const renderRoleSpecificFields = () => {
    switch (selectedRole) {
      case "HR":
        return (
          <div className="role-fields">
            <input type="text" name="companyName" placeholder="Company Name" value={formData.companyName} onChange={handleInputChange} />
            <input type="text" name="companyCity" placeholder="Company City" value={formData.companyCity} onChange={handleInputChange} />
            <input type="email" name="companyEmail" placeholder="Company Email" value={formData.companyEmail} onChange={handleInputChange} />
            <input type="text" name="companyPincode" placeholder="Company Pincode" value={formData.companyPincode} onChange={handleInputChange} />
          </div>
        );
      case "APPLICANT":
        return (
          <div className="role-fields">
            <input type="text" name="resumeLink" placeholder="Resume Link" value={formData.resumeLink} onChange={handleInputChange} />
            <input type="file" name="resume" onChange={handleFileChange} accept=".pdf,.doc,.docx" />
          </div>
        );
      case "EMPLOYEE":
        return (
          <div className="role-fields">
            <input type="text" name="designation" placeholder="Designation" value={formData.designation} onChange={handleInputChange} />
            <input type="text" name="department" placeholder="Department" value={formData.department} onChange={handleInputChange} />
            <input type="text" name="companyName" placeholder="Company Name" value={formData.companyName} onChange={handleInputChange} />

          </div>
        );
      default:
        return null;
    }
  };

  return (
    <div className="login-container">
      <div className="login-card">
        <h2>{isLogin ? "Login" : "Sign Up"}</h2>
        <form onSubmit={handleSubmit}>
          <input type="text" name="username" placeholder="Username" value={formData.username} onChange={handleInputChange} required />
          <input type="password" name="password" placeholder="Password" value={formData.password} onChange={handleInputChange} required />

          {!isLogin && (
            <>
              <input type="email" name="email" placeholder="Email" value={formData.email} onChange={handleInputChange} required />
              <input type="text" name="phoneNumber" placeholder="Phone Number" value={formData.phoneNumber} onChange={handleInputChange} required />
              <input type="text" name="imageUrl" placeholder="Profile Image URL" value={formData.imageUrl} onChange={handleInputChange} />

              <div className="role-selection">
                <h3>Select Your Role</h3>
                <div className="role-buttons">
                  <button type="button" className={`role-btn ${selectedRole === "HR" ? "selected" : ""}`} onClick={() => setSelectedRole("HR")}>
                    HR
                  </button>
                  <button type="button" className={`role-btn ${selectedRole === "EMPLOYEE" ? "selected" : ""}`} onClick={() => setSelectedRole("EMPLOYEE")}>
                    Employee
                  </button>
                  <button type="button" className={`role-btn ${selectedRole === "APPLICANT" ? "selected" : ""}`} onClick={() => setSelectedRole("APPLICANT")}>
                    Applicant
                  </button>
                </div>
              </div>

              {renderRoleSpecificFields()}
            </>
          )}

          <button type="submit" className="submit-btn">{isLogin ? "Login" : "Sign Up"}</button>
        </form>

        <p className="toggle-form">
          {isLogin ? "Don't have an account?" : "Already have an account?"}
          <button type="button" className="toggle-btn" onClick={() => setIsLogin(!isLogin)}>
            {isLogin ? "Sign Up" : "Login"}
          </button>
        </p>
      </div>
    </div>
  );
};

export default Login;
