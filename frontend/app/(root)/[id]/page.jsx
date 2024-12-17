// Add the "use client" directive to mark the component as a Client Component
"use client";

import { useParams } from "next/navigation"; // Correct import from next/navigation for dynamic params
import { ContestList, ImageGallery } from "@/components/shared/ContestList";
import { Sidebar } from "@/components/shared/SideBar";
import React, { useEffect, useState } from "react";
import ElectionTileView from "@/components/shared/EledtionTileView";

const UserDetailPage = () => {
  const { id } = useParams(); // Get the dynamic id from the URL using useParams()
  const [userData, setUserData] = useState(null);

  // Mock function to simulate fetching data based on id
  useEffect(() => {
    if (id) {
      // Simulate fetching user data (this could be an API call)
      const user = {
        aadhaarNumber: "1234-5678-9876",
        fullName: "John Doe",
        gender: "Male",
        dateOfBirth: "1990-01-01",
        addressLine1: "123 Main St",
        addressLine2: "Apt 4B",
        city: "Sample City",
        state: "State",
        pinCode: "123456",
        country: "Country",
        photograph:
          "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e",
        mobileNumber: "(123) 456-7890",
        emailAddress: "john@example.com",
        fatherName: "Father Name",
        motherName: "Mother Name",
        residenceType: "Own",
      };

      // Set the user data
      setUserData(user);
    }
  }, [id]);

  if (!userData) {
    return <div>Loading...</div>; // Show loading state until userData is fetched
  }

  return (
    <div className="flex min-w-full min-h-screen bg-background">
      <Sidebar user={userData} />
      <div className="w-2/3 overflow-auto p-4">
        {/* <ContestList /> */}
        {/* <ImageGalleryallery /> */}
        <ElectionTileView />
      </div>
    </div>
  );
};

export default UserDetailPage;
