"use client";

import { useState, useEffect } from "react";
import { useParams, useRouter } from "next/navigation";
import {
  Card,
  CardHeader,
  CardContent,
  CardFooter,
} from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { ArrowLeft, ThumbsUp } from "lucide-react";

// Mock data - in a real app, this would come from an API or database
const mockContests = [
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
      { id: "p4", name: "David Brown", description: "Snow Scenes", votes: 89 },
      {
        id: "p5",
        name: "Eva Green",
        description: "Winter Wildlife",
        votes: 76,
      },
    ],
  },
];

export default function ContestPage() {
  const params = useParams();
  const router = useRouter();
  const [contest, setContest] = useState(null);
  const [votedFor, setVotedFor] = useState(null);

  useEffect(() => {
    const currentContest = mockContests.find((c) => c.id === params.id);
    setContest(currentContest || null);
  }, [params.id]);

  const handleVote = (participantId) => {
    if (contest?.status !== "active") return;

    setVotedFor(participantId);
    setContest((prev) => {
      if (!prev) return null;
      return {
        ...prev,
        participants: prev.participants.map((p) =>
          p.id === participantId ? { ...p, votes: p.votes + 1 } : p
        ),
      };
    });
  };

  if (!contest) return null;

  return (
    <div className="min-h-screen bg-background p-6">
      <div className="max-w-4xl mx-auto space-y-6">
        <div className="flex items-center space-x-4">
          <Button variant="ghost" onClick={() => router.back()}>
            <ArrowLeft className="h-4 w-4 mr-2" />
            Back
          </Button>
          <div>
            <h1 className="text-2xl font-bold">{contest.title}</h1>
            <p className="text-sm text-muted-foreground">
              {new Date(contest.startDate).toLocaleDateString()} -{" "}
              {new Date(contest.endDate).toLocaleDateString()}
            </p>
          </div>
        </div>

        <div className="grid gap-6">
          {contest.participants.map((participant) => (
            <Card key={participant.id}>
              <CardHeader>
                <div className="flex justify-between items-start">
                  <div>
                    <h3 className="text-xl font-semibold">
                      {participant.name}
                    </h3>
                    <p className="text-sm text-muted-foreground">
                      {participant.description}
                    </p>
                  </div>
                  <Badge variant="secondary">{participant.votes} votes</Badge>
                </div>
              </CardHeader>
              <CardFooter>
                <Button
                  className="w-full"
                  onClick={() => handleVote(participant.id)}
                  disabled={contest.status !== "active" || votedFor !== null}
                  variant={
                    votedFor === participant.id ? "secondary" : "default"
                  }
                >
                  <ThumbsUp className="h-4 w-4 mr-2" />
                  {votedFor === participant.id ? "Voted!" : "Vote"}
                </Button>
              </CardFooter>
            </Card>
          ))}
        </div>
      </div>
    </div>
  );
}
