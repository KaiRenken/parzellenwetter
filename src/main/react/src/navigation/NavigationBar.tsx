import * as React from "react";
import {Box, List, ListItem, ListItemButton} from "@mui/material";
import {createRouterPath} from "../utils/LinkUtils.ts";
import {Link} from "react-router-dom";

const NavigationBar = () => {
    return (
        <Box style={{width: "15em"}}>
            <List>
                <ListItem key={"historie"} disablePadding>
                    <ListItemButton>
                        <Link to={createRouterPath("historie")}>
                            Historie
                        </Link>
                    </ListItemButton>
                </ListItem>
                <ListItem key={"statistik"} disablePadding>
                    <ListItemButton>
                        <Link to={createRouterPath("statistik")}>
                            Statistik
                        </Link>
                    </ListItemButton>
                </ListItem>
            </List>
        </Box>
    )
}

export default NavigationBar;