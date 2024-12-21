import React, { useState, useEffect } from 'react';
import axios from 'axios';

const AdminForm = ({ onClose, onSave }) => {
    const [admins, setAdmins] = useState([]);
    const [newAdmin, setNewAdmin] = useState('');

    useEffect(() => {
        fetchAdmins();
    }, []);

    const fetchAdmins = async () => {
        try {
            const API_URL = process.env.NEXT_PUBLIC_API_URL;
            const response = await axios.get(`${API_URL}/admin/getAllAdmins`);
            setAdmins(response.data);
            console.log(response.data);
        } catch (error) {
            console.error("Error fetching admins:", error);
        }
    };

    const handleAddAdmin = async () => {
        // Check if Aadhaar number length is 12
        if (newAdmin.length !== 12) {
            alert('Aadhaar number must be exactly 12 characters long!');
            return;
        }

        try {
            const API_URL = process.env.NEXT_PUBLIC_API_URL;
            const response = await axios.post(`${API_URL}/admin/create`, { userId: newAdmin });
            setAdmins([...admins, response.data]);
            setNewAdmin('');
        } catch (error) {
            console.error("Error adding admin:", error);
        }
    };

    const handleRemoveAdmin = async (adminId) => {
        try {
            const API_URL = process.env.NEXT_PUBLIC_API_URL;
            await axios.delete(`${API_URL}/admin/${adminId}`);
            setAdmins(admins.filter(admin => admin.userId !== adminId));
        } catch (error) {
            console.error("Error removing admin:", error);
        }
    };

    const handleAdminChange = (e) => {
        const value = e.target.value;
        // Restrict the value to a maximum length of 12 characters
        if (value.length <= 12) {
            setNewAdmin(value);
        }
    };

    return (
        <div className="p-6 bg-white rounded shadow-md">
            <h2 className="text-xl mb-4">Manage Admins</h2>
            <div className="mb-4">
                <input
                    type="text"
                    placeholder="New Admin Aadhaar Number"
                    value={newAdmin}
                    onChange={handleAdminChange}
                    className="w-full p-2 border border-gray-300 rounded"
                    maxLength={12}  // Max length to allow only 12 characters
                />
                <button
                    onClick={handleAddAdmin}
                    className="mt-2 px-4 py-2 bg-blue-500 text-white rounded"
                >
                    Add Admin
                </button>
            </div>
            <ul className=''>
                {admins.map(admin => (
                    <li key={admin.userId} className="mb-2 flex justify-between items-center border border-gray-300 px-3 py-2 rounded">
                        <span>{admin.userId}</span>
                        <button
                            onClick={() => handleRemoveAdmin(admin.userId)}
                            className="text-red-500"
                        >
                            Remove
                        </button>
                    </li>
                ))}
            </ul>
            <div className="mt-4 flex justify-end">
                <button
                    onClick={onClose}
                    className="px-4 py-2 bg-gray-500 text-white rounded"
                >
                    Close
                </button>
            </div>
        </div>
    );
};

export default AdminForm;