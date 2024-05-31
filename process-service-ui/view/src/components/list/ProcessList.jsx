import React, { useEffect, useState, } from "react";
import { useNavigate, createSearchParams, useSearchParams } from 'react-router-dom';
import { formatDate } from "../../service/Utils";
import { useApiContext } from "../../context/ApiContext";
import List, { ListRowSelected, ListRow, ListCell } from "./List.component";
import Header from "./Header.component";

const ProcessList = () => {
    const navigate = useNavigate();
    const [searchParams] = useSearchParams();
    const [productList, setProductList] = useState(null);

    const { getProductList } = useApiContext();
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

    const headers = ["Name", "Last Action", "Updated By", "Status"];
    const items = productList &&
        productList.map((product, id) =>
            <div key={id} onClick={() => handleOpenProduct(product)}
                className={searchParams.get("processId") === product.processEntityId ? ListRowSelected : ListRow}>
                <div className={ListCell}>
                    {product.displayName}
                </div>
                <div className={ListCell}>
                    {formatDate(product.modifiedAt)}
                </div>
                <div className={ListCell}>
                    {product.modifiedBy}
                </div>
                <div className={ListCell}>
                    {product.processStatus}
                </div>
            </div>
        )


    useEffect(() => {
        const fetchDataFromApi = () => {
            getProductList().then(data => {
                setProductList(data)
            })
        }
        fetchDataFromApi();
    }, [searchParams]);

    return (
        <>
            <Header name="Product Overview">
                <List header={headers} items={items} />
            </Header>
        </>
    );
}

export default ProcessList;