import React from 'react'
import { createRoot } from 'react-dom/client'
import App from './App.tsx'
import './index.css'
import {createBrowserRouter, RouterProvider} from "react-router-dom";
import {createRouterPath} from "./utils/LinkUtils.ts";
import DefaultErrorPage from "./pages/DefaultErrorPage.tsx";
import Historie from "./pages/Historie.tsx";
import Statistik from "./pages/Statistik.tsx";

const router = () => createBrowserRouter([
    {
        path: createRouterPath("/"),
        element: <App/>,
        errorElement: <DefaultErrorPage/>,
        children: [
            {
                path: createRouterPath("historie"),
                element: <Historie/>
            },
            {
                path: createRouterPath("statistik"),
                element: <Statistik/>
            }
        ]
    }
]);

createRoot(document.getElementById('root')!).render(
    <React.StrictMode>
        <RouterProvider router={router()}/>
    </React.StrictMode>,
)
