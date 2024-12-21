"use client";

import { useState, useEffect } from "react";
import axios from "axios";

axios.defaults.withCredentials = true;

export default function VoterSignUp() {
  const [aadhaar, setAadhaar] = useState("");
  const [isAgree, setIsAgree] = useState(false);

  // Fetch Aadhaar number from localStorage on component mount
  useEffect(() => {
    const storedAadhaar = localStorage.getItem("aadhaarNumber");
    if (storedAadhaar) {
      setAadhaar(storedAadhaar);
    }
  }, []);

  const handleAgreeChange = (e) => {
    setIsAgree(e.target.checked);
  };

  const handleSignUp = async (e) => {
    e.preventDefault();

    if (!isAgree) {
      alert("You must agree to the terms and conditions to sign up as a voter.");
      return;
    }

    const API_URL = process.env.NEXT_PUBLIC_API_URL;
    try {
      const response = await axios.post(`${API_URL}/register/${aadhaar}`);
      if (response.status === 200) {
        console.log(response.data)
        window.location.href = "/user"; // Redirect to the dashboard page
        localStorage.removeItem("aadhaarNumber")
      }
    } catch (error) {
      console.log("error", error)
    }
  };

  return (
    <div className="flex items-center justify-center bg-gray-100">
      <div className="w-full max-w-md p-8 bg-white rounded shadow-md border border-gray-300">
        <h2 className="mb-6 text-2xl font-bold text-center">Voter Sign-Up</h2>

        <div className="mb-4">
          <label htmlFor="aadhaar" className="block mb-2 text-sm font-medium text-gray-700">
            Aadhaar Number
          </label>
          <input
            id="aadhaar"
            type="text"
            className="w-full px-4 py-2 border rounded-lg"
            value={aadhaar}
            disabled
          />
        </div>

        <div className="mb-6">
          <p className="text-sm text-gray-600">
            By signing up as a voter, you agree to follow the rules and regulations associated with voter registration. This includes complying with all terms set forth by the electoral body.
          </p>
        </div>

        <div className="flex items-center mb-6">
          <input
            id="agree-checkbox"
            type="checkbox"
            checked={isAgree}
            onChange={handleAgreeChange}
            className="mr-2"
          />
          <label htmlFor="agree-checkbox" className="text-sm text-gray-700">
            I agree to the terms and conditions to sign up as a voter.
          </label>
        </div>

        <button
          onClick={handleSignUp}
          className={`w-full px-4 py-2 font-bold text-white rounded ${isAgree ? "bg-blue-600 hover:bg-blue-700" : "bg-gray-400 cursor-not-allowed"}`}
          disabled={!isAgree}
        >
          Sign Up as a Voter
        </button>
      </div>
    </div>
  );
}
