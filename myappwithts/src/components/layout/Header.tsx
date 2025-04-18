import { useLocation } from 'react-router-dom'

export default function Header() {
  const location = useLocation()

  const pageTitleMap: Record<string, string> = {
    '/': 'HOME',
    '/dashBoard': '대시보드',
  }

  const title = pageTitleMap[location.pathname] || ''

  return (
    <header className="h-20 bg-white border-b border-gray-200 flex items-center px-6 lg:px-10 shadow-sm">
      <h1 className="text-2xl font-semibold text-gray-800">
        📌 {title}
      </h1>
    </header>
  )
}
