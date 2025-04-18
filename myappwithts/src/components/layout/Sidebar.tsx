
import { Link, useLocation } from 'react-router-dom'
import { FiHome, FiBarChart2 } from 'react-icons/fi'

const sidebarItems = [
  { path: '/', label: 'Home', icon: <FiHome className="text-lg" /> },
  { path: '/dashBoard', label: 'Dashboard', icon: <FiBarChart2 className="text-lg" /> },
  { path: '/setting', label: 'Setting', icon: <FiBarChart2 className="text-lg" /> },
]

export default function Sidebar() {
  const location = useLocation()

  return (
    <aside className="w-64 h-screen bg-white border-r px-4 py-6 shadow-sm text-sm text-gray-800 flex flex-col">
      <h1 className="text-lg font-bold px-2 mb-8">🏢 NM PLUS</h1>

      <nav className="space-y-1">
        {sidebarItems.map((item) => {
          const isActive = location.pathname === item.path

          return (
            <Link
              key={item.path}
              to={item.path}
              className={`flex items-center gap-3 px-4 py-2 rounded-md transition
                ${isActive ? 'bg-gray-100 text-black font-semibold' : 'text-gray-600 hover:bg-gray-50'}`}
            >
              {item.icon}
              <span>{item.label}</span>
            </Link>
          )
        })}
      </nav>
    </aside>
  )
}



