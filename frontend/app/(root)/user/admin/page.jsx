"use client";

import React, { useEffect, useState } from "react";
import axios from "axios";
import PartyForm from "@/components/shared/PartyForm"; // Assuming PartyForm is a separate component
import CandidateForm from "@/components/shared/CandidateForm"; // Assuming CandidateForm is a separate component
import ElectionForm from "@/components/shared/ElectionForm"; // Assuming ElectionForm is a separate component

const AdminPage = () => {
    const [candidates, setCandidates] = useState([]);
    const [parties, setParties] = useState([]);
    const [elections, setElections] = useState([]);

    const [candidateFilter, setCandidateFilter] = useState("");
    const [partyFilter, setPartyFilter] = useState("");
    const [electionFilter, setElectionFilter] = useState("");

    const [showForm, setShowForm] = useState(false);
    const [formType, setFormType] = useState(""); // 'party', 'candidate', or 'election'
    const [selectedItem, setSelectedItem] = useState(null); // Store selected item for editing

    const fetchData = async () => {
        try {
            const API_URL = process.env.NEXT_PUBLIC_API_URL;
            axios.defaults.withCredentials = true;

            // Fetch data for all columns
            const [candidateRes, partyRes, electionRes] = await Promise.all([
                axios.get(`${API_URL}/candidates/getAllCandidates`),
                axios.get(`${API_URL}/parties/getAllParties`),
                axios.get(`${API_URL}/elections/getAllElections`),
            ]);

            // Log received data
            console.log("Candidates:", candidateRes.data);
            console.log("Parties:", partyRes.data);
            console.log("Elections:", electionRes.data);

            // Set data to state
            setCandidates(candidateRes.data);
            setParties(partyRes.data);
            setElections(electionRes.data);
        } catch (error) {
            console.error("Error fetching data:", error);
        }
    };

    useEffect(() => {
        fetchData();
    }, []);

    const handleDelete = async (id, type) => {
        try {
            const API_URL = process.env.NEXT_PUBLIC_API_URL;
            await axios.delete(`${API_URL}/${type}/${id}`);
            fetchData(); // Refresh data after deletion
        } catch (error) {
            console.error(`Error deleting ${type}:`, error);
        }
    };

    const handleEdit = (item, type) => {
        setSelectedItem(item);
        setFormType(type);
        setShowForm(true);
    };

    const handleAdd = (type) => {
        setSelectedItem(null);
        setFormType(type);
        setShowForm(true);
    };

    const renderColumn = (data, filter, setFilter, type) => {
        if (!data || data.length === 0) {
            return <div>No {type} available</div>;
        }

        return (
            <div className="flex-1 border border-gray-300 rounded p-4 bg-white">
                <input
                    type="text"
                    className="w-full p-2 mb-4 border border-gray-300 rounded"
                    placeholder={`Search ${type}...`}
                    value={filter}
                    onChange={(e) => setFilter(e.target.value)}
                />
                <div className="h-96 overflow-y-auto">
                    {data
                        .filter((item) => {
                            // Filter logic for candidates (based on aadhaarNumber)
                            if (type === "candidates") {
                                return item.aadhaarNumber && item.aadhaarNumber.toLowerCase().includes(filter.toLowerCase());
                            }
                            // Filter logic for parties (based on name)
                            if (type === "parties") {
                                return item.name && item.name.toLowerCase().includes(filter.toLowerCase());
                            }
                            // Filter logic for elections (based on name)
                            if (type === "elections") {
                                return item.name && item.name.toLowerCase().includes(filter.toLowerCase());
                            }
                            return true;
                        })
                        .map((item) => (
                            <div
                                key={item.id}
                                className="p-2 mb-2 border rounded flex justify-between items-center"
                            >
                                <div>{type === "candidates" ? item.aadhaarNumber : item.name}</div>
                                <div className="flex space-x-2">
                                    <button
                                        className="px-2 py-1 bg-blue-500 text-white rounded"
                                        onClick={() => handleEdit(item, type)}
                                    >
                                        Edit
                                    </button>
                                    <button
                                        className="px-2 py-1 bg-red-500 text-white rounded"
                                        onClick={() => handleDelete(item.id, type)}
                                    >
                                        Delete
                                    </button>
                                </div>
                            </div>
                        ))}
                </div>
                <button
                    className="w-full mt-4 p-2 bg-green-500 text-white rounded"
                    onClick={() => handleAdd(type)}
                >
                    Add {type}
                </button>
            </div>
        );
    };

    return (
        <div className="flex min-h-screen w-full bg-gray-100 p-8 space-x-8">
            {renderColumn(candidates, candidateFilter, setCandidateFilter, "candidates")}
            {renderColumn(parties, partyFilter, setPartyFilter, "parties")}
            {renderColumn(elections, electionFilter, setElectionFilter, "elections")}

            {showForm && (
                <div className="fixed inset-0 bg-gray-500 bg-opacity-50 flex justify-center items-center">
                    <div className="bg-white p-6 rounded shadow-lg w-1/3">
                        <h2 className="text-2xl font-bold mb-4">
                            {formType === "party" && "Party Form"}
                            {formType === "candidate" && "Candidate Form"}
                            {formType === "election" && "Election Form"}
                        </h2>

                        {formType === "party" && (
                            <PartyForm
                                party={selectedItem}
                                onClose={() => setShowForm(false)}
                                onSave={() => { fetchData(); setShowForm(false); }}
                            />
                        )}
                        {formType === "candidate" && (
                            <CandidateForm
                                candidate={selectedItem}
                                onClose={() => setShowForm(false)}
                                onSave={() => { fetchData(); setShowForm(false); }}
                            />
                        )}
                        {formType === "election" && (
                            <ElectionForm
                                election={selectedItem}
                                onClose={() => setShowForm(false)}
                                onSave={() => { fetchData(); setShowForm(false); }}
                            />
                        )}
                    </div>
                </div>
            )}
        </div>
    );
};

export default AdminPage;
