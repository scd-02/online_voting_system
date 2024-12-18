// CandidateForm.js
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import StateDropdown from './StateDropdown';

const CandidateForm = ({ candidate, onClose, onSave }) => {
    const [aadhaarNumber, setAadhaarNumber] = useState(candidate ? candidate.aadhaarNumber : '');
    const [partyId, setPartyId] = useState(candidate ? candidate.party.id : '');
    const [error, setError] = useState('');

    const [parties, setParties] = useState([]);

    useEffect(() => {
        // Fetch all parties to populate the dropdown
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
        if (!aadhaarNumber || !partyId) {
            setError('All fields are required!');
            return;
        }

        const candidateData = {
            aadhaarNumber,
            party: { id: partyId }
        };

        if (candidate) {
            // Edit existing candidate
            axios.put(`/api/candidates/${candidate.id}`, candidateData)
                .then(() => {
                    onSave();
                    onClose();
                })
                .catch(err => {
                    console.error(err);
                    setError('Error saving candidate data!');
                });
        } else {
            // Add new candidate
            axios.post('/api/candidates', candidateData)
                .then(() => {
                    onSave();
                    onClose();
                })
                .catch(err => {
                    console.error(err);
                    setError('Error adding candidate!');
                });
        }
    };

    return (
        <div className="p-6 bg-white rounded shadow-md">
            <h2 className="text-xl mb-4">{candidate ? 'Edit Candidate' : 'Add Candidate'}</h2>
            {error && <div className="text-red-500 mb-4">{error}</div>}
            <form onSubmit={handleSubmit}>
                <div className="mb-4">
                    <input
                        type="text"
                        className="w-full p-2 border border-gray-300 rounded"
                        placeholder="Aadhaar Number"
                        value={aadhaarNumber}
                        onChange={(e) => setAadhaarNumber(e.target.value)}
                    />
                </div>
                <div className="mb-4">
                    <select
                        className="w-full p-2 border border-gray-300 rounded"
                        value={partyId}
                        onChange={(e) => setPartyId(e.target.value)}
                    >
                        <option value="">Select Party</option>
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
                        {candidate ? 'Update Candidate' : 'Add Candidate'}
                    </button>
                </div>
            </form>
        </div>
    );
};

export default CandidateForm;
