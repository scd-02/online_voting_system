"use client"

import React, { useState } from "react";
import { ChevronDown, ChevronUp, Vote } from "lucide-react";

export const ElectionTile = ({ election, onVote }) => {
    const [isExpanded, setIsExpanded] = useState(false);
    const [selectedParty, setSelectedParty] = useState("");

    const handleVote = () => {
        if (selectedParty) {
            onVote(election.id, selectedParty);
            setSelectedParty("");
        }
    };

    return (
        <div
            className={`bg-white rounded-lg shadow-md overflow-hidden transition-all duration-300 `}
            style={{
                width: "300px", // Fixed uniform width for all tiles
            }}
        >
            {/* Header Section */}
            <div
                onClick={() => setIsExpanded(!isExpanded)}
                className="cursor-pointer p-4 hover:bg-gray-50"
            >
                <div className="flex justify-between items-start">
                    {/* Title - Allow wrapping */}
                    <h3 className="text-lg font-semibold text-gray-800 leading-tight">
                        {election.name}
                    </h3>
                    {/* Chevron Icon */}
                    {isExpanded ? (
                        <ChevronUp className="w-5 h-5 text-gray-500" />
                    ) : (
                        <ChevronDown className="w-5 h-5 text-gray-500" />
                    )}
                </div>
                <div className="mt-2 flex justify-between items-center">
                    {/* Region */}
                    <span className="text-sm text-gray-600">{election.region}</span>
                    {/* Vote Now Button */}
                    {!isExpanded && (
                        <button
                            onClick={(e) => {
                                e.stopPropagation();
                                setIsExpanded(true);
                            }}
                            className="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600 flex items-center gap-2 text-sm"
                        >
                            <Vote className="w-4 h-4" />
                            Vote Now
                        </button>
                    )}
                </div>
            </div>

            {/* Expanded Content */}
            {isExpanded && (
                <div className="flex flex-col border-t border-gray-200 h-[200px]">
                    {/* Scrollable List */}
                    <div className="flex-1 overflow-y-auto p-4">
                        {election.parties.map((party) => (
                            <div
                                key={party.id}
                                className="flex justify-between items-center py-2"
                            >
                                <span className="text-gray-700">{party.name}</span>
                                <span className="text-gray-600 font-medium">
                                    {party.voteCount} votes
                                </span>
                            </div>
                        ))}
                    </div>
                    {/* Vote Section */}
                    <div className="p-4 bg-gray-50 border-t border-gray-200">
                        <div className="flex gap-2">
                            {/* Dropdown for Party Selection */}
                            <select
                                value={selectedParty}
                                onChange={(e) => setSelectedParty(e.target.value)}
                                className="flex-1 rounded-md border-gray-300 shadow-sm focus:border-blue-300 focus:ring focus:ring-blue-200 focus:ring-opacity-50"
                            >
                                <option value="">Select a party</option>
                                {election.parties.map((party) => (
                                    <option key={party.id} value={party.id}>
                                        {party.name}
                                    </option>
                                ))}
                            </select>
                            {/* Vote Button */}
                            <button
                                onClick={handleVote}
                                disabled={!selectedParty}
                                className="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600 disabled:bg-gray-400 disabled:cursor-not-allowed flex items-center gap-2"
                            >
                                <Vote className="w-4 h-4" />
                                Vote
                            </button>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
};
