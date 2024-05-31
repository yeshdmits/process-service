import React from "react";

export const ListRow = "grid grid-cols-4 gap-4 divide-x divide-zinc-300 p-2 cursor-pointer hover:bg-gray-100 text-center";
export const ListRowSelected = "grid grid-cols-4 gap-4 divide-x divide-zinc-300 py-2 cursor-pointer bg-gray-200 text-center";
export const ListCell = "p-3 flex items-center justify-center text-center"
export const ListRow5 = "grid grid-cols-5 gap-4 divide-x divide-zinc-300 p-2 cursor-pointer hover:bg-gray-100 text-center";

const List = ({ header, items }) => {
    const headerStyle = header.length === 4 ? "grid grid-cols-4 gap-4" : header.length === 1 ? "grid grid-cols-1 gap-4" : "grid grid-cols-5 gap-4"
    return (<>
        {items && items.length > 0 ?
            <div>
                <div className="bg-gray-600 text-white px-2 py-4">
                    <div className={headerStyle}>
                        {header &&
                            header.map((value, index) => {
                                return (
                                    <div key={index} className="flex items-center justify-center hover:cursor-default text-center">
                                        {value}
                                    </div>
                                );
                            })
                        }
                    </div>
                </div>
                <div className="divide-y divide-zinc-300">
                    {items}
                </div>
            </div>
            :
            <div className="flex justify-center my-8 items-center">
                No Data Avaliable
            </div>
        }
    </>)
}

export default List;