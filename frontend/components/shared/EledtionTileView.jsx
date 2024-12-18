"use client";

import React, { useState } from "react";
import { ElectionTile } from "./ElectionTile";

const mockElections = [
    {
        id: "1",
        name: "Presidential Election 2024",
        region: "National",
        parties: [
            { id: "1", name: "BJP", voteCount: 200 },
            { id: "2", name: "Democratic Party", voteCount: 154 },
            { id: "3", name: "Republican Party", voteCount: 142 },
            { id: "4", name: "Green Party", voteCount: 45 },
            { id: "5", name: "Libertarian Party", voteCount: 38 },
        ],
    },
    {
        id: "2",
        name: "State Governor Election",
        region: "California",
        parties: [
            { id: "1", name: "Progressive Alliance", voteCount: 89 },
            { id: "2", name: "Conservative Union", voteCount: 76 },
            { id: "3", name: "Reform Party", voteCount: 34 },
        ],
    },
];

export default function ElectionTileView() {
    const [elections, setElections] = useState(mockElections);

    const handleVote = (electionId, partyId) => {
        setElections((prevElections) =>
            prevElections.map((election) => {
                if (election.id === electionId) {
                    return {
                        ...election,
                        parties: election.parties.map((party) => {
                            if (party.id === partyId) {
                                return { ...party, voteCount: party.voteCount + 1 };
                            }
                            return party;
                        }),
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
                    Active Elections
                </h1>
                {elections.map((election) => (
                    <ElectionTile
                        key={election.id}
                        election={election}
                        onVote={handleVote}
                    />
                ))}
            </div>
        </div>
    );
}