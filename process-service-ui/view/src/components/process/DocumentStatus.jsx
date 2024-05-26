import React from "react";
import StatusDone from '../../svgs/status-done.svg?react'
import StatusInProgress from '../../svgs/status-inprogress.svg?react'
import StatusRejected from '../../svgs/status-rejected.svg?react'

const DocumentStatus = (props) => {
    return (
        <div className="table-status">
            {props.docStatus === 'Created' ? <StatusInProgress width={'20px'} /> : props.docStatus === 'Completed' ? <StatusDone width={'20px'} /> : props.docStatus === 'Sent' ?  <StatusInProgress width={'20px'} />:<StatusRejected width={'20px'} />}
            <div className="table-status-text">{props.docStatus}</div>
        </div>
    );
}

export default DocumentStatus;