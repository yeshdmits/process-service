import React from "react";

const Checkbox = ({ label, checked, onChange }) => {
    return (
        <>
            <label className="flex items-center space-x-3">
                <input
                    type="checkbox"
                    className="hidden"
                    checked={checked}
                    onChange={onChange}
                />
                <div
                    className={`w-5 h-5 border-2 border-gray-300 rounded-md flex items-center justify-center ${checked ? 'bg-blue-500 border-blue-500' : 'bg-white'}`}
                >
                    {checked && (
                        <svg
                            xmlns="http://www.w3.org/2000/svg"
                            className="h-4 w-4 text-white"
                            fill="none"
                            viewBox="0 0 24 24"
                            stroke="currentColor"
                        >
                            <path
                                strokeLinecap="round"
                                strokeLinejoin="round"
                                strokeWidth="2"
                                d="M5 13l4 4L19 7"
                            />
                        </svg>
                    )}
                </div>
                <span className="text-gray-700">{label}</span>
            </label>
        </>
    )
}

export default Checkbox;