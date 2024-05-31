import React, { useEffect, useState } from "react";
import { useNavigate, createSearchParams, useSearchParams } from 'react-router-dom';
import { useApiContext } from "../../context/ApiContext";
import TaskCompleteCustom from "./TaskCompleteCustom"
import TaskCompleteJson from "./TaskCompleteJson";
import Header from "../list/Header.component";
import GoBack from '../../svgs/go-back.svg?react'

export const Button = 'hover:cursor-pointer hover:bg-emerald-500 sticky bottom-2 w-full flex items-center justify-center min-h-14 mb-2 text-white mt-4 bg-emerald-600 border border-cyan-900';


const TaskOverview = () => {
    const navigate = useNavigate();
    const [searchParams] = useSearchParams();
    const [taskData, setTaskData] = useState(null);
    const { getTask, completeTask } = useApiContext();

    const submitTask = (formData, decision) => {
        completeTask({
            taskId: taskData.taskId,
            formData: formData,
            decision: decision
        }).then(() => navigate({
            pathName: "/",
            search: `?${createSearchParams({ processId: taskData.processId })}`
        },
            {
                state: {
                    addProduct: false,
                    features: false,
                    process: true,
                    task: false,
                    doc: false
                }
            }))
    }

    const cancelTask = () => {
        navigate({
            pathName: "/",
            search: `?${createSearchParams({ processId: taskData.processId })}`
        },
            {
                state: {
                    addProduct: false,
                    features: false,
                    process: true,
                    task: false,
                    doc: false
                }
            })
    }

    useEffect(() => {
        const fetchDataFromApi = async () => {
            const fetchedData = await getTask(searchParams.get("taskId"));
            setTaskData(fetchedData);
        };
        if (searchParams.get("taskId")) {
            fetchDataFromApi();
        }
    }, [searchParams.get("taskId")]);

    if (!taskData) {
        return <div>Loading...</div>
    }
    return (
        <Header name={taskData.taskName} elem={<GoBack onClick={cancelTask} />}>
            {taskData.componentProps ?
                <TaskCompleteCustom task={taskData} readOnly={taskData.taskStatus !== 'In progress'} submitTask={submitTask} />
                :
                <TaskCompleteJson task={taskData} readOnly={taskData.taskStatus !== 'In progress'} submitTask={submitTask} />

            }
        </Header>
    );

}

export default TaskOverview;