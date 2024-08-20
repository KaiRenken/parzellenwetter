import * as React from "react";
import {createRouterPath} from "../utils/LinkUtils";
import {NavLink} from "react-router-dom";

const NavigationBar = () => {
    return (
        <nav>
            <ul>
                <li>
                    <NavLink to={createRouterPath("topics")}>
                        <span>Topics</span>
                    </NavLink>
                </li>
                <li>
                    <NavLink to={createRouterPath("systeminformation")}>
                        <span>Info</span>
                    </NavLink>
                </li>
            </ul>
        </nav>
    )
}

export default NavigationBar;