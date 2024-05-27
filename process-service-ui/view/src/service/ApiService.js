import axios from 'axios';

const createProduct = async () => {
    return axios.post('/backend/api/v1/process',
        { processDefinitionId: "a66e73cc-f460-4698-a03b-3dd3f54de0e8" }
    ).then(response => {
        return response.data
    }).catch(error => {
        // throw new CustomError(error.message, error.status);
    });
}

const getProduct = async (processId, processInstanceId) => {
    const params = processId ? 'processId=' + processId : 'processInstanceId=' + processInstanceId;
    return axios.get('/backend/api/v1/process?' + params
    ).then(response => {
        return response.data
    }).catch(error => {
        // throw new CustomError(error.message, error.status);
    });
}

const getProductList = async () => {
    return axios.get('/backend/api/v1/process/list'
    ).then(response => {
        return response.data
    });
}

const getAddProduct = async () => {
    return axios.get('/backend/api/v1/process/definition'
    ).then(response => {
        return response.data
    });
}

const completeTask = async (taskFormData) => {
    return axios.put('/backend/api/v1/process', taskFormData
    ).then(response => {
        return response.data
    }).catch(error => {
        // throw new CustomError(error.message, error.status);
    });
}

const getDocContent = async (documentId) => {
    return fetch('/backend/api/v1/process/document?documentId=' + documentId
    )
        .then(response => response.blob())
        .then(blob => {
            return blob;
        }).catch(error => {
            // throw new CustomError(error.message, error.status);
        });
}

export { createProduct, getProduct, completeTask, getDocContent, getProductList, getAddProduct };