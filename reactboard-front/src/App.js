// import {Route, Routes} from "react-router-dom";
// import Home from './views/Home';
// import BoardList from './views/boards/BoardList';
// import BoardDetail from './views/boards/BoardDetail';
// import BoardWrite from './views/boards/BoardWrite';
// import BoardUpdate from './views/boards/BoardUpdate';

import { Box, CssBaseline, ThemeProvider } from '@mui/material';
import { useRoutes } from 'react-router-dom';
import Router from './routes/Router';

import { baselightTheme } from "./theme/DefaultColors";
// import LoginTemplate from "src/views/login/LoginTemplate";

function App() {
  const routing = useRoutes(Router);
  const theme = baselightTheme;
  // const authenticated = sessionStorage.getItem("userid") != null;

  return (
    <ThemeProvider theme={theme}>
      <Box>
        <CssBaseline />
          {routing}
      </Box>
    </ThemeProvider>
  );
}

export default App;

