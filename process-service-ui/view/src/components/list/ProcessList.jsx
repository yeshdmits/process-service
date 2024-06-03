import React, { useEffect, useState, } from "react";
import { useNavigate, createSearchParams, useSearchParams } from 'react-router-dom';
import { formatDate } from "../../service/Utils";
import { useApiContext } from "../../context/ApiContext";
import List, { ListRowSelected, ListRow, ListCell } from "./List.component";
import Header from "./Header.component";
import Asc from '../../svgs/asc.svg?react';
import Desc from '../../svgs/desc.svg?react';
import Pagination from "./Pagination.component";

const ProcessList = () => {
    const navigate = useNavigate();
    const [searchParams] = useSearchParams();
    const [productList, setProductList] = useState([]);
    const [page, setPage] = useState(0);

    const [size, setSize] = useState(10);
    const [sortBy, setSortBy] = useState('createdAt');
    const [order, setOrder] = useState('desc');
    const [totalPages, setTotalPages] = useState(0);
    const [totalItems, setTotalItems] = useState(0);

    const { getProductList, getFilteredList } = useApiContext();
    const handleOpenProduct = (product) => {
        navigate({
            pathName: "/",
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
                setTotalPages(response.totalPages);
                setTotalItems(response.totalItems);
                setProductList(response.data)
            })
    }

    useEffect(() => {
        callFiltered()
    }, [searchParams, page, size, order, sortBy]);

    return (
        <>
            <Header name="Product Overview">
                <List header={headers} items={items} customHeader={true} customStyle={true} />
                <Pagination
                    size={size}
                    page={page}
                    onPrev={onPrev}
                    onNext={onNext}
                    totalPages={totalPages}
                    handleSizeChange={handleSizeChange}
                    defaultSizeValues={[5, 10, 50]}
                />
            </Header>
        </>
    );
}

export default ProcessList;