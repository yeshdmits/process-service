import React, { useState } from "react";

const DatePicker = ({ field, filters, handleDateFilter, setIsOpen }) => {
    const [fromDate, setFromDate] = useState(filters.filter(i => i.name === field && i.criteria === 'GT').map(i => i.value));
    const [toDate, setToDate] = useState(filters.filter(i => i.name === field && i.criteria === 'LT').map(i => i.value));

    const handleFilterSubmit = () => {
        let newFilters = [...filters].filter(i => i.name !== field);
        newFilters.push({name: field, criteria: 'GT', value: fromDate})
        newFilters.push({name: field, criteria: 'LT', value: toDate})
        handleDateFilter(newFilters, field);
        setIsOpen(false)
    }

    return (
        <>
            <div className="text-gray-700 container mx-auto p-4">
                <div className="mb-4">
                    <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="fromDate">
                        From Date
                    </label>
                    <input
                        type="date"
                        id="fromDate"
                        value={fromDate}
                        onChange={(e) => setFromDate(e.target.value)}
                        className="p-2 border border-gray-300 rounded-md w-full"
                    />
                </div>
                <div className="mb-4">
                    <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="toDate">
                        To Date
                    </label>
                    <input
                        type="date"
                        id="toDate"
                        value={toDate}
                        onChange={(e) => setToDate(e.target.value)}
                        className="p-2 border border-gray-300 rounded-md w-full"
                    />
                </div>
                <div className="mt-4">
                    <button
                        onClick={handleFilterSubmit}
                        className="bg-blue-500 text-white p-2 rounded-md"
                    >
                        Submit
                    </button>
                </div>
            </div>
        </>
    )
}

export default DatePicker;