// PartyForm.js
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import StateDropdown from '@/components/shared/StateDropdown';

axios.defaults.withCredentials = true;

const PartyForm = ({ party, candidates, onClose, onSave }) => {

    console.log("party",party)
    console.log("candidates",candidates)

    const [name, setName] = useState(party ? party.name : '');
    const [agenda, setAgenda] = useState(party ? party.agenda : '');
    const [leaderId, setLeaderId] = useState(party && party.leader ? party.leader.aadhaarNumber : '');
    const [stateValue, setStateValue] = useState(party ? party.state : '');
    const [error, setError] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        // Validate inputs
        if (!name || !agenda || !stateValue) {
            setError('Name, Agenda, and State are required!');
            return;
        }

        const API_URL = process.env.NEXT_PUBLIC_API_URL;
        const partyData = {
            name,
            agenda,
            leader: leaderId ? { aadhaarNumber: leaderId } : null,
            state: stateValue,
        };

        try {
            let response;
            if (party) {
                // Edit existing party
                response = await axios.put(`${API_URL}/party/${party.name}`, partyData);
            } else {
                // Add new party
                response = await axios.post(`${API_URL}/party`, partyData);
            }
            onSave(response.data, !party);
            onClose();
        } catch (err) {
            console.error(err);
            setError('Error saving party data!');
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
                <div className="mb-4">
                    <label className="block mb-2">Party Leader</label>
                    {party && party.leader && (
                        <p className="mb-2">
                            Current Leader: <strong>{party.leader.aadhaarNumber}</strong>
                        </p>
                    )}
                    <select
                        className="w-full p-2 border border-gray-300 rounded"
                        value={leaderId}
                        onChange={(e) => setLeaderId(e.target.value)}
                    >
                        <option value="">Select Leader</option>
                        {candidates
                            .filter(candidate => !party || (candidate.party && candidate.party.name === party.name))
                            .map(candidate => (
                                <option key={candidate.aadhaarNumber} value={candidate.aadhaarNumber}>
                                    {candidate.aadhaarNumber} {candidate.name ? ` - ${candidate.name}` : ''}
                                </option>
                            ))}
                    </select>
                </div>
                <div className="mb-4">
                    <label className="block mb-2">State</label>
                    <StateDropdown value={stateValue} onChange={(e) => setStateValue(e.target.value)} />
                </div>
                <div className="flex justify-between">
                    <button type="button" onClick={onClose} className="bg-gray-500 text-white px-4 py-2 rounded">
                        Cancel
                    </button>
                    <button type="submit" className="bg-blue-500 text-white px-4 py-2 rounded">
                        {party ? 'Update Party' : 'Add Party'}
                    </button>
                </div>
            </form>
        </div>
    );
};

export default PartyForm;
