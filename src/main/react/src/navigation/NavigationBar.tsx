import * as React from "react";
import {Link} from "react-router-dom";
import {createRouterPath} from "../utils/LinkUtils.ts";

const NavigationBar = () => {
    return (
        <div style={{display: "flex", flexDirection: "column", justifyContent: "space-between", padding: "10px"}}>
            <div>
                <Link to={createRouterPath("historie")}>
                    Historie
                </Link>
            </div>
            <div>
                <Link to={createRouterPath("statistik")}>
                    Statistik
                </Link>
            </div>
        </div>
    )
}

export default NavigationBar;