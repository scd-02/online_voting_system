"use client";

import { Avatar, AvatarImage, AvatarFallback } from "@/components/ui/avatar";
import { Mail, Phone, Calendar, Home, User } from "lucide-react";

export function Sidebar({ user }) {
  return (
    <div className="w-80 h-full">
      <div className="flex flex-col items-center space-y-4">
        {/* Avatar */}
        <Avatar className="h-24 w-24">
          <AvatarImage src={user.photograph} alt={user.fullName} />
          <AvatarFallback>{user.fullName.charAt(0)}</AvatarFallback>
        </Avatar>
        <div className="text-center">
          <h2 className="text-xl font-semibold">{user.fullName}</h2>
        </div>
      </div>

      {/* User Details */}
      <div className="mt-8 space-y-4">
        {/* Email */}
        <div className="flex items-center space-x-3">
          <Mail className="h-5 w-5 text-muted-foreground" />
          <span className="text-sm">{user.emailAddress}</span>
        </div>

        {/* Phone */}
        <div className="flex items-center space-x-3">
          <Phone className="h-5 w-5 text-muted-foreground" />
          <span className="text-sm">{user.mobileNumber}</span>
        </div>

        {/* Date of Birth */}
        <div className="flex items-center space-x-3">
          <Calendar className="h-5 w-5 text-muted-foreground" />
          <span className="text-sm">Date of Birth: {user.dateOfBirth}</span>
        </div>

        {/* Address */}
        <div className="flex items-center space-x-3">
          <Home className="h-5 w-5 text-muted-foreground" />
          <div>
            <span className="text-sm">
              Address:<br /> {user.addressLine1}, <br />{user.addressLine2}, <br />{user.city}, {user.state} - {user.pinCode}
            </span>
            <br />
            <span className="text-sm">Residence Type: {user.residenceType}</span>
          </div>
        </div>

        {/* Father's Name */}
        <div className="flex items-center space-x-3">
          <User className="h-5 w-5 text-muted-foreground" />
          <span className="text-sm">Father's Name: {user.fatherName}</span>
        </div>

        {/* Mother's Name */}
        <div className="flex items-center space-x-3">
          <User className="h-5 w-5 text-muted-foreground" />
          <span className="text-sm">Mother's Name: {user.motherName}</span>
        </div>

      </div>
    </div>
  );
}
