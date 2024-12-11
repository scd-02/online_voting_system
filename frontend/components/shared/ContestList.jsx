"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";
import {
  Card,
  CardHeader,
  CardContent,
  CardFooter,
} from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Calendar } from "lucide-react";

export function ContestList() {
  const router = useRouter();
  // Sample contests - in a real app, these would come from an API
  const [contests] = useState([
    {
      id: "1",
      title: "Spring Photography Contest 2024",
      startDate: "2024-04-01",
      endDate: "2024-04-30",
      status: "active",
      participants: [
        {
          id: "p1",
          name: "Alice Smith",
          description: "Nature's Beauty",
          votes: 45,
        },
        { id: "p2", name: "Bob Johnson", description: "Urban Life", votes: 38 },
        { id: "p3", name: "Carol White", description: "Wildlife", votes: 52 },
      ],
    },
    {
      id: "2",
      title: "Summer Art Exhibition",
      startDate: "2024-06-01",
      endDate: "2024-06-30",
      status: "upcoming",
      participants: [],
    },
    {
      id: "3",
      title: "Winter Photography Contest 2023",
      startDate: "2023-12-01",
      endDate: "2023-12-31",
      status: "ended",
      participants: [
        {
          id: "p4",
          name: "David Brown",
          description: "Snow Scenes",
          votes: 89,
        },
        {
          id: "p5",
          name: "Eva Green",
          description: "Winter Wildlife",
          votes: 76,
        },
      ],
    },
  ]);

  const getStatusColor = (status) => {
    switch (status) {
      case "upcoming":
        return "text-blue-500";
      case "active":
        return "text-green-500";
      case "ended":
        return "text-gray-500";
      default:
        return "";
    }
  };

  const formatDate = (dateString) => {
    return new Date(dateString).toLocaleDateString("en-US", {
      year: "numeric",
      month: "long",
      day: "numeric",
    });
  };

  return (
    <div className="p-6 space-y-6">
      <div className="flex justify-between items-center">
        <h2 className="text-2xl font-semibold">Elections</h2>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {contests.map((contest) => (
          <Card key={contest.id} className="hover:shadow-lg transition-shadow">
            <CardHeader>
              <h3 className="text-xl font-semibold">{contest.title}</h3>
              <span
                className={`text-sm font-medium ${getStatusColor(
                  contest.status
                )} capitalize`}
              >
                {contest.status}
              </span>
            </CardHeader>
            <CardContent>
              <div className="space-y-2">
                <div className="flex items-center text-sm text-muted-foreground">
                  <Calendar className="h-4 w-4 mr-2" />
                  <span>
                    {formatDate(contest.startDate)} -{" "}
                    {formatDate(contest.endDate)}
                  </span>
                </div>
                <p className="text-sm text-muted-foreground">
                  {contest.participants.length} participants
                </p>
              </div>
            </CardContent>
            <CardFooter>
              <Button
                className="w-full"
                onClick={() => router.push(`/election/${contest.id}`)}
                variant={contest.status === "active" ? "default" : "secondary"}
                disabled={contest.status === "upcoming"}
              >
                {contest.status === "active" ? "Vote Now" : "View Details"}
              </Button>
            </CardFooter>
          </Card>
        ))}
      </div>
    </div>
  );
}
