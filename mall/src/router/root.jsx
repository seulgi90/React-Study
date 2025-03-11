import {createBrowserRouter} from "react-router-dom";
import {lazy, Suspense} from "react";
import todoRouter from "./todoRouter.jsx";


// 코드 스플리팅 : 필요할 때까지 로딩하지 않기
const Loading = <div className={'bg-red-500'}>Loading...</div>
const Main = lazy(() => import("../pages/MainPage.jsx"))
const About = lazy(() => import("../pages/AboutPage.jsx"))
const TodoIndex =lazy(() => import("../pages/todo/IndexPage.jsx"))


const root = createBrowserRouter([
    {
        path: '/',
        element: <Suspense fallback={Loading}><Main/></Suspense>
        // element: <MainPage />
    },
    {
        path: '/about',
        element: <Suspense fallback={Loading}><About/></Suspense>
    },
    {
        path: '/todo',
        element: <Suspense fallback={Loading}><TodoIndex/></Suspense>,
        children: todoRouter()

    },
])

export default root