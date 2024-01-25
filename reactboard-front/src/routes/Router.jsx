import React, { lazy } from 'react';
// import { Navigate } from 'react-router-dom';
// import Loadable from '../layouts/full/shared/loadable/Loadable';
import Home from '../views/Home';
import BoardList from '../views/boards/BoardList';
import BoardDetail from '../views/boards/BoardDetail';
import BoardWrite from '../views/boards/BoardWrite';
import BoardUpdate from '../views/boards/BoardUpdate';

/* ****Pages***** */
// const Home = Loadable(lazy(() => import('../views/Home')))

const Router = [
  {
    path: '/',
    element: <Home />,
    children: [
      { path: '/board', element: <BoardList /> },
      { path: '/board/:idx', element: <BoardDetail /> },
      { path: '/write', element: <BoardWrite /> },
      { path: '/update/:idx', element: <BoardUpdate /> },
    ],
  },
  // {
  //   path: '/auth',
  //   element: <BlankLayout />,
  //   children: [
  //     { path: '404', element: <Error /> },
  //     { path: '/auth/register', element: <Register /> },
  //     { path: '/auth/login', element: <Login /> },
  //     { path: '*', element: <Navigate to="/auth/404" /> },
  //   ],
  // },
];

export default Router;
