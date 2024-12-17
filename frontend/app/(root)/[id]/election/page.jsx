"use client";
import React, { useState } from "react";
import { Mail, Lock, LogIn } from "lucide-react";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { LoginHeader } from "@/components/shared/LoginHeader";
import { Select } from "@/components/shared/Select";

export default function LoginForm() {
  // const { formData, handleSubmit, handleChange } = useLoginForm();
  const ROLE_OPTIONS = [
    { value: "student", label: "Student" },
    { value: "teacher", label: "Teacher" },
    { value: "admin", label: "Administrator" },
  ];
  const [formData, setFormData] = useState({
    email: "",
    password: "",
    role: "student",
  });

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log("Form submitted:", formData);
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-50 flex items-center justify-center p-4">
      <div className="w-full max-w-md p-8 bg-white rounded-2xl shadow-xl border border-gray-100">
        <LoginHeader />

        <form onSubmit={handleSubmit} className="space-y-6">
          <Input
            label="Email"
            icon={Mail}
            type="email"
            id="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
            placeholder="Enter your email"
            required
            autoComplete="email"
          />

          <Input
            label="Password"
            icon={Lock}
            type="password"
            id="password"
            name="password"
            value={formData.password}
            onChange={handleChange}
            placeholder="Enter your password"
            required
            autoComplete="current-password"
          />

          <Select
            label="Role"
            id="role"
            name="role"
            value={formData.role}
            onChange={handleChange}
            options={ROLE_OPTIONS}
          />

          <Select
            label="Role"
            id="role"
            name="role"
            value={formData.role}
            onChange={handleChange}
            options={ROLE_OPTIONS}
          />

          <Button type="submit" icon={LogIn}>
            Sign in
          </Button>

          <div className="text-center text-sm text-gray-500">
            <p>Protected by reCAPTCHA and subject to our</p>
            <div className="space-x-1">
              <a href="#" className="text-blue-600 hover:underline">
                Privacy Policy
              </a>
              <span>and</span>
              <a href="#" className="text-blue-600 hover:underline">
                Terms of Service
              </a>
            </div>
          </div>
        </form>
      </div>
    </div>
  );
}
