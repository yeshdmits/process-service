import React from "react";
import { useNavigate, createSearchParams } from 'react-router-dom';
import { formatDate } from "../../service/Utils";
import List, { ListRow, ListCell } from "../list/List.component";
import { Button } from "./TaskOverview";


const TaskCompleteCustom = ({ task, submitTask, readOnly }) => {
    const componentProps = JSON.parse(task.componentProps);
    const navigate = useNavigate()
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
    const items = componentProps && componentProps.map((doc, id) =>
        <div key={id} onClick={() => renderDocument(doc)} className={ListRow}>

            <div className={ListCell}>
                {doc.documentName}
            </div>
            <div className={ListCell}>
                {formatDate(doc.modifiedAt)}
            </div>
            <div className={ListCell}>
                {doc.modifiedBy}
            </div>
        </div>
    )


    return (
        <>
            <List header={[]} items={items} />
            <div className={Button}
                onClick={handleSubmit}>
                Submit
            </div>
        </>
    );
}

export default TaskCompleteCustom;