"use client";

import { useState } from "react";
import axios from "axios";

export default function Login() {
  const [aadhaar, setAadhaar] = useState("");

  const handleLogin = async (e) => {
    e.preventDefault();
    const API_URL = process.env.NEXT_PUBLIC_API_URL;

    try {
      const response = await axios.get(`${API_URL}/auth/checkAadhaar/${aadhaar}`);
      const data = response.data;

      if (data.exists) {
        // Redirect to the next page or perform other actions
        console.log("User exists, ID:", data);
        // alert("Login successful! Redirecting...");
        // window.location.href = "/nextPage"; // Replace with your actual page
      } else {
        alert("User does not exist.");
      }
    } catch (error) {
      console.error("Error checking Aadhaar:", error);
      alert("Login failed. Please try again.");
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
              className="w-full px-4 py-2 border rounded-lg"
              value={aadhaar}
              onChange={(e) => setAadhaar(e.target.value)}
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
