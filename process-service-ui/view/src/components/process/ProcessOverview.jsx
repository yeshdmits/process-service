import React, { useEffect, useState } from "react";
import { useNavigate, useSearchParams, createSearchParams } from 'react-router-dom';
import GoBack from '../../svgs/go-back.svg?react'
import ActivationStatus from "../status/ActivationStatus";
import TaskList from "../list/TaskList";
import DocumentList from "../list/DocumentList";
import { useApiContext } from "../../context/ApiContext";
import Header from "../list/Header.component";
import { Button } from "../task/TaskOverview";
import { formatDate } from "../../service/Utils";


const ProcessOverview = () => {
    const [searchParams] = useSearchParams();
    const [processData, setProcessData] = useState({});
    const { getProduct } = useApiContext();

    const navigate = useNavigate();

    const handleTaskComplete = (id) => {
        navigate({
            pathName: "/",
            search: `?${createSearchParams({ taskId: id })}`
        },
            {
                state: {
                    addProduct: false,
                    features: false,
                    process: false,
                    task: true,
                    doc: false
                }
            });
    }


    const handleViewDocument = (doc) => {
        navigate({
            pathName: "/",
            search: `?${createSearchParams({ processId: searchParams.get("processId") })}&${createSearchParams({ docId: doc.documentId })}`
        },
            {
                state: {
                    addProduct: false,
                    features: false,
                    process: false,
                    task: false,
                    doc: true
                }
            })
    }
    const handleViewTask = (task) => {
        if (task.taskStatus === 'In progress') {
            handleTaskComplete(processData.metadata.taskId);
            return;
        }
        handleTaskComplete(task.taskId);
    }

    const handleClose = () => {
        navigate("/", {
            state: {
                addProduct: false,
                features: true,
                process: false,
                task: false,
                doc: false
            }
        });
    }

    useEffect(() => {
        const fetchDataFromApi = async () => {
            const fetchedData = await getProduct(searchParams.get("processId"));
            setProcessData(fetchedData);
        };

        if (searchParams.get("processId")) {
            fetchDataFromApi();
        }
    }, [searchParams.get("processId")]);

    return (
        <Header name={processData.displayName} elem={<GoBack onClick={handleClose} />}>
            <div className="mt-1 grid gap-1">
                <div className="flex flex-col justify-around m-2">
                    <div className="bg-gray-100 p-4 rounded-md shadow-md w-full">
                        <div className="font-semibold text-gray-700">
                            {processData.createdBy}
                        </div>
                        <div className="text-gray-500 text-sm mt-2">
                            Name of the user who created the process
                        </div>
                        <div className="font-semibold text-gray-700 mt-4">
                            {formatDate(processData.createdAt)}
                        </div>
                        <div className="text-gray-500 text-sm mt-2">
                            Date and time when the process was created
                        </div>
                    </div>
                    <div className="bg-gray-100 p-4 rounded-md shadow-md w-full">
                        <div className="font-semibold text-gray-700 mt-4">
                            {processData.modifiedBy}
                        </div>
                        <div className="text-gray-500 text-sm mt-2">
                            Name of the user who last modified the process
                        </div>
                        <div className="font-semibold text-gray-700">
                            {formatDate(processData.modifiedAt)}
                        </div>
                        <div className="text-gray-500 text-sm mt-2">
                            Date and time when the process was last modified
                        </div>
                    </div>

                </div>
                {processData.metadata &&
                    <div className={Button}
                        onClick={() => handleTaskComplete(processData.metadata.taskId)}>
                        Work on task
                    </div>
                }
                <div>
                    <ActivationStatus status={processData.processStatus} />
                </div>
                <div>
                    <TaskList handleViewTask={handleViewTask} taskList={processData.taskList} />
                </div>
                <div>
                    <DocumentList handleViewDocument={handleViewDocument} documentList={processData.documentList} />
                </div>
            </div>
        </Header>
    );
}

export default ProcessOverview;