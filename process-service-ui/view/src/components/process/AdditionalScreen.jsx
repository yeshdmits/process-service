import React from "react";
import AddProductList from './AddProductList';
import ProcessOverview from './ProcessOverview';
import TaskOverview from "../task/TaskOverview";
import ViewPdfComponent from "../task/ViewPdfComponent";
import FeatureToggle from "./FeatureToggle";


const AdditionalScreen = ({ showAddProduct, showProcess, showFeatures, showTask, handleClick, showDoc }) => {
    return (
        <>
            <div className="grow bg-neutral-200">
                {showAddProduct &&
                    <AddProductList />}
                {showProcess &&
                    <ProcessOverview />}
                {showTask &&
                    <TaskOverview />}
                {showDoc &&
                    <ViewPdfComponent />}
                {showFeatures &&
                    <FeatureToggle handleClick={handleClick} />}
            </div>
        </>
    )
}

export default AdditionalScreen;