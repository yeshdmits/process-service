import React, { useState } from "react";
import Close from "../../svgs/close.svg?react"
import Open from "../../svgs/open-process.svg?react"

const Header = (props) => {
    const [show, setShow] = useState(true);

    return (<>
        <div className="flex bg-sky-600 items-center sticky top-0">
            <div className="hover:bg-sky-500 rounded-full hover:cursor-pointer ">
                {props.elem != null && props.elem}
            </div>
            <div className="p-2 px-5 flex grow justify-between items-center text-2xl rounded-full hover:cursor-pointer hover:bg-sky-500 hover:text-black text-white"
                onClick={() => setShow(!show)} >
                <div>{props.name}</div>
                <div>
                    {show ? <Close stroke="#fff" /> : <Open stroke="#fff" />}
                </div>
            </div>
        </div>
        {show && props.children}
    </>);
}

export default Header;