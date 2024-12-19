"use client";

import React, { useEffect, useState } from "react";
import axios from "axios";
import Modal from "@/components/shared/Modal";
import CandidateForm from "@/components/shared/CandidateForm";
import PartyForm from "@/components/shared/PartyForm";
import ElectionForm from "@/components/shared/ElectionForm";
import AdminForm from "@/components/shared/AdminForm";

const AdminPage = () => {
    const [candidates, setCandidates] = useState([]);
    const [parties, setParties] = useState([]);
    const [elections, setElections] = useState([]);

    const [candidateFilter, setCandidateFilter] = useState("");
    const [partyFilter, setPartyFilter] = useState("");
    const [electionFilter, setElectionFilter] = useState("");

    const [isModalOpen, setIsModalOpen] = useState(false);
    const [currentItem, setCurrentItem] = useState(null);
    const [modalType, setModalType] = useState('');
    const [isNew, setIsNew] = useState(false);

    const fetchData = async () => {
        try {
            const API_URL = process.env.NEXT_PUBLIC_API_URL;
            axios.defaults.withCredentials = true;

            // Fetch data for all columns
            const [candidateRes, partyRes, electionRes] = await Promise.all([
                axios.get(`${API_URL}/candidate/getAllCandidates`),
                axios.get(`${API_URL}/party/getAllParties`),
                axios.get(`${API_URL}/election/getAllElections`),
            ]);

            console.log("Candidates:", candidateRes.data);
            console.log("Parties:", partyRes.data);
            console.log("Elections:", electionRes.data);

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

    // Handlers for saving data without re-fetching
    const handleCandidateSave = (updatedCandidate, isNew) => {
        setCandidates(prevCandidates => {
            if (isNew) {
                return [...prevCandidates, updatedCandidate];
            } else {
                return prevCandidates.map(candidate =>
                    candidate.aadhaarNumber === updatedCandidate.aadhaarNumber ? updatedCandidate : candidate
                );
            }
        });
    };

    const handlePartySave = (updatedParty, isNew, newCandidate) => {
        setParties(prevParties => {
            if (isNew) {
                return [...prevParties, updatedParty];
            } else {
                return prevParties.map(party =>
                    party.name === updatedParty.name ? updatedParty : party
                );
            }
        });

        // If a new candidate is created, update the candidates state
        if (isNew && newCandidate) {
            setCandidates(prevCandidates => [...prevCandidates, newCandidate]);
        }
    };

    const handleElectionSave = (updatedElection, isNew) => {
        setElections(prevElections => {
            if (isNew) {
                return [...prevElections, updatedElection];
            } else {
                return prevElections.map(election =>
                    election.name === updatedElection.name ? updatedElection : election
                );
            }
        });
    };

    const handleAdminSave = (updatedAdmin, isNew) => {
        // Implement the logic to handle saving admin data
        // This could involve updating the state or making an API call
        console.log("Admin saved:", updatedAdmin, isNew);
    };

    const handleDelete = async (id, type) => {
        switch (type) {
            case "candidates": type = "candidate"; break;
            case "parties": type = "party"; break;
            case "elections": type = "election"; break;
        }

        const confirmDelete = window.confirm(`Are you sure you want to delete this ${type}?`);
        if (!confirmDelete) return;

        try {
            const API_URL = process.env.NEXT_PUBLIC_API_URL;

            await axios.delete(`${API_URL}/${type}/${id}`);

            // Update the state without re-fetching
            if (type === 'candidate') {
                setCandidates((prevCandidates) =>
                    prevCandidates.filter((candidate) => candidate.aadhaarNumber !== id)
                );
            } else if (type === 'party') {
                // Find the deleted party to identify its leader
                const deletedParty = parties.find((party) => party.name === id);
                setParties((prevParties) =>
                    prevParties.filter((party) => party.name !== id)
                );

                if (deletedParty && deletedParty.leaderAadhaarNumber) {
                    setCandidates((prevCandidates) =>
                        prevCandidates.filter(
                            (candidate) => candidate.aadhaarNumber !== deletedParty.leaderAadhaarNumber
                        )
                    );
                }
            } else if (type === 'election') {
                setElections((prevElections) =>
                    prevElections.filter((election) => election.name !== id)
                );
            }
        } catch (error) {
            console.error(`Error deleting ${type}:`, error);
        }
    };

    const handleEdit = (item, type) => {
        setCurrentItem(item);
        setModalType(type);
        setIsModalOpen(true);
        setIsNew(false);
    };

    const handleAdd = (type) => {
        setCurrentItem(null);
        setModalType(type);
        setIsModalOpen(true);
        setIsNew(true);
    };

    const handleEditAdmin = () => {
        setModalType("admins");
        setIsModalOpen(true);
        setIsNew(false);
    };

    const renderColumn = (items, filter, setFilter, type) => {
        const filteredItems = items.filter(item => {
            if (!filter) return true;
            // Implement filtering logic based on type
            if (type === "candidates") {
                return item.aadhaarNumber && item.aadhaarNumber.toLowerCase().includes(filter.toLowerCase());
            } else if (type === "parties") {
                return item.name && item.name.toLowerCase().includes(filter.toLowerCase());
            } else if (type === "elections") {
                return item.name && item.name.toLowerCase().includes(filter.toLowerCase());
            } else {
                return true;
            }
        });

        // Filter out non-object items
        const validItems = filteredItems.filter(item => typeof item === 'object' && item !== null);

        return (
            <div className="h-screen w-1/3 flex flex-col">
                <h2 className="text-xl font-bold mb-4 capitalize">{type}</h2>
                <input
                    type="text"
                    placeholder={`Search ${type}`}
                    value={filter}
                    onChange={e => setFilter(e.target.value)}
                    className="mb-4 px-2 py-1 border rounded w-full"
                />
                <div className="flex-grow w-full bg-white p-4 rounded-lg shadow overflow-y-auto">
                    <ul>
                        {validItems.map(item => (
                            <li key={item.aadhaarNumber || item.id || item.name} className="mb-2 border-b pb-2">
                                {type === "candidates" && (
                                    <div>
                                        <p><strong>Aadhaar Number:</strong> {item.aadhaarNumber}</p>
                                        <p className="mb-2"><strong>Party:</strong> {item.partyName}</p>
                                        <button onClick={() => handleEdit(item, type)} className="rounded px-4 py-1 mr-4 bg-blue-500 text-white">Edit</button>
                                        <button onClick={() => handleDelete(item.aadhaarNumber, type)} className="text-red-500 ml-2">Delete</button>
                                    </div>
                                )}
                                {type === "parties" && (
                                    <div>
                                        <p><strong>Name:</strong> {item.name}</p>
                                        <p><strong>Leader:</strong> {item.leaderAadhaarNumber}</p>
                                        <p><strong>Agenda:</strong> {item.agenda}</p>
                                        <p className="mb-2"><strong>State:</strong> {item.state}</p>
                                        <button onClick={() => handleEdit(item, type)} className="rounded px-4 py-1 mr-4 bg-blue-500 text-white">Edit</button>
                                        <button onClick={() => handleDelete(item.name, type)} className="text-red-500 ml-2">Delete</button>
                                    </div>

                                )}

                                {/* Render Elections */}
                                {type === "elections" && (
                                    <div>
                                        <strong>{item.name}</strong>
                                        <p>State: {item.state}</p>
                                        <div className="flex space-x-3 mb-3">
                                            <p>Eligible Parties:</p>
                                            {item.eligiblePartys && item.eligiblePartys.length > 0 ? (
                                                <ul>
                                                    {item.eligiblePartys.map(partyName => {
                                                        const party = parties.find(
                                                            p => p.name === partyName
                                                        );
                                                        return (
                                                            <li key={partyName}>
                                                                {party ? party.name : `Party Name ${partyName}`}
                                                            </li>
                                                        );
                                                    })}
                                                </ul>
                                            ) : (
                                                <p>No eligible parties.</p>
                                            )}
                                        </div>
                                        <button onClick={() => handleEdit(item, type)} className="rounded px-4 py-1 mr-4 bg-blue-500 text-white">Edit</button>
                                        <button onClick={() => handleDelete(item.name, type)} className="text-red-500 ml-2">Delete</button>
                                    </div>
                                )}
                            </li>
                        ))}
                        {/* Render warnings for incomplete data */}
                        {filteredItems.length > validItems.length && (
                            <li className="mb-2 border-b pb-2 text-red-500">
                                Incomplete {type.slice(0, -1)} Data Found
                            </li>
                        )}
                    </ul>
                </div>
                <button onClick={() => handleAdd(type)} className="mt-4 px-4 py-2 bg-blue-500 text-white rounded self-end">
                    Add {type}
                </button>
            </div>
        );
    };

    const renderForm = () => {
        if (modalType === "candidates") {
            return (
                <CandidateForm
                    candidate={currentItem}
                    parties={parties}
                    onClose={() => setIsModalOpen(false)}
                    onSave={handleCandidateSave}
                />
            );
        }
        if (modalType === "parties") {
            return (
                <PartyForm
                    party={currentItem}
                    candidates={candidates}
                    onClose={() => setIsModalOpen(false)}
                    onSave={handlePartySave}
                />
            );
        }
        if (modalType === "elections") {
            return (
                <ElectionForm
                    election={currentItem}
                    parties={parties}
                    onClose={() => setIsModalOpen(false)}
                    onSave={handleElectionSave}
                />
            );
        }
        if (modalType === "admins") {
            return (
                <AdminForm
                    onClose={() => setIsModalOpen(false)}
                    onSave={handleAdminSave}
                />
            );
        }
        return null;
    };

    return (
        <div className="flex flex-col min-h-screen w-full bg-gray-100 p-8">
            <div className="py-3 flex justify-between items-center">
                <h1 className="text-2xl font-bold">ADMIN</h1>
                <button
                    onClick={() => handleEditAdmin()}
                    className="px-4 py-2 bg-blue-500 text-white rounded"
                >
                    Edit Admin
                </button>
            </div>
            <div className="flex flex-grow space-x-8 ">
                {renderColumn(candidates, candidateFilter, setCandidateFilter, "candidates")}
                {renderColumn(parties, partyFilter, setPartyFilter, "parties")}
                {renderColumn(elections, electionFilter, setElectionFilter, "elections")}
                <Modal isOpen={isModalOpen} onClose={() => setIsModalOpen(false)}>
                    {renderForm()}
                </Modal>
            </div>
        </div>
    );
};

export default AdminPage;
