import React, { useState } from 'react';
import axios from 'axios';
import StateDropdown from './StateDropdown';

axios.defaults.withCredentials = true;

const ElectionForm = ({ election, parties, onClose, onSave }) => {
    const [name, setName] = useState('');
    const [state, setState] = useState('');
    const [eligiblePartyIds, setEligiblePartyIds] = useState(new Set());
    const [filter, setFilter] = useState('');
    const [error, setError] = useState('');
    const [dropdownVisible, setDropdownVisible] = useState(false); // Toggle state for dropdown visibility

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!name || !state || eligiblePartyIds.size === 0) {
            setError('All fields are required!');
            return;
        }

        const API_URL = process.env.NEXT_PUBLIC_API_URL;
        const electionData = {
            name,
            state,
            eligiblePartys: Array.from(eligiblePartyIds),
            active: true,
        };

        try {
            const response = await axios.post(`${API_URL}/election/create`, electionData);
            onSave(response.data, true);
            onClose();
        } catch (err) {
            console.error(err);
            setError('Error saving election data!');
        }
    };

    // Add a party to the selected list
    const handleAddParty = (partyId) => {
        setEligiblePartyIds((prev) => new Set(prev).add(partyId));
    };

    // Remove a party from the selected list
    const handleRemoveParty = (partyId) => {
        setEligiblePartyIds((prev) => {
            const updated = new Set(prev);
            updated.delete(partyId);
            return updated;
        });
    };

    // Filter parties based on search input
    const filteredParties = parties.filter((party) =>
        party.name.toLowerCase().includes(filter.toLowerCase())
    );

    return (
        <div className="p-6 bg-white rounded shadow-md">
            <h2 className="text-xl mb-4">Add Election</h2>
            {error && <div className="text-red-500 mb-4">{error}</div>}
            <form onSubmit={handleSubmit}>
                <div className="mb-4">
                    <input
                        type="text"
                        className="w-full p-2 border border-gray-300 rounded"
                        placeholder="Election Name"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                    />
                </div>
                <div className="mb-4">
                    <StateDropdown
                        value={state}
                        onChange={(e) => setState(e.target.value)}
                        form="election"
                    />
                </div>
                <div className="mb-4">
                    <label className="block mb-2">Eligible Parties:</label>
                    <div className="mb-2">
                        {Array.from(eligiblePartyIds).map((partyId) => {
                            const party = parties.find((p) => p.name === partyId);
                            return (
                                <span
                                    key={partyId}
                                    className="inline-flex items-center bg-blue-100 text-blue-700 px-2 py-1 rounded-full mr-2 mb-2"
                                >
                                    {party?.name || partyId}
                                    <button
                                        type="button"
                                        className="ml-2 text-red-500"
                                        onClick={() => handleRemoveParty(partyId)}
                                    >
                                        ✕
                                    </button>
                                </span>
                            );
                        })}
                    </div>
                    <div>
                        {/* Toggle Dropdown */}
                        <button
                            type="button"
                            onClick={() => setDropdownVisible((prev) => !prev)}
                            className="bg-gray-200 px-4 py-2 rounded text-sm"
                        >
                            {dropdownVisible ? 'Hide Parties' : 'Add Parties'}
                        </button>
                        {dropdownVisible && (
                            <div className="relative mt-2">
                                <input
                                    type="text"
                                    className="w-full p-2 border border-gray-300 rounded mb-2"
                                    placeholder="Search Parties"
                                    value={filter}
                                    onChange={(e) => setFilter(e.target.value)}
                                />
                                <div
                                    className="absolute bg-white border border-gray-300 rounded mt-1 w-full max-h-60 overflow-y-auto"
                                    style={{ zIndex: 1000 }}
                                >
                                    {filteredParties
                                        .filter((party) => !eligiblePartyIds.has(party.name))
                                        .map((party) => (
                                            <div
                                                key={party.name}
                                                className="p-2 hover:bg-gray-100 cursor-pointer"
                                                onClick={() => handleAddParty(party.name)}
                                            >
                                                {party.name}
                                            </div>
                                        ))}
                                </div>
                            </div>
                        )}
                    </div>
                </div>
                <div className="flex justify-between">
                    <button type="button" onClick={onClose} className="bg-gray-500 text-white px-4 py-2 rounded">
                        Cancel
                    </button>
                    <button type="submit" className="bg-blue-500 text-white px-4 py-2 rounded">
                        Add Election
                    </button>
                </div>
            </form>
        </div>
    );
};

export default ElectionForm;
