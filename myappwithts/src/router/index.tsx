import { createBrowserRouter, Navigate } from "react-router-dom";
import { lazy, Suspense } from "react";
import ProtectedRoute from "./ProtectedRoute";

// 코드 스플리팅 : 필요할 때까지 로딩하지 않기
const Loading = <div className={"bg-red-500"}>Loading...</div>;
const Login = lazy(() => import("../pages/Login"));
const Home = lazy(() => import("../pages/Home"));
const UserList = lazy(() => import("../pages/UserList"));
// const Settings = lazy(() => import('../pages/Settings'))

const router = createBrowserRouter([
    // 잘못된 경로로 접근 시 무조건 login으로 -> todo 다른 방향 생각해 볼 것 
    {
      path: "*",
      element: <Navigate to="/login" replace />,
    },
  {
    path: "/login",
    element: (
      <Suspense fallback={Loading}>
        <Login />
      </Suspense>
    ),
  },
  {
    path: '/',
    element: (
      <ProtectedRoute>
        <Suspense fallback={Loading}>
          <Home />
        </Suspense>
      </ProtectedRoute>
    )
  },
  {
    path: '/userList',
    element: (
      <ProtectedRoute requiredRoles={['ROLE_ADMIN']}>
        <Suspense fallback={Loading}>
          <UserList />
        </Suspense>
      </ProtectedRoute>
    )
  }
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
