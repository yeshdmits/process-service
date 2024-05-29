import React, { useEffect, useState, } from "react";
import { useApiContext } from "../../context/ApiContext";
import { createSearchParams, useNavigate } from 'react-router-dom';
import Close from "../../svgs/go-back.svg?react"

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
                    search: `?${createSearchParams({processId: data})}`
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
            <div className="w-full">
                <Close onClick={handleBack} className="hover:cursor-pointer rounded-full hover:bg-slate-200" />
            </div>
            <div className="mt-8 grid grid-cols-2 gap-4 ">
                {productList &&
                    productList.map((product, index) => {
                        return (
                            <div onClick={() => handleAddProduct(product.processDefinitionId)} key={index} className="border rounded-lg min-h-48 hover:cursor-pointer hover:bg-gray-100">
                                <div className="px-4 py-2">
                                    <div className="text-gray-800 text-xl font-semibold">
                                        {product.displayName}
                                    </div>
                                </div>
                            </div>
                        )
                    })

                }
                <div className="border rounded-lg min-h-48">
                    <div className="px-4 py-2">
                        <div className="text-gray-800 text-xl font-semibold">
                            Example Product
                        </div>
                    </div>
                </div>
                <div className="border rounded-lg min-h-48">
                    <div className="px-4 py-2">
                        <div className="text-gray-800 text-xl font-semibold">
                            Something Product
                        </div>
                    </div>
                </div>
            </div>
        </>
    )
}

export default AddProductList;