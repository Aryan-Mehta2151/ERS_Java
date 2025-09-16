// App.js
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Login from "./components/Login";
import HomePage from "./components/HomePage";
import HRDashboard from "./components/HRDashboard";
import EmployeeDashboard from "./components/EmployeeDashboard";
import ApplicantDashboard from "./components/ApplicantDashboard";
import PostJob from "./components/PostJob";
import HRPostedJobs from "./components/HRPostedJobs";
import CompanyJobs from './components/CompanyJobs';
import SearchApplicants from "./components/SearchApplicants";
import ReferApplicant from "./components/ReferApplicant";

// Toastify setup
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

function App() {
  return (
    <Router>
      <ToastContainer
        position="top-center"
        autoClose={3000}
        hideProgressBar={false}
        newestOnTop={true}
        closeOnClick
        rtl={false}
        pauseOnFocusLoss
        draggable
        pauseOnHover
        theme="colored"
      />

      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/login" element={<Login />} />
        <Route path="/dashboard/hr" element={<HRDashboard />} />
        <Route path="/dashboard/employee" element={<EmployeeDashboard />} />
        <Route path="/employee/search-applicants" element={<SearchApplicants />} />
        <Route path="/refer" element={<ReferApplicant />} />
        <Route path="/dashboard/applicant" element={<ApplicantDashboard />} />
        <Route path="/post-job" element={<PostJob />} />
        <Route path="/posted-jobs" element={<HRPostedJobs />} />

        <Route path="/employee/company-jobs" element={<CompanyJobs />} />

      </Routes>
    </Router>
  );
}

export default App;
