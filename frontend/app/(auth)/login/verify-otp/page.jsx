"use client";

import { useEffect, useState } from "react";
import axios from "axios";

export default function OTPVerification() {
    const [otp, setOtp] = useState("");
    const [isSubmitting, setIsSubmitting] = useState(false);

    // const [isAgree, setIsAgree] = useState(false);
    const [aadhaar, setAadhaar] = useState("");

    // Fetch Aadhaar number from localStorage on component mount
    useEffect(() => {
        const storedAadhaar = localStorage.getItem("aadhaarNumber");
        setAadhaar(storedAadhaar); // Read from localStorage after hydration
    }, []);

    useEffect(() => {
        const timeout = setTimeout(() => {
            window.location.href = "/login"; // Redirect to login after 2 minutes
        }, 2 * 60 * 1000);

        return () => clearTimeout(timeout);
    }, []);

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
            const response = await axios.get(`${API_URL}/otp/validate/${aadhaar}/${otp}`);
            console.log(response)

            if (response.data === "exists") {
                console.log("OTP verified successfully, redirecting to dashboard.");
                window.location.href = "/dashboard"; // Redirect to the dashboard page
            } else if (response.data === "new") {
                console.log("OTP verified successfully, redirecting to registration page.");
                window.location.href = "/register"; // Redirect to the registration page
            } else {
                alert("OTP verification failed. Please try again")
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
