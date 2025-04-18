import {createBrowserRouter} from "react-router-dom";
import {lazy, Suspense} from "react";

// 코드 스플리팅 : 필요할 때까지 로딩하지 않기
const Loading = <div className={'bg-red-500'}>Loading...</div>
const Home = lazy(() => import("../pages/Home"))
const DashBoard = lazy(() => import('../pages/DashBoard'))
// const Settings = lazy(() => import('../pages/Settings'))

const router = createBrowserRouter([
  {
    path: '/',
    element: (
        <Suspense fallback={Loading}>
          <Home />
        </Suspense>
    ),
  },
  {
    path: '/dashBoard',
    element: (
        <Suspense fallback={Loading}>
          <DashBoard />
        </Suspense>
    ),
  },
//   {
//     path: '/settings',
//     element: (
//       <Suspense fallback={Loading}>
//         <Layout><Settings /></Layout>
//       </Suspense>
//     ),
//   },
])

export default router;
