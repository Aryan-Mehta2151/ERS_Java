import axios from "axios";

const API_BASE_URL = "http://localhost:8197/api/auth"; // Replace with your actual backend URL

export const registerUser = async (userData) => {
  try {
    const response = await axios.post(`${API_BASE_URL}/register`, userData);
    return response.data;
  } catch (error) {
    console.error("Registration error:", error);
    throw error;
  }
};
