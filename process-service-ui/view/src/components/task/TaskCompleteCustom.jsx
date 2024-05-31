import React from "react";
import { useNavigate, createSearchParams } from 'react-router-dom';
import { formatDate } from "../../service/Utils";
import List, { ListRow, ListCell } from "../list/List.component";
import { DocItems } from "../list/DocumentList";
import { Button } from "./TaskOverview";
import Download from "../../svgs/download.svg?react";
import { useApiContext } from "../../context/ApiContext";

const TaskCompleteCustom = ({ task, submitTask, readOnly }) => {
    const componentProps = JSON.parse(task.componentProps);
    const navigate = useNavigate()
    const { handleDownloadDocument } = useApiContext()
    const handleSubmit = () => {
        if (readOnly) {
            return
        }
        submitTask(JSON.stringify(componentProps), 'completed')
    }

    const renderDocument = (doc) => {
        navigate({
            pathName: "/",
            search: `?${createSearchParams({ taskId: task.taskId })}&${createSearchParams({ docId: doc.documentId })}`
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


    return (
        <>
            <List header={["Documents to Distribute"]} items={DocItems(componentProps, renderDocument)} />
            <div className={Button}
                onClick={handleSubmit}>
                Submit
            </div>
        </>
    );
}

export default TaskCompleteCustom;