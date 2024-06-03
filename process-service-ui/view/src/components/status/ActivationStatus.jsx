import React from "react";
import Header from "../list/Header.component";
import Status, {Done, Rejected, InProgress} from "./Status.component";

const ActivationStatus = ({ status }) => {
    const createdStatus = status === 'Created' ? "progress" : "done";
    const inProgressStatus = status === 'In Progress' ? "progress" : status === 'Created' ? "pending" : "done";
    const activeStatus = status === 'Active' ? "done" : "pending"
    return (
        <>
            <Header name={"Activation Status"}>
                <div className="flex justify-around min-h-24 text-sm items-center">
                    <div className="flex flex-col items-center justify-center">
                        <Status status={createdStatus} />
                        <div className={status === 'Created' ? InProgress : Done}>
                            Created
                        </div>
                    </div>
                    <div className="flex flex-col items-center justify-center">
                        <Status status={inProgressStatus} />
                        <div className={status === 'In Progress' ? InProgress : status === 'Created' ? Rejected : Done }>
                            In Progress
                        </div>
                    </div>
                    <div className="flex flex-col items-center justify-center">
                        <Status status={activeStatus} /> 
                        <div className={status === 'Active' ? Done : Rejected}>
                            Active
                        </div>
                    </div>
                </div>
            </Header>
        </>
    )
}

export default ActivationStatus;