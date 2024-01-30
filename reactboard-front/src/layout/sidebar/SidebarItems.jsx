import { Box, List, ListItem, ListItemButton, ListItemText } from '@mui/material';
import React from 'react';
import { NavLink, useLocation } from "react-router-dom";
import Menuitems from './MenuItems';
import NavGroup from './Nav/NavGroup';
import NavItem from './Nav/NavItem';

const SidebarItems = () => {
    const {pathname} = useLocation();
    const pathDirect = pathname;

    return (
        <Box sx={{ px: 3 }}>
            <List sx={{ pt: 0 }} className="sidebarNav">
            {Menuitems.map((item) => {
                if (item.subheader) {
                    return <NavGroup item={item} key={item.subheader} />;
                } else {
                    return (
                    <NavItem item={item} key={item.id} pathDirect={pathDirect} />
                    );
                }
                })}
            </List>
        </Box>
    )
}

export default SidebarItems;