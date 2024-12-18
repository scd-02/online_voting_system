// PartyForm.js
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import StateDropdown from './StateDropdown';

const PartyForm = ({ party, onClose, onSave }) => {
    const [name, setName] = useState(party ? party.name : '');
    const [symbol, setSymbol] = useState(party ? party.symbol : '');
    const [agenda, setAgenda] = useState(party ? party.agenda : '');
    const [leader, setLeader] = useState(party ? party.leader.aadhaarNumber : '');
    const [state, setState] = useState(party ? party.state : '');
    const [error, setError] = useState('');

    const [leaders, setLeaders] = useState([]);

    useEffect(() => {
        // Fetch all candidates to populate the leader dropdown
        axios.get('/api/candidates/getAllCandidates')
            .then(response => {
                setLeaders(response.data);
            })
            .catch(error => {
                console.error('Error fetching candidates:', error);
            });
    }, []);

    const handleSubmit = (e) => {
        e.preventDefault();
        // Validate inputs
        if (!name || !symbol || !agenda || !leader || !state) {
            setError('All fields are required!');
            return;
        }

        const partyData = {
            name,
            symbol,
            agenda,
            leader: { aadhaarNumber: leader },
            state
        };

        if (party) {
            // Edit existing party
            axios.put(`/api/parties/${party.id}`, partyData)
                .then(() => {
                    onSave();
                    onClose();
                })
                .catch(err => {
                    console.error(err);
                    setError('Error saving party data!');
                });
        } else {
            // Add new party
            axios.post('/api/parties', partyData)
                .then(() => {
                    onSave();
                    onClose();
                })
                .catch(err => {
                    console.error(err);
                    setError('Error adding party!');
                });
        }
    };

    return (
        <div className="p-6 bg-white rounded shadow-md">
            <h2 className="text-xl mb-4">{party ? 'Edit Party' : 'Add Party'}</h2>
            {error && <div className="text-red-500 mb-4">{error}</div>}
            <form onSubmit={handleSubmit}>
                <div className="mb-4">
                    <input
                        type="text"
                        className="w-full p-2 border border-gray-300 rounded"
                        placeholder="Party Name"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                    />
                </div>
                <div className="mb-4">
                    <input
                        type="text"
                        className="w-full p-2 border border-gray-300 rounded"
                        placeholder="Party Symbol"
                        value={symbol}
                        onChange={(e) => setSymbol(e.target.value)}
                    />
                </div>
                <div className="mb-4">
                    <textarea
                        className="w-full p-2 border border-gray-300 rounded"
                        placeholder="Party Agenda"
                        value={agenda}
                        onChange={(e) => setAgenda(e.target.value)}
                    />
                </div>
                <div className="mb-4">
                    <select
                        className="w-full p-2 border border-gray-300 rounded"
                        value={leader}
                        onChange={(e) => setLeader(e.target.value)}
                    >
                        <option value="">Select Leader</option>
                        {leaders.map((candidate) => (
                            <option key={candidate.id} value={candidate.aadhaarNumber}>
                                {candidate.aadhaarNumber} - {candidate.party?.name}
                            </option>
                        ))}
                    </select>
                </div>
                <div className="mb-4">
                    <StateDropdown value={state} onChange={(e) => setState(e.target.value)} />
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
