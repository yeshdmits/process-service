import axios from 'axios';
import { getToken, fetchToken } from './AuthService.js';


const createProduct = async () => {
    const headers = {
        'Authorization': 'Bearer ' + getToken()
    };
    return axios.post('/backend/api/v1/process',
        { processDefinitionId: "a66e73cc-f460-4698-a03b-3dd3f54de0e8" },
        { headers }
    ).then(response => {
        return response.data
    }).catch(error => {
        // throw new CustomError(error.message, error.status);
    });
};

const getProduct = async (processId, processInstanceId) => {
    const headers = {
        'Authorization': 'Bearer ' + getToken()
    };
    const params = processId ? 'processId=' + processId : 'processInstanceId=' + processInstanceId;
    return axios.get('/backend/api/v1/process?' + params,
        { headers }
    ).then(response => {
        return response.data
    }).catch(error => {
        // throw new CustomError(error.message, error.status);
    });
};

const getProductList = async () => {
    const headers = {
        'Authorization': 'Bearer ' + getToken()
    };
    return axios.get('/backend/api/v1/process/list',
        { headers }
    ).then(response => {
        return response.data
    });
};


const completeTask = async (taskFormData) => {
    const headers = {
        'Authorization': 'Bearer ' + getToken()
    };
    return axios.put('/backend/api/v1/process',
        taskFormData, { headers }
    ).then(response => {
        return response.data
    }).catch(error => {
        // throw new CustomError(error.message, error.status);
    });
};

const getDocContent = async (documentId) => {
    const headers = {
        'Authorization': 'Bearer ' + getToken()
    };
    return fetch('/backend/api/v1/process/document?documentId=' + documentId,
        { headers }
    )
        .then(response => response.blob())
        .then(blob => {
            return blob;
        }).catch(error => {
            // throw new CustomError(error.message, error.status);
        });
};

const requestLogin = (username, password) => {
    return fetchToken(username, password);
}

export { createProduct, getProduct, completeTask, getDocContent, getProductList, requestLogin };