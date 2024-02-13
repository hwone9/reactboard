import React from "react";
import { Navigate } from "react-router-dom";

/* ****Pages***** */
import BoardList from "../views/boards/BoardList";
import BoardDetail from "../views/boards/BoardDetail";
import BoardWrite from "../views/boards/BoardWrite";
import BoardUpdate from "../views/boards/BoardUpdate";
import FullLayout from "../layout/FullLayout";
import BlankLayout from "src/layout/BlankLayout";
import Error from "src/views/auth/Error";
import WorkTemplate from "src/views/works/WorkTemplate";
import Login from "src/views/login/Login";

const Router = [
  {
    path: "/",
    element: <Login />,
    children: [
      { path: "*", exact: true, element: <Navigate to="/auth/404" /> },
    ],
  },
  {
    path: "/board",
    element: <FullLayout />,
    children: [
      { path: "/board", exact: true, element: <BoardList /> },
      { path: "/board/:idx", exact: true, element: <BoardDetail /> },
      { path: "/board/write", exact: true, element: <BoardWrite /> },
      { path: "/board/update/:idx", exact: true, element: <BoardUpdate /> },
      { path: "*", exact: true, element: <Navigate to="/auth/404" /> },
    ],
  },
  {
    path: "/work",
    element: <FullLayout />,
    children: [
      { path: "/work", exact: true, element: <WorkTemplate /> },
      { path: "*", exact: true, element: <Navigate to="/auth/404" /> },
    ],
  },
  {
    path: "/sample",
    element: <FullLayout />,
    children: [
      { path: "/sample", exact: true, element: <WorkTemplate /> },
      { path: "*", exact: true, element: <Navigate to="/auth/404" /> },
    ],
  },

  {
    path: "/auth",
    element: <BlankLayout />,
    children: [
      { path: "404", element: <Error /> },
      //     { path: '/auth/register', element: <Register /> },
      //     { path: '/auth/login', element: <Login /> },
      //     { path: '*', element: <Navigate to="/auth/404" /> },
    ],
  },
];

export default Router;
