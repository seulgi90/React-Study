import { createBrowserRouter, Navigate } from "react-router-dom";
import { lazy, Suspense } from "react";
import ProtectedRoute from "./ProtectedRoute";
import Layout from '../components/layout/Layout.tsx';

// 코드 스플리팅 : 필요할 때까지 로딩하지 않기
const Loading = <div className={"bg-red-500"}>Loading...</div>;
const Login = lazy(() => import("../pages/Login"));
const Home = lazy(() => import("../pages/Home"));
const UserList = lazy(() => import("../pages/UserList"));
const TypeTest = lazy(() => import("../pages/typeTest/TestPage.tsx"));
const ReactQueryTest = lazy(() => import("../pages/reactQueryTest/TodoListPage.tsx"));

// const Settings = lazy(() => import('../pages/Settings'))

const router = createBrowserRouter([
    // todo 잘못된 경로로 접근 또는 권한 없는 페이지 
  {
    path: "*",
    element: <Navigate to="/" replace />,
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
      // todo 권한 하드코딩은 좋은 방향은 아님, 임시기때문에 이렇게 설정했으나 수정 필요
      <ProtectedRoute requiredRoles={['ROLE_ADMIN']}>
        <Suspense fallback={Loading}>
          <UserList />
        </Suspense>
      </ProtectedRoute>
    )
  },
    {
      path: '/typeTest',
      element: (
        <Suspense fallback={Loading}>
          <Layout><TypeTest /></Layout>
        </Suspense>
      ),
    },
  {
    path: '/reactQueryTest',
    element: (
      <Suspense fallback={Loading}>
        <Layout><ReactQueryTest /></Layout>
      </Suspense>
    ),
  },
]);

export default router;
