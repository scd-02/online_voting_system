"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";
import Link from "next/link";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import {
  Card,
  CardHeader,
  CardContent,
  CardFooter,
} from "@/components/ui/card";

import { Label } from "@/components/ui/label";
import { User, Mail, Lock, Phone } from "lucide-react";
import OTP from "@/components/shared/OTP";

export default function RegisterPage() {
  const router = useRouter();
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    password: "",
    phone: "",
  });

  const [sendOtpStatus, setSendOtpStatus] = useState(false);
  const [otpValue, setOtpValue] = useState("");
  const [otpVerificationStatus, setOtpVerificationStatus] = useState(false);

  const handleSubmit = async () => {
    e.preventDefault();
    // TODO: Implement actual registration logic
    console.log("Registration attempt:", formData);
  };

  const handleOTPVerification = (otp) => {
    setOtpVerificationStatus(true);
    // TODO: Implement actual OTP verification logic
  };

  const handleChange = () => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  return (
    <Card className="w-full max-w-md">
      <CardHeader className="space-y-1 text-center">
        <h2 className="text-2xl font-bold">Create an account</h2>
        <p className="text-muted-foreground">
          Enter your details to create your account
        </p>
      </CardHeader>
      <form onSubmit={handleSubmit}>
        <CardContent className="space-y-4">
          <div className="space-y-2">
            <Label htmlFor="name">Full Name</Label>
            <div className="relative">
              <User className="absolute left-3 top-3 h-4 w-4 text-muted-foreground" />
              <Input
                id="name"
                name="name"
                placeholder="John Doe"
                className="pl-10"
                value={formData.name}
                onChange={handleChange}
                required
              />
            </div>
          </div>
          <div className="space-y-2">
            <Label htmlFor="email">Email</Label>
            <div className="relative">
              <Mail className="absolute left-3 top-3 h-4 w-4 text-muted-foreground" />
              <Input
                id="email"
                name="email"
                type="email"
                placeholder="name@example.com"
                className="pl-10"
                value={formData.email}
                onChange={handleChange}
                required
              />
            </div>
          </div>
          <div className="space-y-2">
            <Label htmlFor="password">Password</Label>
            <div className="relative">
              <Lock className="absolute left-3 top-3 h-4 w-4 text-muted-foreground" />
              <Input
                id="password"
                name="password"
                type="password"
                className="pl-10"
                value={formData.password}
                onChange={handleChange}
                required
              />
            </div>
          </div>
          <div className="space-y-2">
            <Label htmlFor="phone">Phone Number</Label>
            <div className="relative flex gap-2">
              <Phone className="absolute left-3 top-3 h-4 w-4 text-muted-foreground" />
              <Input
                id="phone"
                name="phone"
                type="tel"
                placeholder="(123) 456-7890"
                className="pl-10"
                value={formData.phone}
                onChange={handleChange}
                required
              />
              <Button onClick={() => setSendOtpStatus(true)}>
                {sendOtpStatus ? "success" : "Send OTP"}
              </Button>
            </div>
            
          </div>

          {sendOtpStatus && (
              <div>
                <div className="flex items-center gap-1">
                  <OTP value={otpValue} setValue={setOtpValue} />
                  <Button
                    disabled={otpValue.length !== 6 && !otpVerificationStatus}
                    className={`relative flex h-12 w-full items-center justify-center ${
                      otpValue.length !== 6 ? "bg-gray-500" : "bg-black"
                    } text-white border rounded-md`}
                    onClick={() => handleOTPVerification(otpValue)}
                  >
                    verify
                  </Button>
                </div>
                {otpVerificationStatus && (
                  <div className="bg-green-500 border rounded-sm p-2 mt-2 text-center text-gray-50">Verified</div>
                )}
              </div>
            )}
        </CardContent>
        <CardFooter className="flex flex-col space-y-4">
          <Button type="submit" className="w-full">
            Create account
          </Button>
          <p className="text-center text-sm text-muted-foreground">
            Already have an account?{" "}
            <Link
              href="/login"
              className="text-primary underline-offset-4 hover:underline"
            >
              Sign in
            </Link>
          </p>
        </CardFooter>
      </form>
    </Card>
  );
}
