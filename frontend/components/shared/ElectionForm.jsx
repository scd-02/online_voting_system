// ElectionForm.js
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import StateDropdown from './StateDropdown';

const ElectionForm = ({ election, onClose, onSave }) => {
    const [name, setName] = useState(election ? election.name : '');
    const [state, setState] = useState(election ? election.state : '');
    const [eligibleParties, setEligibleParties] = useState(election ? election.eligiblePartys : []);
    const [error, setError] = useState('');

    const [parties, setParties] = useState([]);

    useEffect(() => {
        // Fetch all parties for the election form
        axios.get('/api/parties/getAllParties')
            .then(response => {
                setParties(response.data);
            })
            .catch(error => {
                console.error('Error fetching parties:', error);
            });
    }, []);

    const handleSubmit = (e) => {
        e.preventDefault();
        if (!name || !state || eligibleParties.length === 0) {
            setError('All fields are required!');
            return;
        }

        const electionData = {
            name,
            state,
            eligiblePartys: eligibleParties
        };

        if (election) {
            // Edit existing election
            axios.put(`/api/elections/${election.id}`, electionData)
                .then(() => {
                    onSave();
                    onClose();
                })
                .catch(err => {
                    console.error(err);
                    setError('Error saving election data!');
                });
        } else {
            // Add new election
            axios.post('/api/elections', electionData)
                .then(() => {
                    onSave();
                    onClose();
                })
                .catch(err => {
                    console.error(err);
                    setError('Error adding election!');
                });
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
                    <select
                        multiple
                        className="w-full p-2 border border-gray-300 rounded"
                        value={eligibleParties}
                        onChange={(e) => setEligibleParties(Array.from(e.target.selectedOptions, option => option.value))}
                    >
                        {parties.map((party) => (
                            <option key={party.id} value={party.id}>
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
