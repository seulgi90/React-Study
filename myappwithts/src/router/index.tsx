import { createBrowserRouter } from "react-router-dom";
import { lazy, Suspense } from "react";

// 코드 스플리팅 : 필요할 때까지 로딩하지 않기
const Loading = <div className={"bg-red-500"}>Loading...</div>;
const Login = lazy(() => import("../pages/Login"));
const Home = lazy(() => import("../pages/Home"));
const DashBoard = lazy(() => import("../pages/DashBoard"));
// const Settings = lazy(() => import('../pages/Settings'))

const router = createBrowserRouter([
  {
    path: "/login",
    element: (
      <Suspense fallback={Loading}>
        <Login />
      </Suspense>
    ),
  },
  {
    path: "/",
    element: (
      <Suspense fallback={Loading}>
        <Home />
      </Suspense>
    ),
  },
  // { 
  //   path: "/",
  //   element: (
  //     <ProtectedRoute> // todo 로그인 안하면 이동 못하도록 추가 
  //       <Layout>
  //         <Suspense fallback={Loading}>
  //           <Home />
  //         </Suspense>
  //       </Layout>
  //     </ProtectedRoute>
  //   ),
  // },
  {
    path: "/dashBoard",
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
]);

export default router;
