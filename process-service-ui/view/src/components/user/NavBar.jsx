import React, { useEffect, useRef, useState } from "react";
import { useSwipeable } from "react-swipeable";
import { useApiContext } from "../../context/ApiContext";
import { useNavigate, createSearchParams } from "react-router-dom";
// grid-cols-2 sm:grid-cols-1
const NavBar = () => {
    const { logout } = useApiContext();
    const [isOpen, setIsOpen] = useState(false);
    const [account, setAccount] = useState('');
    const { getAccountName } = useApiContext();

    const navigate = useNavigate();

    const handleProfile = () => {
        console.log("handleProfile")
        navigate("/account")
    }

    const handleProcesses = () => {
        navigate("/",
            {
                state: {
                    addProduct: false,
                    features: true,
                    process: false
                }
            });
    }

    const handleMouseEnter = () => {
        setIsOpen(true);
    };

    const handleMouseLeave = () => {
        setIsOpen(false);
    };

    useEffect(() => {
        const fetchApi = () => {
            getAccountName().then(response => setAccount(response))
        }

        fetchApi();
    }, [])

    return (
        <div className="w-full bg-sky-900 p-1 text-white flex justify-around items-center shadow-md">
            <div className="relative inline-block text-left" >
                <div className="cursor-pointer px-4 py-2 bg-sky-600 text-white rounded-md" onClick={handleProcesses}>
                    Processes
                </div>
            </div>
            <div className="relative inline-block text-left" onMouseEnter={handleMouseEnter} >
                <div className="cursor-pointer px-4 py-2 bg-sky-600 text-white rounded-md">
                    {account}
                </div>

                {isOpen && (
                    <div className="absolute right-0 mt-2 w-48 bg-white rounded-md shadow-lg z-10" onMouseEnter={handleMouseEnter} onMouseLeave={handleMouseLeave}>
                        <div className="py-1">
                            <div className="hover:cursor-pointer block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100"
                                onClick={handleProfile}>
                                Profile
                            </div>
                            <div className="hover:cursor-pointer block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100"
                                onClick={logout}>
                                LogOut
                            </div>
                        </div>
                    </div>
                )}
            </div>

        </div>
    );
}

export default NavBar;