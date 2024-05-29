import React from "react";
import Status from "./Status.component";

const DocumentStatus = ({ docStatus }) => {
    const status = docStatus === 'Created' ?
        "progress"
        :
        docStatus === 'Completed' ?
            "done"
            :
            docStatus === 'Sent' ?
                "progress"
                :
                "reject"
    return (
        <Status status={status} />
    );
}

export default DocumentStatus;