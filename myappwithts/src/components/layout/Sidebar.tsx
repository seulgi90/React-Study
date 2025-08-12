import { Link, useLocation } from 'react-router-dom'
import { FiHome, FiBarChart2, FiFileText } from 'react-icons/fi';

const sidebarItems = [
  {
    path: '/',
    label: 'Home',
    icon: <FiHome className="text-lg" />,
  },
  {
    path: '/userList',
    label: '회원관리',
    icon: <FiBarChart2 className="text-lg" />,
    children: [
      { path: '/userList/create', label: '사용자 등록-미구현' },
    ],
  },
  {
    path: '/typeTest',
    label: '타입테스트',
    icon: <FiFileText className="text-lg" />,
  },
]

export default function Sidebar() {
  const location = useLocation()

  return (
    <aside className="w-64 h-screen bg-white border-r px-4 py-6 shadow-sm text-sm text-gray-800 flex flex-col">
      <h1 className="text-lg font-bold px-2 mb-8">🏢 NM PLUS</h1>

      <nav className="space-y-1">
        {sidebarItems.map((item) => {
          const isActive = location.pathname.startsWith(item.path)

          return (
            <div key={item.path}>
              <Link
                to={item.path}
                className={`flex items-center gap-3 px-4 py-2 rounded-md transition
                  font-semibold tracking-tight
                  ${isActive ? 'bg-gray-100 text-black' : 'text-gray-800 hover:bg-gray-100'}`}
              >
                {item.icon}
                <span>{item.label}</span>
              </Link>

              {/* 하위 메뉴 있을 경우 */}
              {item.children && item.children.length > 0 && isActive && (
                <div className="ml-10 mt-1 space-y-1">
                  {item.children.map((sub) => {
                    const isSubActive = location.pathname === sub.path

                    return (
                      <Link
                        key={sub.path}
                        to={sub.path}
                        className={`block px-2 py-1 rounded-md transition text-sm
                          ${isSubActive ? 'bg-gray-100 text-black font-medium' : 'text-gray-700 hover:bg-gray-50'}`}
                      >
                        {sub.label}
                      </Link>
                    )
                  })}
                </div>
              )}
            </div>
          )
        })}
      </nav>
    </aside>
  )
}
