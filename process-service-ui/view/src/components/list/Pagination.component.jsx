import React from "react";
import Prev from '../../svgs/prev.svg?react';
import Next from '../../svgs/next.svg?react';

const Pagination = ({ size, page, totalPages, onPrev, onNext, handleSizeChange, defaultSizeValues }) => {
    return (
        <>
            <div className="flex justify-around items-center mb-3">
                <div className="grow flex justify-around items-center">
                    <div className={"cursor-pointer bg-gray-200 hover:bg-gray-300 text-gray-700 font-semibold py-2 px-4 rounded"}
                        onClick={onPrev}>
                        <Prev width="20px" height="20px" />
                    </div>
                    <div className="text-gray-700 font-medium">Page {page + 1} of {totalPages}</div>
                    <div className={"cursor-pointer bg-gray-200 hover:bg-gray-300 text-gray-700 font-semibold py-2 px-4 rounded"}
                        onClick={onNext}>
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
        </>
    );
}

export default Pagination;