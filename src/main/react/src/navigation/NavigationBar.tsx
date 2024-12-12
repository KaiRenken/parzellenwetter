import * as React from "react";
import {Box, List, ListItem, ListItemButton} from "@mui/material";
import {createRouterPath} from "../utils/LinkUtils.ts";
import {Link} from "react-router-dom";

const NavigationBar = () => {
    return (
        <Box style={{width: "15em"}}>
            <List>
                <ListItem key={"historie"} disablePadding>
                    <Link to={createRouterPath("historie")} style={{width: "inherit"}}>
                        <ListItemButton style={{color: "rgb(70, 70, 70)"}}>
                            Historie
                        </ListItemButton>
                    </Link>
                </ListItem>
                <ListItem key={"statistik"} disablePadding>
                    <Link to={createRouterPath("statistik")} style={{width: "inherit"}}>
                        <ListItemButton style={{color: "rgb(70, 70, 70)"}}>
                            Statistik
                        </ListItemButton>
                    </Link>
                </ListItem>
            </List>
        </Box>
    )
}

export default NavigationBar;