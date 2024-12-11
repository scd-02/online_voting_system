"use client";

import { Avatar, AvatarImage, AvatarFallback } from "@/components/ui/avatar";
import { Mail, Phone, Calendar, Shield } from "lucide-react";

export function Sidebar({ user }) {
  return (
    <div className="w-80 h-screen border-r bg-card p-6">
      <div className="flex flex-col items-center space-y-4">
        <Avatar className="h-24 w-24">
          <AvatarImage src={user.avatar} alt={user.name} />
          <AvatarFallback>{user.name.charAt(0)}</AvatarFallback>
        </Avatar>
        <div className="text-center">
          <h2 className="text-xl font-semibold">{user.name}</h2>
          <p className="text-sm text-muted-foreground">{user.role}</p>
        </div>
      </div>

      <div className="mt-8 space-y-4">
        <div className="flex items-center space-x-3">
          <Mail className="h-5 w-5 text-muted-foreground" />
          <span className="text-sm">{user.email}</span>
        </div>
        <div className="flex items-center space-x-3">
          <Phone className="h-5 w-5 text-muted-foreground" />
          <span className="text-sm">{user.phone}</span>
        </div>
        <div className="flex items-center space-x-3">
          <Calendar className="h-5 w-5 text-muted-foreground" />
          <span className="text-sm">Joined {user.joinDate}</span>
        </div>
        <div className="flex items-center space-x-3">
          <Shield className="h-5 w-5 text-muted-foreground" />
          <span className="text-sm">{user.role}</span>
        </div>
      </div>
    </div>
  );
}
