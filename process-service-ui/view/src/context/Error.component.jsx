import React from "react";
import { formatTimestamp } from "../service/Utils";

const ErrorDialog = ({ error, onClose }) => {

    return (
        <>
            <div className="fixed inset-0 flex justify-center items-center">
                {/* <div className="absolute inset-0 bg-gray-500 opacity-75">

                </div> */}
                <div className="bg-white w-full max-w-md p-8 rounded-lg shadow-lg">
                    <div className="text-xl font-semibold text-red-600 mb-4">
                        {error.title}
                    </div>
                    <div className="text-gray-800 mb-4">
                        {error.detail}
                    </div>
                    <div className="text-gray-800 mb-4">
                        {formatTimestamp(error.timestamp)}
                    </div>
                    <div className="flex justify-end">
                        <button
                            className="text-sm font-semibold text-gray-600 hover:text-gray-800 focus:outline-none"
                            onClick={onClose}
                        >
                            Close
                        </button>
                    </div>
                </div>
            </div>
        </>
    )
}

export default ErrorDialog;