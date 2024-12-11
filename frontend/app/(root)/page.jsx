import { ContestList, ImageGallery } from "@/components/shared/ContestList";
import { Sidebar } from "@/components/shared/SideBar";
import React from "react";

const Home = () => {
  const user ={
    name: "John Doe",
    email: "john@example.com",
    phone: "(123) 456-7890",
    avatar: "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e",
    role: "Premium Member",
    joinDate: "April 2024"
  };

  return (
    <div className="flex min-h-screen bg-background">
      <Sidebar user={user} />
      <main className="flex-1 overflow-auto">
        <ContestList />
      </main>
    </div>
  );
};

export default Home;
