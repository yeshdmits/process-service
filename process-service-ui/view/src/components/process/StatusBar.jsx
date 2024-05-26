import React from "react";
import StatusDone from '../../svgs/status-done.svg?react'
import StatusInProgress from '../../svgs/status-inprogress.svg?react'
import StatusPending from '../../svgs/status-pending.svg?react'

const StatusBar = (props) => {
    return (
        <div className="flex justify-around min-h-24 items-center">
            <div className="flex flex-col items-center justify-center">
                {props.status === 'Created' ? <StatusInProgress /> : <StatusDone />}
                <div className={`mt-3 ${props.status === 'Created' ? 'text-blue-400' : 'text-green-500'}`}>
                    Created
                </div>
            </div>
            <div className="flex flex-col items-center justify-center">
                {props.status === 'In Progress' ? <StatusInProgress /> : props.status === 'Created' ? <StatusPending /> : <StatusDone />}
                <div className={`mt-3 ${props.status === 'In Progress' ? 'text-blue-400' : props.status === 'Created' ? 'text-slate-500' : 'text-green-500'}`}>
                    In Progress
                </div>
            </div>
            <div className="flex flex-col items-center justify-center">
                {props.status === 'Active' ? <StatusDone /> : <StatusPending />}
                <div className={`mt-3 ${props.status === 'Active' ? 'text-green-500' : 'text-slate-500'}`}>
                    Active
                </div>
            </div>
        </div>
    );
}

export default StatusBar;