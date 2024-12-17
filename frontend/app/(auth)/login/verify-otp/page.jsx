"use client";

import { useState } from "react";
import axios from "axios";
import Cookies from "js-cookie";  // Import js-cookie to manage cookies

export default function OTPVerification() {
    const [otp, setOtp] = useState("");
    const [isSubmitting, setIsSubmitting] = useState(false);

    // Aadhaar number can be hardcoded or passed directly
    const aadhaar = "YOUR_AADHAAR_NUMBER";  // Replace with actual Aadhaar number

    const handleOtpChange = (e) => {
        const value = e.target.value;
        // Allow only numbers and limit the length to 6
        if (/^\d*$/.test(value) && value.length <= 6) {
            setOtp(value);
        }
    };

    const handleVerifyOTP = async (e) => {
        const API_URL = process.env.NEXT_PUBLIC_API_URL;

        e.preventDefault();

        if (otp.length !== 6) {
            alert("Please enter a 6-digit OTP.");
            return;
        }

        try {
            setIsSubmitting(true);
            const response = await axios.post(`${API_URL}/otp/validate/${aadhaar}/${otp}`);
            console.log(response.data);

            // Check the user status from the backend response
            if (response.data.user === "exist") {
                console.log("OTP verified successfully, redirecting to dashboard.");

                // Save the JSESSIONID cookie manually
                Cookies.set('JSESSIONID', response.data.sessionId, { expires: 2 / 24, path: '/', secure: true, sameSite: 'None' });
                // Redirect user to dashboard or other page
                window.location.href = "/user"; 
            } else if (response.data.user === "new") {
                console.log("OTP verified successfully, redirecting to registration page.");
                Cookies.set('JSESSIONID', response.data.sessionId, { expires: 2 / 24, path: '/', secure: true, sameSite: 'None' });
                window.location.href = "/register"; // Redirect to the registration page
            } else if (response.data.user === "wrong") {
                alert("OTP verification failed. Please try again");
            } else {
                // Optional: Handle other unexpected cases or errors in the response
                console.error("Unexpected response:", response.data);
                alert("An unexpected error occurred. Please try again later.");
            }
        } catch (error) {
            console.error("Error verifying OTP:", error);
            alert("An error occurred. Please try again.");
        } finally {
            setIsSubmitting(false);
        }
    };

    return (
        <div className="flex items-center justify-center bg-gray-100">
            <div className="w-full max-w-sm p-8 bg-white rounded shadow-md border border-gray-300">
                <h2 className="mb-6 text-2xl font-bold text-center">OTP Verification</h2>
                <form onSubmit={handleVerifyOTP}>
                    <div className="mb-4">
                        <label htmlFor="otp" className="block mb-2 text-sm font-medium text-gray-700">
                            Enter OTP
                        </label>
                        <input
                            id="otp"
                            type="text"
                            className="w-full px-4 py-2 border rounded-lg"
                            value={otp}
                            onChange={handleOtpChange}
                            maxLength={6}
                            pattern="\d*"
                            required
                        />
                    </div>
                    <button
                        type="submit"
                        className={`w-full px-4 py-2 font-bold text-white rounded ${isSubmitting ? "bg-gray-400" : "bg-blue-600 hover:bg-blue-700"
                            }`}
                        disabled={isSubmitting}
                    >
                        {isSubmitting ? "Verifying..." : "Verify OTP"}
                    </button>
                </form>
            </div>
        </div>
    );
}
