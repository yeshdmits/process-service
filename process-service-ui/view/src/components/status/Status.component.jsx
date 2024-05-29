import React from "react";
import StatusDone from '../../svgs/status-done.svg?react'
import StatusInProgress from '../../svgs/status-inprogress.svg?react'
import StatusRejected from '../../svgs/status-rejected.svg?react'
import StatusPending from '../../svgs/status-pending.svg?react'


export const Done = "mt-3 text-green-500";
export const Rejected = "mt-3 text-slate-500"
export const InProgress = "mt-3 text-blue-400"

{`mt-3 ${status === 'Created' ? 'text-blue-400' : 'text-green-500'}`}
const Status = ({status}) => {
    if (status === 'done') {
        return <StatusDone />
    }
    if (status === 'reject') {
        return <StatusRejected />
    }
    if (status === 'progress') {
        return <StatusInProgress />
    }
    return <StatusPending />
}

export default Status;