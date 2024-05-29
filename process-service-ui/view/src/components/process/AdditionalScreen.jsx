import React from "react";
import AddProductList from './AddProductList';
import ProcessOverview from './ProcessOverview';
import TaskOverview from "../task/TaskOverview";
import ViewPdfComponent from "../task/ViewPdfComponent";

const featureButton = "text-center bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded hover:cursor-pointer";
const featureText = "text-center p-4 cursor-default";
const fatureCard = "max-w-48 m-4 border min-h-32 bg-gray-200 mt-20";


const AdditionalScreen = ({ showAddProduct, showProcess, showFeatures, showTask, handleClick, showDoc }) => {
    return (
        <>
            <div className="grow mx-2">
                {showAddProduct &&
                    <AddProductList />}

                {showProcess &&
                    <ProcessOverview />}
                {showTask &&
                    <TaskOverview />}
                {showDoc &&
                    <ViewPdfComponent />}
                {showFeatures &&
                    <div>
                        <div className={fatureCard}>
                            <div className={featureButton} onClick={() => handleClick("add")}>
                                <div>Add Product</div>
                            </div>
                            <div className={featureText}>This feature enables the addition of a new product from a predefined list.</div>
                        </div>
                        <div className={fatureCard}>
                            <div className={featureButton} onClick={() => handleClick("add")}>
                                <div>Build Product</div>
                            </div>
                            <div className={featureText}>This feature enables the creation of a new product from scratch.</div>
                        </div>
                    </div>}
            </div>
        </>
    )
}

export default AdditionalScreen;