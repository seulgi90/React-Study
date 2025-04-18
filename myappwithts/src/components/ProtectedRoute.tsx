import { Navigate } from 'react-router-dom'
import { useUserStore } from '../store/userStore'
import { JSX } from 'react'

export default function ProtectedRoute({ children }: { children: JSX.Element }) {
  const user = useUserStore((state) => state.user)

  if (!user) return <Navigate to="/login" replace />
  return children
}
