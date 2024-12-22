"use client";

import { Sidebar } from "@/components/shared/SideBar";
import React, { useEffect, useState } from "react";
import axios from "axios";
import ElectionTileView from "@/components/shared/ElectionTileView";

// Set axios to send cookies with requests
axios.defaults.withCredentials = true;

const UserDetailPage = () => {
  const [userData, setUserData] = useState(null);
  const [profileData, setProfileData] = useState(null); // New state for profile data
  const [isLoading, setIsLoading] = useState(true);
  const [aadhaar, setAadhaar] = useState(null); // State for Aadhaar number
  const [eligibleElections, setEligibleElections] = useState([]); // <-- Add this line

  useEffect(() => {
    const fetchProfileData = async () => {
      try {
        const API_URL = process.env.NEXT_PUBLIC_API_URL;
        setIsLoading(true);

        // Fetch profile data (for logged-in user)
        const profileResponse = await axios.get(`${API_URL}/auth/profile`);
        console.log("Profile Response Data:", profileResponse.data);

        setProfileData(profileResponse.data); // Assign fetched profile data to state

        // Once profile data is fetched, set Aadhaar number from profileData
        if (profileResponse.data && profileResponse.data.userId) {
          setAadhaar(profileResponse.data.userId); // Set Aadhaar from profileData
        }
      } catch (error) {
        console.error("Failed to fetch profile data:", error);
      } finally {
        setIsLoading(false);
      }
    };

    fetchProfileData();
  }, []); // Empty dependency array ensures this runs only once when the component mounts

  useEffect(() => {
    // If aadhaar is set, fetch user data by Aadhaar number
    if (aadhaar) {
      const fetchUserData = async () => {
        try {
          const API_URL = process.env.NEXT_PUBLIC_API_URL;
          setIsLoading(true);

          // First request: Fetch user data by Aadhaar number
          const userResponse = await axios.get(`${API_URL}/users/${aadhaar}`);
          console.log(userResponse.data)
          setUserData(userResponse.data); // Assign fetched user data to state

          // Fetch elections the user is eligible for
          if (userResponse.data.electionList && userResponse.data.electionList.length > 0) {
            // Assuming electionList contains election IDs as strings
            const electionIds = userResponse.data.electionList.join(',');
            const electionsResponse = await axios.get(`${API_URL}/election/getElectionsByIds`, {
              params: { ids: electionIds },
            });
            console.log(electionsResponse.data)
            setEligibleElections(electionsResponse.data); // Set eligible elections
          } else {
            setEligibleElections([]); // No eligible elections
          }

        } catch (error) {
          console.error("Failed to fetch user data:", error);
        } finally {
          setIsLoading(false);
        }
      };

      fetchUserData();
    }
  }, [aadhaar]); // Trigger user data fetch when aadhaar is updated

  // Show a skeleton loader or default content until data is loaded
  if (isLoading) {
    return (
      <div className="flex min-h-screen bg-background">
        <div className="flex-1 flex items-center justify-center">
          <div className="text-center">
            <div className="animate-pulse">
              <div className="h-4 bg-gray-300 rounded mb-4 w-1/2"></div>
              <div className="h-4 bg-gray-300 rounded w-3/4"></div>
            </div>
          </div>
        </div>
      </div>
    );
  }

  if (!userData) {
    return (
      <div className="flex min-h-screen bg-background">
        <div className="flex-1 flex items-center justify-center">
          <p className="text-red-500">User data could not be loaded. Please try again.</p>
        </div>
      </div>
    );
  }

  return (
    <div className="flex min-h-full w-full bg-background">
      <Sidebar
        user={userData}
        profileRole={profileData.role}
      />
      <ElectionTileView
        eligibleElections={eligibleElections}
        voterId={aadhaar}
      />
    </div>
  );
};

export default UserDetailPage;
