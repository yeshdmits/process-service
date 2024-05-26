import React from "react";
import StatusDone from '../../svgs/status-done.svg?react'
import StatusInProgress from '../../svgs/status-inprogress.svg?react'
import StatusRejected from '../../svgs/status-rejected.svg?react'

const TaskStatus = (props) => {
    return (
        <div className="table-status">
            {props.taskStatus === 'In progress' ? <StatusInProgress width={'20px'} /> : props.taskStatus === 'Completed' ? <StatusDone width={'20px'} /> : <StatusRejected width={'20px'} />}
            <div className="table-status-text">{props.taskStatus}</div>
        </div>
    );
}

export default TaskStatus;