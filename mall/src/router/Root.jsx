import {createBrowserRouter} from "react-router-dom";
import {lazy, Suspense} from "react";

// 코드 스플리팅 : 필요할 때까지 로딩하지 않기
const Loading = <div className={'bg-red-500'}>Loading...</div>
const Main = lazy(() => import("./pages/MainPage.jsx"))
const About = lazy(() => import("./pages/AboutPage.jsx"))

const root = createBrowserRouter([
    {
        path: '/',
        element: <Suspense fallback={Loading}><Main/></Suspense>
        // element: <MainPage />
    },
    {
        path: '/about',
        element: <Suspense fallback={Loading}><About/></Suspense>
        // element: <MainPage />
    },
])

export default root