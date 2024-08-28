import * as React from "react";
import {Box, Drawer, List, ListItem, ListItemButton} from "@mui/material";
import {createRouterPath} from "../utils/LinkUtils.ts";
import {Link} from "react-router-dom";

const NavigationBar = () => {
    return (
        <Drawer
            variant={"permanent"} ModalProps={{keepMounted: false}}>
            <Box sx={{width: 250}} role="presentation">
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
        </Drawer>
    )
}

export default NavigationBar;