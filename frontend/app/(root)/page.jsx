"use client";
import React from "react";
import { LogIn } from "lucide-react";
import { Features } from "@/components/shared/Features";
import Image from "next/image";

export default function Home() {
  const handleClick = () => {
    window.location.href = "/login";
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-white via-blue-50 to-blue-100">
      <div className="container mx-auto px-4">
        {/* Hero Section */}

        <div className="px-16 flex flex-row justify-between items-center py-8 gap-0">
          <section className="pl-16">
            <Image src={"/logo.png"} width={350} height={350} alt="logo"/>
          </section>
          <section>
            <div className="h-screen flex flex-col items-start justify-center">
              <div className="pr-16 mb-12">
                <h1 className="pb-5 text-9xl text-left font-bold text-blue-900 mb-4">
                  VoteX
                </h1>
                <h1 className="text-5xl font-bold text-blue-900 mb-4">
                  Welcome to E-Voting System
                </h1>
                <p className="text-xl text-blue-700/80">
                  Secure, Transparent, and Efficient Voting Platform
                </p>
              </div>

              <button
                className="group bg-blue-600 hover:bg-blue-700 text-white px-8 py-3 rounded-lg 
            shadow-lg transition-all duration-300 hover:shadow-xl flex items-center 
            space-x-2 font-semibold"
                onClick={() => handleClick()}
              >
                <span>Login</span>
                <LogIn className="w-5 h-5 group-hover:translate-x-1 transition-transform duration-300" />
              </button>
            </div>
          </section>
        </div>

        {/* Features Section */}
        <Features />

        <div className="py-8 text-center text-blue-600/60">
          <p>© 2024 E-Voting System. All rights reserved.</p>
        </div>
      </div>
    </div>
  );
}
