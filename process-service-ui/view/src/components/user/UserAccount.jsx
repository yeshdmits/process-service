import React, { useEffect, useState } from "react";
import { useApiContext } from "../../context/ApiContext";
import { ListCell } from "../list/List.component";
import Pagination from "../list/Pagination.component";
import { createSearchParams, useNavigate } from "react-router-dom";
import { formatDate, formatAuthDate } from "../../service/Utils";
import TaskStatus from "../status/TaskStatus";
import Sortable from "./Sortable.component";

const UserAccount = () => {
    const [decodedToken, setDecodedToken] = useState();
    const [userTaskList, setUserTaskList] = useState();
    const { getUserInfo, getAssignedTasks } = useApiContext();
    const headers = ["Name", "Updated At", "Created At", "Updated By", "Resolution"];

    const navigate = useNavigate();

    const [page, setPage] = useState(0);
    const [size, setSize] = useState(10);
    const [sortBy, setSortBy] = useState('Created At');
    const [order, setOrder] = useState('desc');
    const [totalPages, setTotalPages] = useState(0);
    const [currentPageTask, setCurrentPageTask] = useState([]);
    const [totalItems, setTotalItems] = useState(0);
    const [filters, setFilters] = useState([]);

    const handleViewTask = (task) => {
        console.log(task);

        navigate({
            pathname: "/",
            search: `?${createSearchParams({ taskId: task.taskId })}`
        },
            {
                state: {
                    addProduct: false,
                    features: false,
                    process: false,
                    task: true,
                    doc: false
                }
            })
    }

    const onPrev = () => {
        if (page === 0) {
            return;
        }
        let currentPage = page - 1;
        setPage(currentPage)
        const offset = currentPage * size
        setCurrentPageTask(userTaskList.slice(offset, offset + size));
        setTotalPages(Math.ceil(userTaskList.length / size));
        setTotalItems(userTaskList.length);
    }

    const onNext = () => {
        if (page + 1 === totalPages) {
            return;
        }
        let currentPage = page + 1;
        setPage(currentPage)
        const offset = currentPage * size
        setCurrentPageTask(userTaskList.slice(offset, offset + size));
        setTotalPages(Math.ceil(userTaskList.length / size));
        setTotalItems(userTaskList.length);
    }

    const handleSizeChange = (event) => {
        let currentSize = event.target.value;
        let currentPage = 0;
        setSize(currentSize);
        setPage(currentPage);
        const offset = currentPage * currentSize
        setCurrentPageTask(userTaskList.slice(offset, offset + currentSize));
        setTotalPages(Math.ceil(userTaskList.length / currentSize));
        setTotalItems(userTaskList.length);
    }

    const mapSort = (field) => {
        switch (field) {
            case "Name":
                return "taskName"
            case "Updated At":
                return "modifiedAt"
            case "Created At":
                return "createdAt"
            case "Updated By":
                return "modifiedBy"
            case "Resolution":
                return "taskStatus"
        }
    }

    const handleSortBy = (field) => {
        let column = mapSort(field);
        if (!userTaskList || userTaskList.length === 0) {
            return;
        }
        let sortable = [...userTaskList];
        sortable.sort((a, b) => {
            if (a[column] < b[column]) {
                return order === "asc" ? -1 : 1;
            }
            if (a[column] > b[column]) {
                return order === "asc" ? 1 : -1;
            }
            return 0;
        });

        if (sortBy === field) {
            if (order === 'asc') {
                setOrder('desc');
            } else {
                setOrder('asc');
            }
        } else {
            setSortBy(field);
            setOrder('desc');
        }
        const offset = page * size
        setUserTaskList(sortable)
        setCurrentPageTask(sortable.slice(offset, offset + size));
    }

    // const handleFilters = (newFilter) => {
    //     let newFilters = [...filters];

    //     if (newFilters.map(item => item.name).includes(newFilter)) {
    //     setFilters(newFilters.filter(item => item.name !== newFilter));
    //     } else {
    //         newFilters.push({name: newFilter});
    //         setFilters(newFilters);
    //     }
    // }

    // const handleDateFilter = (newFilters, field) => {
    //     setFilters(newFilters)
    //     let filterDate = newFilters.filter(i => i.name === field);
    //     const filtered = [...filteredTaskList].filter(i => {
    //         if (!filterDate[0]) {
    //             return true;
    //         }
    //         if (!filterDate[0].criteria === 'GT') {
    //             return new Date(i[field]) >= new Date(filterDate[0].value)
    //         } else {
    //             return new Date(i[field]) <= new Date(filterDate[0].value)
    //         }
    //     }).filter(i => {
    //         if (filterDate[1]) {
    //             return true;
    //         }
    //         if (filterDate[1].criteria === 'GT') {
    //             return new Date(i[field]) >= new Date(filterDate[1].value)
    //         } else {
    //             return new Date(i[field]) <= new Date(filterDate[1].value)
    //         }
    //     })
    //     const offset = page * size
    //     console.log(filtered)
    //     setFilteredTaskList(filtered)
    //     setCurrentPageTask(filtered.slice(offset, offset + size));
    // }


    useEffect(() => {
        const fetchApi = () => {
            getUserInfo()
                .then(response => {
                    setDecodedToken(response);
                    getAssignedTasks()
                        .then(data => {
                            setUserTaskList(data);
                            const offset = page * size
                            setCurrentPageTask(data.slice(offset, offset + size));
                            setTotalPages(Math.ceil(data.length / size));
                            setTotalItems(data.length);
                            handleSortBy(sortBy);
                        })

                });
        }
        fetchApi()
    }, [])

    return (
        <>
            {decodedToken &&
                <div className="flex flex-col items-center justify-center bg-gray-100">
                    <h1 className="text-2xl my-4 font-bold">Your Profile</h1>
                    <div className="min-w-full flex justify-around bg-white rounded-lg shadow-lg max-w-md">
                        <div>
                            <div className="mb-2">
                                <strong>Email:</strong> {decodedToken.email}
                            </div>
                            <div className="mb-2">
                                <strong>Email Verified:</strong> {decodedToken.email_verified ? 'Yes' : 'No'}
                            </div>
                        </div>
                        <div>
                            <div>
                                <div className="mb-2">
                                    <strong>Family Name:</strong> {decodedToken.family_name}
                                </div>
                                <div className="mb-2">
                                    <strong>Name:</strong> {decodedToken.name}
                                </div>
                            </div>
                            <div>
                                <div className="mb-2">
                                    <strong>Given Name:</strong> {decodedToken.given_name}
                                </div>
                                <div className="mb-2">
                                    <strong>Preferred Username:</strong> {decodedToken.preferred_username}
                                </div>
                            </div>
                        </div>
                        <div className="mb-2">
                            <strong>Auth Time:</strong> {formatAuthDate(new Date(decodedToken.auth_time * 1000))}
                        </div>
                    </div>
                    <div className="w-full text-sm">
                        <div className={`grid grid-cols-5 bg-gray-600 text-white h-12 item`}>
                            {
                                headers.map((value, index) => {
                                    return (
                                        <div key={index} className="flex justify-around items-center">
                                            <Sortable
                                                sortBy={sortBy}
                                                index={index}
                                                handleSortBy={handleSortBy}
                                                value={value}
                                                order={order} />
                                            {/* <Filterable
                                                list={userTaskList}
                                                field={mapSort(value)}
                                                filters={filters}
                                                handleFilters={handleFilters}
                                                handleDateFilter={handleDateFilter} /> */}
                                        </div>
                                    );
                                })
                            }
                        </div>
                        <div className={`divide-y divide-zinc-300 min-h-[50vh] max-h-[50vh] overflow-y-auto`}>
                            {currentPageTask && currentPageTask.map((task, id) =>
                                <div key={id} onClick={() => handleViewTask(task)} className="grid grid-cols-5 gap-4 divide-x divide-zinc-300 cursor-pointer hover:bg-gray-100 text-center">

                                    <div className={ListCell}>
                                        {task.taskName}
                                    </div>
                                    <div className={ListCell}>
                                        {formatDate(task.modifiedAt)}
                                    </div>
                                    <div className={ListCell}>
                                        {formatDate(task.createdAt)}
                                    </div>
                                    <div className={ListCell}>
                                        {task.modifiedBy}
                                    </div>
                                    <div className={ListCell}>
                                        <TaskStatus taskStatus={task.taskStatus} />
                                        {task.taskStatus}
                                    </div>
                                </div>)}
                        </div>
                        <Pagination
                            size={size}
                            page={page}
                            onPrev={onPrev}
                            onNext={onNext}
                            totalPages={totalPages}
                            handleSizeChange={handleSizeChange}
                            defaultSizeValues={[5, 10, 50]}
                        />
                    </div>
                </div>
            }
        </>
    )
}

export default UserAccount;