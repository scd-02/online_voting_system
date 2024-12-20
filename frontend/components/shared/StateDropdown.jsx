// StateDropdown.js
import React from 'react';

const StateDropdown = ({ value, onChange, form }) => {
    const states = [
        'Andhra Pradesh', 'Arunachal Pradesh', 'Assam', 'Bihar', 'Chhattisgarh',
        'Goa', 'Gujarat', 'Haryana', 'Himachal Pradesh', 'Jharkhand',
        'Karnataka', 'Kerala', 'Madhya Pradesh', 'Maharashtra', 'Manipur',
        'Meghalaya', 'Mizoram', 'Nagaland', 'Odisha', 'Punjab', 'Rajasthan',
        'Sikkim', 'Tamil Nadu', 'Telangana', 'Tripura', 'Uttar Pradesh',
        'Uttarakhand', 'West Bengal', 'Delhi', 'Andaman and Nicobar Islands',
        'Chandigarh', 'Dadra and Nagar Haveli and Daman and Diu', 'Lakshadweep',
        'Puducherry'
    ];

    if(form){
        states.unshift('India');
    }

    return (
        <select value={value} onChange={onChange} className="w-full p-2 border border-gray-300 rounded">
            <option value="">Select State</option>
            {states.map(state => (
                <option key={state} value={state}>{state}</option>
            ))}
        </select>
    );
};

export default StateDropdown;
