// ElectionForm.js
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import StateDropdown from './StateDropdown';

axios.defaults.withCredentials = true;

const ElectionForm = ({ election, parties, onClose, onSave }) => {
    const [name, setName] = useState(election ? election.name : '');
    const [state, setState] = useState(election ? election.state : '');
    const [eligiblePartyIds, setEligiblePartyIds] = useState(
        election && election.eligiblePartys ? election.eligiblePartys.map(party => party.id.toString()) : []
    );
    const [error, setError] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!name || !state || eligiblePartyIds.length === 0) {
            setError('All fields are required!');
            return;
        }

        const API_URL = process.env.NEXT_PUBLIC_API_URL;
        const electionData = {
            name,
            state,
            eligiblePartys: eligiblePartyIds.map(id => ({ id: parseInt(id) })),
        };

        try {
            axios.defaults.withCredentials = true;
            let response;
            if (election) {
                // Edit existing election
                response = await axios.put(`${API_URL}/elections/${election.id}`, electionData);
            } else {
                // Add new election
                response = await axios.post(`${API_URL}/elections`, electionData);
            }
            onSave(response.data, !election);
            onClose();
        } catch (err) {
            console.error(err);
            setError('Error saving election data!');
        }
    };

    return (
        <div className="p-6 bg-white rounded shadow-md">
            <h2 className="text-xl mb-4">{election ? 'Edit Election' : 'Add Election'}</h2>
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
                    <StateDropdown value={state} onChange={(e) => setState(e.target.value)} />
                </div>
                <div className="mb-4">
                    {election && election.eligiblePartys && election.eligiblePartys.length > 0 && (
                        <p className="mb-2">
                            Current Eligible Parties:{" "}
                            <strong>{election.eligiblePartys.map((p) => p.name).join(", ")}</strong>
                        </p>
                    )}
                    <label className="block mb-2">Eligible Parties:</label>
                    <select
                        multiple
                        className="w-full p-2 border border-gray-300 rounded"
                        value={eligiblePartyIds}
                        onChange={(e) =>
                            setEligiblePartyIds(Array.from(e.target.selectedOptions, option => option.value))
                        }
                    >
                        {parties.map((party) => (
                            <option key={party.id} value={party.id.toString()}>
                                {party.name}
                            </option>
                        ))}
                    </select>
                </div>
                <div className="flex justify-between">
                    <button type="button" onClick={onClose} className="bg-gray-500 text-white px-4 py-2 rounded">
                        Cancel
                    </button>
                    <button type="submit" className="bg-blue-500 text-white px-4 py-2 rounded">
                        {election ? 'Update Election' : 'Add Election'}
                    </button>
                </div>
            </form>
        </div>
    );
};

export default ElectionForm;
