import React, { useEffect, useState } from "react";
import ProcessList from '../list/ProcessList';
import { useLocation, useSearchParams } from 'react-router-dom';
import AdditionalScreen from "./AdditionalScreen";

const ProcessMain = () => {
    const { state } = useLocation();
    const [searchParams] = useSearchParams();

    const [addProduct, setAddProduct] = useState(false)
    const [features, setFeatures] = useState(true)
    const [process, setProcess] = useState(false)
    const [task, setTask] = useState(false)
    const [doc, setDoc] = useState(false)

    const handleClick = (name) => {
        if (name === 'add') {
            setAddProduct(true)
            setFeatures(false)
            setProcess(false)
            setTask(false)
            setDoc(false)
        }
    }

    useEffect(() => {
        if (state != null) {
            setAddProduct(state.addProduct)
            setFeatures(state.features)
            setProcess(state.process)
            setTask(state.task)
            setDoc(state.doc)
        }
        if (searchParams.get("docId")) {
            setAddProduct(false)
            setFeatures(false)
            setProcess(false)
            setTask(false)
            setDoc(true)
        } else if (searchParams.get("taskId")) {
            setAddProduct(false)
            setFeatures(false)
            setProcess(false)
            setTask(true)
            setDoc(false)
        } else if (searchParams.get("processId")) {
            setAddProduct(false)
            setFeatures(false)
            setProcess(true)
            setTask(false)
            setDoc(false)
        }
    }, [state, searchParams])

    return (
        <>
            <div className="grid md:grid-cols-2 sm:grid-cols-1 overflow-hidden justify-around">
                <div className={`md:flex ${features ? 'sm:flex' : 'sm:hidden'} flex-col justify-around w-full`}>
                    <div className="grow">
                        <ProcessList />
                    </div>
                </div>
                <div className="min-h-screen relative overflow-y-auto max-h-screen flex flex-col justify-around w-full">
                    <AdditionalScreen
                        showAddProduct={addProduct}
                        showFeatures={features}
                        showProcess={process}
                        showTask={task}
                        showDoc={doc}
                        handleClick={handleClick} />
                </div>
            </div>
        </>
    )
}

export default ProcessMain;