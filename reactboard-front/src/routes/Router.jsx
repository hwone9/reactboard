import React from 'react';
import { Navigate } from 'react-router-dom';

/* ****Pages***** */
import BoardList from '../views/boards/BoardList';
import BoardDetail from '../views/boards/BoardDetail';
import BoardWrite from '../views/boards/BoardWrite';
import BoardUpdate from '../views/boards/BoardUpdate';
import FullLayout from '../layout/FullLayout';
import BlankLayout from 'src/layout/BlankLayout';
import Error from 'src/views/auth/Error';

const Router = [
  {
    path: '/',
    element: <FullLayout />,
    children: [
      { path: '/', element: <Navigate to="/board" /> },
      { path: '/board', exact: true, element: <BoardList /> },
      { path: '/board/:idx', exact: true, element: <BoardDetail /> },
      { path: '/write', exact: true, element: <BoardWrite /> },
      { path: '/update/:idx', exact: true, element: <BoardUpdate /> },
      { path: '*', exact: true, element: <Navigate to="/auth/404" /> },
    ],
  },
  {
    path: '/auth',
    element: <BlankLayout />,
    children: [
      { path: '404', element: <Error /> },
  //     { path: '/auth/register', element: <Register /> },
  //     { path: '/auth/login', element: <Login /> },
  //     { path: '*', element: <Navigate to="/auth/404" /> },
    ],
  },
];

export default Router;
