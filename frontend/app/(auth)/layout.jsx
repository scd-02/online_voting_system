import React from "react";

const AuthLayout = ({ children }) => {
  return <main className="auth min-h-screen flex items-center justify-center bg-gradient-to-br from-background to-secondary/20 p-4 ">{children}</main>;
};

export default AuthLayout;
