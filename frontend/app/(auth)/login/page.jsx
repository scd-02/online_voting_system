"use client";

import { useState } from "react";
import axios from "axios";

export default function Login() {
  const [aadhaar, setAadhaar] = useState("");

  const handleLogin = async (e) => {
    e.preventDefault(); // Prevent default form submission
    const API_URL = process.env.NEXT_PUBLIC_API_URL; // Environment variable for backend URL

    const token = `admin:admin`;
    const encodedToken = Buffer.from(token).toString('base64');
    var config = {
      method: 'get',
      // url: API_URL + `/users/${aadhaar}`,
      url: API_URL + `/users/123456789012`,
      headers: { 'Authorization': 'Basic ' + encodedToken }
    };
    console.log("Config:", config);
    console.log(API_URL);
    axios(config)
      .then(function (response) {
        console.log(JSON.stringify(response.data));
      })
      .catch(function (error) {
        console.log(error);
      });
  };

  return (
    <div className="flex items-center justify-center bg-gray-100">
      <div className="w-full max-w-sm p-8 bg-white rounded shadow-md border border-e-gray-300">
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
