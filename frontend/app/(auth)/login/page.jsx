"use client"

import { useState } from "react";
import axios from "axios";

export default function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const handleLogin = async (e) => {
    e.preventDefault(); // Prevent default form submission
    const API_URL = process.env.NEXT_PUBLIC_API_URL; // Environment variable for backend URL

    const token = `admin:admin`;
    const encodedToken = Buffer.from(token).toString('base64');
    var config = {
      method: 'get',
      url: API_URL + '/users/123456789012',
      headers: { 'Authorization': 'Basic ' + encodedToken }
    };

    axios(config)
      .then(function (response) {
        console.log(JSON.stringify(response.data));
      })
      .catch(function (error) {
        console.log(error);
      });
    // try {
    //   // const response = await axios.get(`${API_URL}/users/123456789012`, {
    //   //   auth: {
    //   //     username: "admin",
    //   //     password: "admin"
    //   //   }
    //   // });
      
    //   console.log("Login response:", response);
    //   if (response.status === 200) {
    //     alert("Login successful!");
    //     // Redirect to home or other pages
    //     window.location.href = "/home";
    //   }
    // } catch (error) {
    //   console.error("Error during login:", error);
    //   alert("Invalid credentials, please try again.");
    // }
  };

  return (
    <div className="flex items-center justify-center min-h-screen bg-gray-100">
      <div className="w-full max-w-sm p-8 bg-white rounded shadow-md">
        <h2 className="mb-6 text-2xl font-bold text-center">Login</h2>
        <form onSubmit={handleLogin}>
          <div className="mb-4">
            <label htmlFor="username" className="block mb-2 text-sm font-medium text-gray-700">
              Username
            </label>
            <input
              id="username"
              type="text"
              className="w-full px-4 py-2 border rounded-lg"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              required
            />
          </div>
          <div className="mb-4">
            <label htmlFor="password" className="block mb-2 text-sm font-medium text-gray-700">
              Password
            </label>
            <input
              id="password"
              type="password"
              className="w-full px-4 py-2 border rounded-lg"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
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
