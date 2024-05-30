import React, { useState } from "react";
import Close from "../../svgs/close.svg?react"
import Open from "../../svgs/open-process.svg?react"

const Header = (props) => {
    const [show, setShow] = useState(true);

    return (<>
        <div className="flex items-center sticky top-0">
            <div className="bg-slate-100 hover:bg-slate-200 hover:cursor-pointer ">{props.elem != null && props.elem}</div>
            <div className="p-2 flex grow justify-between items-center text-2xl bg-slate-100 hover:cursor-pointer hover:bg-slate-200"
                onClick={() => setShow(!show)} >
                <div>{props.name}</div>
                <div>
                    {show ? <Close /> : <Open />}
                </div>
            </div>
        </div>
        {show && props.children}
    </>);
}

export default Header;