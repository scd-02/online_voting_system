import React, { useState } from 'react';
import axios from 'axios';
import PropTypes from 'prop-types';

axios.defaults.withCredentials = true;

const CandidateForm = ({ candidate, parties, onClose, onSave }) => {
    const [aadhaarNumber] = useState(candidate ? candidate.aadhaarNumber : '');
    const [partyId, setPartyId] = useState(candidate?.party?.id?.toString() || '');
    const [error, setError] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!aadhaarNumber || !partyId) {
            setError('All fields are required!');
            return;
        }

        const API_URL = process.env.NEXT_PUBLIC_API_URL;
        const candidateData = {
            aadhaarNumber,
            party: { id: parseInt(partyId, 10) },
        };

        try {
            let response;
            if (candidate) {
                // Edit existing candidate
                const updatedCandidateData = {
                    id: candidate.id, // Ensure the ID is sent
                    ...candidateData,
                };
                response = await axios.put(`${API_URL}/candidates/${candidate.id}`, updatedCandidateData);
            } else {
                // Add new candidate
                response = await axios.post(`${API_URL}/candidates/create`, candidateData);
            }
            onSave(response.data, !candidate);
            onClose();
        } catch (err) {
            console.error(err);
            setError('Error saving candidate data!');
        }
    };

    return (
        <div className="p-6 bg-white rounded shadow-md">
            <h2 className="text-xl mb-4">{candidate ? 'Edit Candidate' : 'Add Candidate'}</h2>
            {error && <div className="text-red-500 mb-4">{error}</div>}
            <form onSubmit={handleSubmit}>
                <div className="mb-4">
                    <p className="w-full p-2 border border-gray-300 rounded bg-gray-50">
                        Aadhaar Number: <strong>{aadhaarNumber}</strong>
                    </p>
                </div>
                <div className="mb-4">
                    {candidate && candidate.party && (
                        <p className="mb-2">
                            Current Party: <strong>{candidate.party.name}</strong>
                        </p>
                    )}
                    <select
                        className="w-full p-2 border border-gray-300 rounded"
                        value={partyId}
                        onChange={(e) => setPartyId(e.target.value)}
                    >
                        <option value="">Select Party</option>
                        {parties.map((party) => (
                            <option key={party.id} value={party.id.toString()}>
                                {party.name}
                            </option>
                        ))}
                    </select>
                </div>
                <div className="flex justify-between">
                    <button
                        type="button"
                        onClick={onClose}
                        className="bg-gray-500 text-white px-4 py-2 rounded"
                    >
                        Cancel
                    </button>
                    <button
                        type="submit"
                        className="bg-blue-500 text-white px-4 py-2 rounded"
                    >
                        {candidate ? 'Update Candidate' : 'Add Candidate'}
                    </button>
                </div>
            </form>
        </div>
    );
};

CandidateForm.propTypes = {
    candidate: PropTypes.object,
    parties: PropTypes.array.isRequired,
    onClose: PropTypes.func.isRequired,
    onSave: PropTypes.func.isRequired,
};

export default CandidateForm;
