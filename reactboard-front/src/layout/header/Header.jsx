import * as React from 'react';

import { AppBar, Button, Toolbar, Typography } from '@mui/material';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';

import PropTypes from 'prop-types';

const Header = (props)=>{

  return (
    <AppBar position="static">
      <Toolbar>
        <IconButton size="large"
                    edge="start"
                    color="inherit"
                    aria-label="menu"
                    onClick={props.toggleMobileSidebar}
                    sx={{ display: { lg: "none", xs: "inline", }, }}>
          <MenuIcon />
        </IconButton>
        <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
          Header
        </Typography>
        <Button color="inherit">Login</Button>
      </Toolbar>
    </AppBar>
  )
}

Header.propTypes = {
  sx: PropTypes.object,
};

export default Header;