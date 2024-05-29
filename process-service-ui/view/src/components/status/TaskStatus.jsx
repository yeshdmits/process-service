import React from "react";
import Status from "./Status.component";

const TaskStatus = ({ taskStatus }) => {
    const status = taskStatus === 'In progress' ?
                "progress" 
                :
                taskStatus === 'Completed' ?
                    "done" 
                    :
                    "reject"
    return (
        <Status status={status}/>
    );
}

export default TaskStatus;