import React, { useState } from 'react';
import axios from 'axios';
import PropTypes from 'prop-types';

axios.defaults.withCredentials = true;

const CandidateForm = ({ candidate, parties, onClose, onSave }) => {

    // console.log("candidate", candidate)
    // console.log("parties", parties)

    const [aadhaarNumber, setAadhaarNumber] = useState(candidate ? candidate.aadhaarNumber : '');
    const [partyId, setPartyId] = useState(candidate?.partyName || '');
    const [error, setError] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();

        // Check if Aadhaar number is exactly 12 characters long
        if (aadhaarNumber.length !== 12) {
            setError('Aadhaar number must be exactly 12 characters long!');
            return;
        }

        if (!aadhaarNumber || !partyId) {
            setError('All fields are required!');
            return;
        }

        const API_URL = process.env.NEXT_PUBLIC_API_URL;
        const candidateData = {
            aadhaarNumber,
            party: { name: partyId }
        };

        try {
            let response;
            if (candidate) {
                // Edit existing candidate
                response = await axios.put(`${API_URL}/candidate/${candidate.aadhaarNumber}`, null, {
                    params: {
                        partyName: partyId
                    },
                });
            } else {
                // Add new candidate
                response = await axios.post(`${API_URL}/candidate/create`, candidateData);
            }
            onSave(response.data, !candidate);
            onClose();
        } catch (err) {
            console.error(err);
            if (err.response && err.response.data) {
                setError(err.response.data);
            } else {
                setError('Error saving candidate data!');
            }
        }
    };

    const handleAadhaarChange = (e) => {
        const value = e.target.value;
        // Restrict input to 12 characters max and only allow numbers
        if (value.length <= 12 && /^\d*$/.test(value)) {
            setAadhaarNumber(value);
        }
    };

    return (
        <div className="p-6 bg-white rounded shadow-md">
            <h2 className="text-xl mb-4">{candidate ? 'Edit Candidate' : 'Add Candidate'}</h2>
            {error && <div className="text-red-500 mb-4">{error}</div>}
            <form onSubmit={handleSubmit}>
                {/* Aadhaar Number Field */}
                <div className="mb-4">
                    <label className="block mb-2">Aadhaar Number</label>
                    {candidate ? (
                        <p className="w-full p-2 border border-gray-300 rounded bg-gray-50">
                            <strong>{aadhaarNumber}</strong>
                        </p>
                    ) : (
                        <input
                            type="text"
                            className="w-full p-2 border border-gray-300 rounded"
                            value={aadhaarNumber}
                            onChange={handleAadhaarChange}
                            placeholder="Enter Aadhaar Number"
                            maxLength={12} // Ensures that the input is limited to 12 characters
                        />
                    )}
                </div>

                {/* Party Selection Field */}
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
                            <option key={party.name} value={party.name}>
                                {party.name}
                            </option>
                        ))}
                    </select>
                </div>

                {/* Action Buttons */}
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
