import React from 'react';
import { User } from 'lucide-react';

export function LoginHeader() {
  return (
    <div className="flex flex-col items-center mb-8">
      <div className="p-4 bg-blue-100 rounded-full mb-4">
        <User className="w-8 h-8 text-blue-600" />
      </div>
      <h2 className="text-2xl font-bold text-gray-900">Welcome back</h2>
      <p className="text-gray-600 mt-2">Please enter your details</p>
    </div>
  );
}