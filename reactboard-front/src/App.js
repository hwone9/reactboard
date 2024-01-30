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

function App() {
  const routing = useRoutes(Router);
  const theme = baselightTheme;
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

