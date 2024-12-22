"use client";
import React from "react";
import { Fingerprint, List, Users, Shield, Zap } from "lucide-react";

const features = [
  {
    icon: <Fingerprint className="w-6 h-6" />,
    title: "Secure Authentication",
    description:
      "Authenticate securely using Aadhar number with OTP validation",
  },
  {
    icon: <List className="w-6 h-6" />,
    title: "Election Dashboard",
    description: "User-friendly interface displaying all available elections",
  },
  {
    icon: <Users className="w-6 h-6" />,
    title: "Role-Based Access",
    description:
      "Separate interfaces for voters and administrators with dedicated admin dashboard",
  },
  {
    icon: <Shield className="w-6 h-6" />,
    title: "Administrative Control",
    description:
      "Exclusive admin access to manage parties, candidates, elections, and API documentation",
  },
  {
    icon: <Zap className="w-6 h-6" />,
    title: "High Performance",
    description:
      "Advanced caching mechanism for faster vote processing from multiple users",
  },
];

export function Features() {
  return (
    <div className="py-16 px-4">
      <div className="max-w-7xl mx-auto">
        <h2 className="text-3xl font-bold text-blue-900 text-center mb-12">
          Key Features
        </h2>
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
          {features.map((feature, index) => (
            <div
              key={index}
              className="bg-white p-6 rounded-xl shadow-md hover:shadow-lg transition-shadow duration-300"
            >
              <div className="flex items-center mb-4">
                <div className="p-2 bg-blue-100 rounded-lg text-blue-600">
                  {feature.icon}
                </div>
              </div>
              <h3 className="text-xl font-semibold text-blue-900 mb-2">
                {feature.title}
              </h3>
              <p className="text-blue-700/80">{feature.description}</p>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
