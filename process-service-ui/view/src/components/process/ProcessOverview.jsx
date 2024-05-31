import React, { useEffect, useState } from "react";
import { useNavigate, useSearchParams, createSearchParams } from 'react-router-dom';
import GoBack from '../../svgs/go-back.svg?react'
import ActivationStatus from "../status/ActivationStatus";
import TaskList from "../list/TaskList";
import DocumentList from "../list/DocumentList";
import { useApiContext } from "../../context/ApiContext";
import Header from "../list/Header.component";
import { Button } from "../task/TaskOverview";


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
                {processData.metadata &&
                    <div className={Button}
                        onClick={() => handleTaskComplete(processData.metadata.taskId)}>
                        Work on task
                    </div>
                }
                <div>
                    <ActivationStatus status={processData.processStatus} />
                </div>
                {/* <div className="product-summary"></div> */}
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