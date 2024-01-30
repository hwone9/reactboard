import React, { useState } from "react";
import SidebarItems from "./SidebarItems";
import { Box, Drawer, useMediaQuery } from "@mui/material";
import LogoDevIcon from '@mui/icons-material/LogoDev';

const SideBar = (props) => {
    const lgUp = useMediaQuery((theme) => theme.breakpoints.up("lg"));
    const sidebarWidth = '270px';
    
    if(lgUp){
        return (
            <Box sx={{width:sidebarWidth, flexShrink: 0,}}>
                {/* desktop */}
                <Drawer anchor="left"
                        open={props.isSidebarOpen}
                        variant="permanent"
                        PaperProps={{ sx: { width: sidebarWidth, boxSizing: 'border-box', }, }}>
                    <Box sx={{height: '100%',}}>
                        {/* Logo */}
                        <Box px={3}>
                            <LogoDevIcon />
                        </Box>
                        <Box>
                            <SidebarItems />
                        </Box>
                    </Box>
                </Drawer>
            </Box>
        )
    }
    {/* mobile */}
    return (
        <Drawer anchor="left"
                open={props.isMobileSidebarOpen}
                onClose={props.onSidebarClose}
                variant="temporary"
                 >
            {/* Logo */}
            <Box px={2}>
            <LogoDevIcon />
            </Box>
            <SidebarItems />
        </Drawer>
    );

}

export default SideBar;