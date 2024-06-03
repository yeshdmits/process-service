import React, { createContext, useContext, useEffect, useState } from 'react';
import axios from 'axios';
import ErrorDialog from './Error.component';
import { useNavigate } from 'react-router-dom';
const isDevEnv = import.meta.env.DEV;

const ApiContext = createContext(null);

export const useApiContext = () => {
    return useContext(ApiContext);
}

const getBackendUrl = () => {
    return isDevEnv ? "http://localhost:8081" : "/backend"
}

export const ApiContextProvider = ({ children }) => {
    const [error, setError] = useState(null);
    const [showError, setShowError] = useState(true);
    const navigate = useNavigate();

    const createProduct = async (productId) => {
        return axios.post(getBackendUrl() + '/api/v1/process',
            { processDefinitionId: productId }
        ).then(response => {
            return response.data
        }).catch(error => {
            setError(error.response.data)
            setShowError(true)
            throw error;
        });
    }

    const getProduct = async (processId) => {
        return axios.get(getBackendUrl() + '/api/v1/process?processId=' + processId)
            .then(response => {
                return response.data
            }).catch(error => {
                setError(error.response.data)
                setShowError(true)
                throw error;
            });
    }

    const getTask = async (taskId) => {
        return axios.get(getBackendUrl() + '/api/v1/process/task?taskId=' + taskId)
            .then(response => {
                return response.data
            }).catch(error => {
                setError(error.response.data)
                setShowError(true)
                throw error;
            });
    }

    const getProductList = async () => {
        const response = await axios.get(getBackendUrl() + '/api/v1/process/list');
        return response.data;

    }

    const getAddProduct = async () => {
        return axios.get(getBackendUrl() + '/api/v1/process/definition'
        ).then(response => {
            return response.data
        }).catch(error => {
            setError(error.response.data)
            setShowError(true)
            throw error;
        });
    }

    const completeTask = async (taskFormData) => {
        return axios.put(getBackendUrl() + '/api/v1/process', taskFormData)
            .then(response => {
                return response.data
            }).catch(error => {
                setError(error.response.data)
                setShowError(true)
                throw error;
            });
    }

    const getDocContent = async (documentId) => {
        return fetch(getBackendUrl() + '/api/v1/process/document?documentId=' + documentId
        )
            .then(response => response.blob())
            .then(blob => {
                return blob;
            }).catch(error => {
                setError(error.response.data)
                setShowError(true)
                throw error;
            });
    }
    const handleDownloadDocument = (doc) => {
        getDocContent(doc.documentId)
            .then((response) => {
                window.URL.createObjectURL(response);
                const blobUrl = window.URL.createObjectURL(response);
                const link = document.createElement('a');
                link.href = blobUrl;
                link.download = doc.documentName;
                document.body.appendChild(link);
                link.click();
                document.body.removeChild(link);
                URL.revokeObjectURL(blobUrl);
            }).catch(error => {
                setError(error.response.data)
                setShowError(true)
                throw error;
            });
    }

    const getFilteredList = async (page, size, sortBy, order, filters) => {
        return axios.post(getBackendUrl() + '/api/v1/process/filtered',
            { page: { page, size, sortBy, order }, filters })
            .then((response) => {
                return response.data;
            }).catch(error => {
                setError(error.response.data)
                setShowError(true)
                throw error;
            });
    }

    const logout = () => {
        const link = document.createElement('a');
        link.href = '/logout';
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
    }

    const getUserInfo = async () => {
        return axios.get(getBackendUrl() + '/userInfo'
        ).then(response => {
            return response.data
        }).catch(error => {
            setError(error.response.data)
            setShowError(true)
            throw error;
        });
    }

    const getAccountName = async () => {
        return axios.get(getBackendUrl() + '/account'
        ).then(response => {
            return response.data
        }).catch(error => {
            setError(error.response.data)
            setShowError(true)
            throw error;
        });
    }

    const getAssignedTasks = async () => {
        return axios.get(getBackendUrl() + '/api/v1/process/task/assigned'
        ).then(response => {
            return response.data
        }).catch(error => {
            setError(error.response.data)
            setShowError(true)
            throw error;
        });
    }

    const onClose = () => {
        setShowError(false);
        setError(null);
        navigate("/")
    }

    useEffect(() => { }, [])

    if (error && showError) {
        return <ErrorDialog error={error} onClose={onClose}/>
    }

    return (
        <ApiContext.Provider value={{
            createProduct,
            getProduct,
            getProductList,
            getAddProduct,
            completeTask,
            getDocContent,
            getTask,
            handleDownloadDocument,
            getFilteredList,
            logout, getUserInfo, getAssignedTasks, getAccountName
        }}>
            {children}
        </ApiContext.Provider>
    );
}