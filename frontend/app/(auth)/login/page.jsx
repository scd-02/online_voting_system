"use client";

import { useState } from "react";
import axios from "axios";

axios.defaults.withCredentials = true;

export default function Login() {
  const [aadhaar, setAadhaar] = useState("");

  const handleLogin = async (e) => {
    e.preventDefault();
    const API_URL = process.env.NEXT_PUBLIC_API_URL;

    try {
      const response = await axios.post(`${API_URL}/otp/send/${aadhaar}`);

      if (response.status === 200) {
        // Cache Aadhaar number
        localStorage.setItem("aadhaarNumber", aadhaar);

        console.log("OTP sent successfully, redirecting to verification page.");

        // Redirect
        window.location.href = "/login/verify-otp";
      } else {
        alert("Failed to send OTP. Please try again.");
      }
    } catch (error) {
      console.error("Error sending OTP:", error);
      alert("An error occurred. Please try again later.");
    }
  };

  const handleAadhaarChange = (e) => {
    const value = e.target.value;
    // Restrict input to 12 characters max and only allow numbers
    if (value.length <= 12 && /^\d*$/.test(value)) {
      setAadhaar(value);
    }
  };

  return (
    <div className="flex items-center justify-center bg-gray-100">
      <div className="w-full max-w-sm p-8 bg-white rounded shadow-md border border-gray-300">
        <h2 className="mb-6 text-2xl font-bold text-center">Login</h2>
        <form onSubmit={handleLogin}>
          <div className="mb-4">
            <label htmlFor="aadhaar" className="block mb-2 text-sm font-medium text-gray-700">
              Aadhaar Number
            </label>
            <input
              id="aadhaar"
              type="text"
              className="w-full p-2 border border-gray-300 rounded"
              value={aadhaar}
              onChange={handleAadhaarChange}
              placeholder="Enter Aadhaar Number"
              maxLength={12} // Ensures that the input is limited to 12 characters
              required
            />
          </div>
          <button
            type="submit"
            className="w-full px-4 py-2 font-bold text-white bg-blue-600 rounded hover:bg-blue-700"
          >
            Login
          </button>
        </form>
      </div>
    </div>
  );
}
