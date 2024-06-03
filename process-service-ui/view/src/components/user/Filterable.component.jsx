import React, { useEffect, useState } from "react";
import Filter from "../../svgs/filter.svg?react"
import Checkbox from "./Checkbox.component";
import DatePicker from "./DatePicker.component";

const Filterable = ({ filters, list, field, handleFilters, handleDateFilter }) => {
    const [isHover, setIsHover] = useState(false);
    const [isOpen, setIsOpen] = useState(false);
    const [values, setValues] = useState();

    const handleMouseEnter = () => {
        setIsHover(true);
    };

    const handleMouseLeave = () => {
        setIsHover(false);
    };
    useEffect(() => {
        if (list) {
            setValues([...new Set(list.map(i => {
                let it = i[field];
                return it
            }))]);
        }
    }, [list])

    return (
        <>
            <div className="relative inline-block text-left">

                <div className="hover:cursor-pointer" onClick={() => setIsOpen(!isOpen)} onMouseEnter={handleMouseEnter} onMouseLeave={handleMouseLeave}>
                    {isHover ? <Filter width="22px" height="22px" /> : <Filter width="20px" height="20px" />}
                </div>
                {isOpen && (
                    <div className="max-h-64 overflow-y-auto absolute right-0 mt-2 w-48 bg-white rounded-md shadow-lg z-10">
                        <div className="py-1">
                            {
                                field === 'createdAt' || field === 'modifiedAt' ?
                                    <div>
                                        <DatePicker 
                                            field={field}
                                            filters={filters}
                                            handleDateFilter={handleDateFilter}
                                            setIsOpen={setIsOpen}
                                        />
                                    </div> :
                                    values && values.map((item, index) => (
                                        <div key={index} className="hover:cursor-pointer block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">
                                            {

                                                <div>
                                                    <Checkbox
                                                        checked={filters.filter(filter => filter.name === item).length > 0}
                                                        onChange={() => handleFilters(item)}
                                                        label={item} />
                                                </div>
                                            }
                                        </div>
                                    ))}
                        </div>
                    </div>
                )}
            </div>
        </>
    )
}

export default Filterable;