import React from "react";
import TaskStatus from "../status/TaskStatus";
import { formatDate } from "../../service/Utils";
import Header from "./Header.component";
import List, { ListRow, ListCell } from "./List.component";

export const TaskItems = (taskList, handleViewTask) => {
    return taskList && taskList.map((task, id) =>
        <div key={id} onClick={() => handleViewTask(task)} className={ListRow}>

            <div className={ListCell}>
                {task.taskName}
            </div>
            <div className={ListCell}>
                {formatDate(task.modifiedAt)}
            </div>
            <div className={ListCell}>
                {task.modifiedBy}
            </div>
            <div className={ListCell}>
                <TaskStatus taskStatus={task.taskStatus} />
            </div>
        </div>
    )
}

const TaskList = ({ taskList, handleViewTask }) => {
    return (
        <>
            <Header name={"Task Overview"}>
                <List header={["Name", "Last Action", "Updated By", "Resolution"]} items={TaskItems(taskList, handleViewTask)} />
            </Header>
        </>
    );
}

export default TaskList;