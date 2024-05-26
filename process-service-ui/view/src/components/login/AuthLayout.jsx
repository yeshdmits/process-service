import React, { useEffect } from 'react';
import { useNavigate, useLocation, Outlet } from 'react-router-dom';
import { getToken } from '../../service/AuthService.js';

const AuthLayout = (props) => {
    const navigate = useNavigate();
    const { state } = useLocation();

    useEffect(() => {
        const token = getToken();
        localStorage.setItem('requestedUrl', props.url)
        if (!token) {
            navigate("/login", { state: state });
        }
    }, [navigate, props.url, state]);
    return (
        <Outlet />
    );
}


export default AuthLayout;