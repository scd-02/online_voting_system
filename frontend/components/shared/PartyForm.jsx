// PartyForm.js
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import StateDropdown from '@/components/shared/StateDropdown';

axios.defaults.withCredentials = true;

const PartyForm = ({ party, candidates, onClose, onSave }) => {

    console.log("party", party)
    console.log("candidates", candidates)

    const [name, setName] = useState(party ? party.name : '');
    const [agenda, setAgenda] = useState(party ? party.agenda : '');
    const [leaderAadhaar, setLeaderAadhaar] = useState(party && party.leaderAadhaarNumber ? party.leaderAadhaarNumber : '');
    const [stateValue, setStateValue] = useState(party ? party.state : '');
    const [error, setError] = useState('');
    const [isSubmitting, setIsSubmitting] = useState(false); // New state to handle submission

    const handleSubmit = async (e) => {
        e.preventDefault();
        // Prevent multiple submissions
        if (isSubmitting) return;

        // Validate inputs
        if (!name || !agenda || !stateValue) {
            setError('Name, Agenda, and State are required!');
            return;
        }

        setIsSubmitting(true); // Set submitting state

        const API_URL = process.env.NEXT_PUBLIC_API_URL;
        const partyData = {
            name,
            agenda,
            state: stateValue,
            leaderAadhaarNumber: leaderAadhaar || null, // Send the leader's Aadhaar number if provided
        };

        try {
            let response;
            let newCandidate = null;

            if (party) {
                // Edit existing party
                response = await axios.put(`${API_URL}/party/${party.name}`, partyData);
                onSave(response.data, false);
            } else {
                // Add new party
                response = await axios.post(`${API_URL}/party`, partyData);
                const newParty = response.data;

                // Extract newCandidate from the response if leaderAadhaarNumber is provided
                if (newParty.leaderAadhaarNumber) {
                    newCandidate = {
                        aadhaarNumber: newParty.leaderAadhaarNumber,
                        partyName: newParty.name,
                        name: '', // Assuming name is optional or fetched elsewhere
                    };
                }

                onSave(newParty, true, newCandidate);
            }

            onClose();
        } catch (err) {
            console.error(err);
            if (err.response && err.response.data && err.response.data.message) {
                setError(err.response.data.message);
            } else {
                setError('Error saving party data!');
            }
        } finally {
            setIsSubmitting(false); // Reset submitting state
        }
    };

    return (
        <div className="p-6 bg-white rounded shadow-md">
            <h2 className="text-xl mb-4">{party ? 'Edit Party' : 'Add Party'}</h2>
            {error && <div className="text-red-500 mb-4">{error}</div>}
            <form onSubmit={handleSubmit}>
                <div className="mb-4">
                    <label className="block mb-2">Party Name</label>
                    <input
                        type="text"
                        className="w-full p-2 border border-gray-300 rounded"
                        placeholder="Party Name"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                        required
                        disabled={!!party}
                    />
                </div>
                <div className="mb-4">
                    <label className="block mb-2">Party Agenda</label>
                    <textarea
                        className="w-full p-2 border border-gray-300 rounded"
                        placeholder="Party Agenda"
                        value={agenda}
                        onChange={(e) => setAgenda(e.target.value)}
                        required
                    />
                </div>
                {!party && (
                    <div className="mb-4">
                        <label className="block mb-2">Leader Aadhaar Number</label>
                        <input
                            type="text"
                            className="w-full p-2 border border-gray-300 rounded"
                            placeholder="Leader Aadhaar Number"
                            value={leaderAadhaar}
                            onChange={(e) => setLeaderAadhaar(e.target.value)}
                        />
                    </div>
                )}
                {party && (
                    <div className="mb-4">
                        <label className="block mb-2">Leader</label>
                        <select
                            className="w-full p-2 border border-gray-300 rounded"
                            value={leaderAadhaar}
                            onChange={(e) => setLeaderAadhaar(e.target.value)}
                        >
                            <option value="">Select Leader</option>
                            {candidates
                                .filter(candidate => candidate.partyName && candidate.partyName === party.name)
                                .map(candidate => (
                                    <option key={candidate.aadhaarNumber} value={candidate.aadhaarNumber}>
                                        {candidate.aadhaarNumber}
                                    </option>
                                ))}
                        </select>
                    </div>
                )}
                {party && party.leaderAadhaarNumber && (
                    <div className="mb-4">
                        <p>
                            Current Leader: <strong>{party.leaderAadhaarNumber}</strong>
                        </p>
                    </div>
                )}
                <div className="mb-4">
                    <label className="block mb-2">State</label>
                    <StateDropdown value={stateValue} onChange={(e) => setStateValue(e.target.value)} />
                </div>
                <div className="flex justify-between">
                    <button
                        type="button"
                        onClick={onClose}
                        className="bg-gray-500 text-white px-4 py-2 rounded"
                        disabled={isSubmitting} // Disable while submitting
                    >
                        Cancel
                    </button>
                    <button
                        type="submit"
                        className="bg-blue-500 text-white px-4 py-2 rounded"
                        disabled={isSubmitting} // Disable while submitting
                    >
                        {isSubmitting ? (party ? 'Updating...' : 'Adding...') : (party ? 'Update Party' : 'Add Party')}
                    </button>
                </div>
            </form>
        </div>
    );

};

export default PartyForm;
