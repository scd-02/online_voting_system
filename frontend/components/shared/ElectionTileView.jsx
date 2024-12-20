"use client";

import React, { useState, useEffect } from "react";
import { ElectionTile } from "./ElectionTile";
import axios from "axios";

// Set axios to send cookies with requests
axios.defaults.withCredentials = true;

export default function ElectionTileView({ eligibleElections, voterId }) {
    const [elections, setElections] = useState(eligibleElections);
    const [votes, setVotes] = useState([]);

    // Fetch the list of votes for the voter when the component mounts
    useEffect(() => {
        const fetchVotes = async () => {
            const API_URL = process.env.NEXT_PUBLIC_API_URL;
            try {
                const response = await axios.get(`${API_URL}/vote/voter/${voterId}`);
                setVotes(response.data);
            } catch (error) {
                console.error("Error fetching votes:", error);
            }
        };

        fetchVotes();
    }, [voterId]);

    const handleVote = (electionId, party) => {
        setElections((prevElections) =>
            prevElections.map((election) => {
                if (election.id === electionId) {
                    return {
                        ...election,
                        partyVotes: {
                            ...election.partyVotes,
                            [party]: election.partyVotes[party] + 1,
                        },
                    };
                }
                return election;
            })
        );
    };

    return (
        <div className="min-h-screen">
            <div className="mx-auto h-full space-y-4">
                <h1 className="text-2xl font-bold text-gray-800 mb-6">
                    {elections && elections.length === 0 ? "No " : ""}Active Elections
                </h1>
                {elections.map((election) => (
                    <ElectionTile
                        key={election.id}
                        election={election}
                        voterId={voterId}
                        onVote={handleVote}
                        votes={votes} // Pass the fetched votes to the ElectionTile
                    />
                ))}
            </div>
        </div>
    );
}
