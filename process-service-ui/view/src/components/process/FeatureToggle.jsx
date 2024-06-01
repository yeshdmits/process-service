import React from "react";

const featureButton = "text-center bg-sky-500 hover:bg-sky-700 text-white font-bold py-2 px-4 rounded hover:cursor-pointer";
const featureText = "text-center p-4 cursor-default";
const fatureCard = "max-w-48 bg-gray-600 text-white rounded border min-h-32 bg-gray-200";

const FeatureToggle = ({handleClick}) => {

    return (
        <>
            <div className="grid grid-cols-2 mt-8 mx-4">
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
            </div>
        </>
    )
}

export default FeatureToggle;