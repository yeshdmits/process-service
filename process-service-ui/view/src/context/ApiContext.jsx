import React, { createContext, useContext, useEffect } from 'react';
import axios from 'axios';
const isDevEnv = import.meta.env.DEV;

const ApiContext = createContext(null);

export const useApiContext = () => {
    return useContext(ApiContext);
}

const getBackendUrl = () => {
    return isDevEnv ? "http://localhost:8081" : "/backend"
}

export const ApiContextProvider = ({ children }) => {


    const createProduct = async (productId) => {
        return axios.post(getBackendUrl() + '/api/v1/process',
            { processDefinitionId: productId }
        ).then(response => {
            return response.data
        }).catch(error => {
            // throw new CustomError(error.message, error.status);
        });
    }

    const getProduct = async (processId) => {
        return axios.get(getBackendUrl() + '/api/v1/process?processId=' + processId)
            .then(response => {
                return response.data
            }).catch(error => {
                // throw new CustomError(error.message, error.status);
            });
    }

    const getTask = async (taskId) => {
        return axios.get(getBackendUrl() + '/api/v1/process/task?taskId=' + taskId)
            .then(response => {
                return response.data
            }).catch(error => {
                // throw new CustomError(error.message, error.status);
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
        });
    }

    const completeTask = async (taskFormData) => {
        return axios.put(getBackendUrl() + '/api/v1/process', taskFormData)
            .then(response => {
                return response.data
            }).catch(error => {
                // throw new CustomError(error.message, error.status);
            });
    }

    const getDocContent = async (documentId) => {
        return fetch(getBackendUrl() + '/api/v1/process/document?documentId=' + documentId
        )
            .then(response => response.blob())
            .then(blob => {
                return blob;
            }).catch(error => {
                // throw new CustomError(error.message, error.status);
            });
    }

    useEffect(() => { }, [])

    return (
        <ApiContext.Provider value={{
            createProduct,
            getProduct,
            getProductList,
            getAddProduct,
            completeTask,
            getDocContent,
            getTask
        }}>
            {children}
        </ApiContext.Provider>
    );
}