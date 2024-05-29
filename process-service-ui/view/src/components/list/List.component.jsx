import React from "react";

export const ListRow = "grid grid-cols-4 gap-4 divide-x divide-zinc-300 py-2 cursor-pointer hover:bg-gray-100 text-center";
export const ListCell = "p-3 flex items-center justify-center text-center"

const List = ({ header, items }) => {
    return (<>
        <div className="bg-zinc-100 px-2 py-4">
            <div className="grid grid-cols-4 gap-4">
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
    </>)
}

export default List;