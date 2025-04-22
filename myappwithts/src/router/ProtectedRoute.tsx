import { Navigate } from 'react-router-dom';
import { useUserStore } from '../store/userStore';
import { JSX } from 'react';

export default function ProtectedRoute({
  children,
  requiredRoles
}: {
  children: JSX.Element;
  requiredRoles?: string[];
}) {
  // todo 토큰으로 변경할 필요 잇을듯?
  const user = useUserStore((state) => state.user);

  if (!user) {
    console.log('user====', user)
    return <Navigate to="/login" replace />;
  }

  console.log('user ---====', user.roles)
  console.log('requiredRoles ---====', requiredRoles)

  // 권한이 없는 경우 → 메인 페이지로
  if (
    requiredRoles &&
    (!Array.isArray(user.roles) || !requiredRoles.some((role) => user.roles.includes(role)))
  ) {
    console.log('Array.isArray(user.roles)---====', Array.isArray(user.roles))
    return <Navigate to="/" replace />;
  }

  return children;
}
