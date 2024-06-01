import React, { useEffect, useState, } from "react";
import { useNavigate, createSearchParams, useSearchParams } from 'react-router-dom';
import { formatDate } from "../../service/Utils";
import { useApiContext } from "../../context/ApiContext";
import List, { ListRowSelected, ListRow, ListCell } from "./List.component";
import Header from "./Header.component";
import Prev from '../../svgs/prev.svg?react';
import Next from '../../svgs/next.svg?react';
import Asc from '../../svgs/asc.svg?react';
import Desc from '../../svgs/desc.svg?react';

const NextPrev = "hover:cursor-pointer flex items-center hover:bg-gray-300 rounded-full";

const ProcessList = () => {
    const navigate = useNavigate();
    const [searchParams] = useSearchParams();
    const [productList, setProductList] = useState([]);
    const [page, setPage] = useState(0);

    const defaultSizeValues = [1, 5, 10]
    const [size, setSize] = useState(5);
    const [sortBy, setSortBy] = useState('createdAt');
    const [order, setOrder] = useState('desc');
    const [totalPages, setTotalPages] = useState(0);
    const [totalItems, setTotalItems] = useState(0);

    const { getProductList, getFilteredList } = useApiContext();
    const handleOpenProduct = (product) => {
        navigate({
            pathName: "/process/overview",
            search: `?${createSearchParams({ processId: product.processEntityId })}`
        },
            {
                state: {
                    addProduct: false,
                    features: false,
                    process: true
                }
            });
    }

    const handleSortBy = (field) => {
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
    }

    const headers = (
        <>
            <div className="flex items-center justify-center hover:cursor-default text-center">
                {"Name"}
            </div>
            <div onClick={() => handleSortBy("createdAt")}
                className="hover:cursor-pointer hover:text-lg flex items-center justify-around text-center">
                <div>{"Created"}</div>
                <div>{order === 'asc' ? <Asc stroke="#fff" width="15px" height="15px" /> : <Desc width="15px" height="15px" />}</div>
            </div>
            <div className="flex items-center justify-center hover:cursor-default text-center">
                {"Updated By"}
            </div>
            <div className="flex items-center justify-center hover:cursor-default text-center">
                {"Status"}
            </div>
        </>
    )
    const items = productList &&
        productList.map((product, id) =>
            <div key={id} onClick={() => handleOpenProduct(product)}
                className={searchParams.get("processId") === product.processEntityId ? ListRowSelected : ListRow}>
                <div className={ListCell}>
                    {product.displayName}
                </div>
                <div className={ListCell}>
                    {formatDate(product.createdAt)}
                </div>
                <div className={ListCell}>
                    {product.modifiedBy}
                </div>
                <div className={ListCell}>
                    {product.processStatus}
                </div>
            </div>
        )

    const onPrev = () => {
        if (page === 0) {
            return;
        }
        setPage(page - 1)
    }

    const onNext = () => {
        if (page + 1 === totalPages) {
            return;
        }
        setPage(page + 1)
    }

    const handleSizeChange = (event) => {
        setSize(event.target.value);
        setPage(0)
    }

    const callFiltered = () => {
        getFilteredList(page, size, sortBy, order, [])
            .then(response => {
                // setPage(response.page);
                // setSize(response.size);
                // setSortBy(response.sortBy);
                // setOrder(response.order);
                setTotalPages(response.totalPages);
                setTotalItems(response.totalItems);
                setProductList(response.data)
            })
    }

    useEffect(() => {
        const fetchDataFromApi = () => {
            getProductList().then(data => {
                setProductList(data)
            })
        }
        callFiltered()

        // fetchDataFromApi();

    }, [searchParams, page, size, order, sortBy]);

    return (
        <>
            <Header name="Product Overview">
                <List header={headers} items={items} customHeader={true} customStyle={true} />
                <div className="flex justify-around items-center mb-3">
                    <div className="grow flex justify-around items-center">
                        <div className={"cursor-pointer bg-gray-200 hover:bg-gray-300 text-gray-700 font-semibold py-2 px-4 rounded"} onClick={onPrev}>
                            <Prev width="20px" height="20px" />
                        </div>
                        <div className="text-gray-700 font-medium">Page {page + 1} of {totalPages}</div>
                        <div className={"cursor-pointer bg-gray-200 hover:bg-gray-300 text-gray-700 font-semibold py-2 px-4 rounded"} onClick={onNext}>
                            <Next width="20px" height="20px" />
                        </div>
                    </div>
                    <div className="mr-4">
                        <select className="hover:bg-gray-200 hover:cursor-pointer bg-white border border-gray-300 text-gray-700 p-3 rounded leading-tight focus:outline-none focus:border-gray-500 focus:bg-gray-200 focus:cursor-pointer"
                            onChange={handleSizeChange} defaultValue={size}>
                            {defaultSizeValues.map((i, k) => {
                                return (
                                    <option value={i} key={k} className="text-gray-700" >{i}</option>
                                )
                            })}
                        </select>
                        <div className="pointer-events-none absolute inset-y-0 right-0 flex items-center px-2 text-gray-700">
                            <svg className="fill-current h-4 w-4" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20">
                                <path d="M7 10l5 5 5-5H7z" />
                            </svg>
                        </div>
                    </div>
                </div>
            </Header>
        </>
    );
}

export default ProcessList;