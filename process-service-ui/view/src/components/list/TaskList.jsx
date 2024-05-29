import React from "react";
import TaskStatus from "../status/TaskStatus";
import { formatDate } from "../../service/Utils";
import Header from "./Header.component";
import List, { ListRow, ListCell } from "./List.component";

const TaskList = ({ taskList, handleViewTask }) => {
    const items = taskList && taskList.map((task, id) =>
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


    return (
        <>
            <Header name={"Task Overview"}>
                <List header={["Task Name", "Last Action", "Updated By", "Resolution"]} items={items} />
            </Header>
        </>
    );
}

export default TaskList;