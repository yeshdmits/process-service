import React from "react";
import Asc from '../../svgs/asc.svg?react';
import Desc from '../../svgs/desc.svg?react';

const Sortable = ({ index, sortBy, handleSortBy, value, order }) => {
    return (
        <>
            <div key={index}
                onClick={() => handleSortBy(value)}
                className={`flex-1${value === sortBy ? "text-sky-300" : ""} hover:cursor-pointer hover:text-lg flex items-center justify-center text-center`}>
                <div>{value}</div>
                {value === sortBy && <div>{order === 'asc' ? <Asc width="15px" height="15px" /> : <Desc width="15px" height="15px" />}</div>}
            </div>
        </>
    )
}

export default Sortable;