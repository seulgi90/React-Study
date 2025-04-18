import { ReactNode } from 'react'
import Sidebar from './Sidebar'
import Header from './Header'

interface LayoutProps {
  children: ReactNode
}

export default function Layout({ children }: LayoutProps) {
  return (
    <div className="flex h-screen">
      {/* 왼쪽: 사이드바 */}
      <aside className="w-64 bg-gray-800 text-white">
        <Sidebar />
      </aside>

      {/* 오른쪽: 상단 제목 + 콘텐츠 */}
      <div className="flex flex-col flex-1">
        <Header />  {/* ← 여기에 페이지 제목 표시 (예: Dashboard) */}
        <main className="flex-1 overflow-auto bg-gray-100 p-6">
          {children} {/* ← 여기에 페이지별 콘텐츠 */}
        </main>
      </div>
    </div>
  )
}
