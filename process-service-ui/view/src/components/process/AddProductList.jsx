import React, { useEffect, useState, } from "react";
import { useApiContext } from "../../context/ApiContext";
import { createSearchParams, useNavigate } from 'react-router-dom';
import GoBack from '../../svgs/go-back.svg?react'
import Header from "../list/Header.component";

const AddProductList = () => {
    const { getAddProduct, createProduct } = useApiContext();
    const [productList, setProductList] = useState(null);
    const navigate = useNavigate();


    const handleBack = () => {
        navigate("/", {
            state: {
                addProduct: false,
                features: true,
                process: false,
                task: false,
                doc: false
            }
        });
    }

    const handleAddProduct = (id) => {
        createProduct(id)
            .then(data => {
                console.log(data)
                navigate({
                    pathName: "/",
                    search: `?${createSearchParams({ processId: data })}`
                },
                    {
                        state: {
                            addProduct: false,
                            features: false,
                            process: true,
                            task: false,
                            doc: false
                        }
                    });
            })
    }


    useEffect(() => {
        const fetchDataFromApi = () => {
            getAddProduct().then(data => {
                setProductList(data)
            })
        }
        fetchDataFromApi();
    }, []);
    return (
        <>
            <Header name={"Available Products"} elem={<GoBack onClick={handleBack} />}>
                <div className="m-8 grid grid-cols-2 gap-4">
                    {productList &&
                        productList.map((product, index) => {
                            return (
                                <div onClick={() => handleAddProduct(product.processDefinitionId)}
                                    key={index}
                                    className="bg-white border text-center rounded-lg min-h-48 hover:cursor-pointer hover:bg-gray-600 hover:text-white">
                                    <div className="px-4 py-2">
                                        <div className="text-xl font-semibold">
                                            {product.displayName}
                                        </div>
                                    </div>
                                </div>
                            )
                        })

                    }
                </div>
            </Header>
        </>
    )
}

export default AddProductList;