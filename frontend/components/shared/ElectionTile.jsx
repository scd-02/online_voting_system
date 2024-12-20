"use client";

import React, { useState, useEffect } from "react";
import { ChevronDown, ChevronUp, Vote } from "lucide-react";
import axios from "axios";

export const ElectionTile = ({ election, voterId, onVote, votes }) => {
    const [isExpanded, setIsExpanded] = useState(false);
    const [selectedParty, setSelectedParty] = useState("");
    const [hasVoted, setHasVoted] = useState(false); // Track if the user has voted
    const [votedParty, setVotedParty] = useState(""); // Track the voted party

    useEffect(() => {
        // Check if the user has already voted in this election
        const userVote = votes.find((vote) => vote.electionId === election.id && vote.voterId === voterId);
        if (userVote) {
            setHasVoted(true); // User has voted in this election
            setVotedParty(userVote.partyId); // Set the voted party
        } else {
            setHasVoted(false); // User has not voted yet
            setVotedParty(""); // Reset voted party
        }
    }, [votes, election.id, voterId]);

    const createVote = async (voterId, partyId, electionId) => {
        const API_URL = process.env.NEXT_PUBLIC_API_URL;
        const payload = {
            voterId: voterId,
            partyId: partyId,
            electionId: electionId,
        };

        try {
            const response = await axios.post(`${API_URL}/vote`, payload);
            console.log(response.data); // Handle success message
        } catch (error) {
            if (error.response && error.response.status === 400) {
                console.error("Error creating vote:", error.response.data); // "Vote already exists for this user in this election."
            } else {
                console.error("Error creating vote:", error);
            }
        }
    };

    const handleVote = async () => {
        if (selectedParty) {
            await createVote(voterId, selectedParty, election.id); // Send vote to API
            onVote(election.id, selectedParty); // Update local state
            setHasVoted(true); // Set the user as having voted
            setVotedParty(selectedParty); // Set the voted party
        }
    };

    return (
        <div
            className="bg-white rounded-lg shadow-md overflow-hidden transition-all duration-300"
            style={{
                width: "300px",
            }}
        >
            {/* Header Section */}
            <div
                onClick={() => setIsExpanded(!isExpanded)}
                className="cursor-pointer p-4 hover:bg-gray-50"
            >
                <div className="flex justify-between items-start">
                    <h3 className="text-lg font-semibold text-gray-800 leading-tight">
                        {election.name}
                    </h3>
                    {isExpanded ? (
                        <ChevronUp className="w-5 h-5 text-gray-500" />
                    ) : (
                        <ChevronDown className="w-5 h-5 text-gray-500" />
                    )}
                </div>
                <div className="mt-2 flex justify-between items-center">
                    <span className="text-sm text-gray-600">{election.state}</span>
                    <span className="text-xs text-gray-400">
                        {new Date(election.creationDate).toLocaleString()}
                    </span>
                </div>
            </div>

            {/* Expanded Content */}
            {isExpanded && (
                <div className="flex flex-col border-t border-gray-200 h-[200px]">
                    <div className="flex-1 overflow-y-auto p-4">
                        {Object.entries(election.partyVotes).map(([party, votes]) => (
                            <div key={party} className="flex justify-between items-center py-2">
                                <span className="text-gray-700">{party}</span>
                                <span className="text-gray-600 font-medium">{votes} votes</span>
                            </div>
                        ))}
                    </div>
                    <div className="p-4 bg-gray-50 border-t border-gray-200">
                        {hasVoted ? (
                            <p className="text-sm text-green-600 font-semibold">
                                Voted: {votedParty}
                            </p>
                        ) : (
                            <div className="flex gap-2">
                                <select
                                    value={selectedParty}
                                    onChange={(e) => setSelectedParty(e.target.value)}
                                    className="flex-1 rounded-md border-gray-300 shadow-sm focus:border-blue-300 focus:ring focus:ring-blue-200 focus:ring-opacity-50"
                                >
                                    <option value="">Select a party</option>
                                    {election.eligiblePartys.map((party) => (
                                        <option key={party} value={party}>
                                            {party}
                                        </option>
                                    ))}
                                </select>
                                <button
                                    onClick={handleVote}
                                    disabled={!selectedParty || hasVoted}
                                    className="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600 disabled:bg-gray-400 disabled:cursor-not-allowed flex items-center gap-2"
                                >
                                    <Vote className="w-4 h-4" />
                                    Vote
                                </button>
                            </div>
                        )}
                    </div>
                </div>
            )}
        </div>
    );
};
