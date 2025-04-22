import { useLocation, useNavigate } from 'react-router-dom'
import { useUserStore } from '../../store/userStore'
import { useState, useRef, useEffect } from 'react'

export default function Header() {
  const location = useLocation()
  const navigate = useNavigate()
  const user = useUserStore((state) => state.user)
  const clearUser = useUserStore((state) => state.clearUser)

  const pageTitleMap: Record<string, string> = {
    '/': 'HOME',
    '/userList': '회원관리',
  }

  const title = pageTitleMap[location.pathname] || ''
  const [dropdownOpen, setDropdownOpen] = useState(false)
  const dropdownRef = useRef<HTMLDivElement>(null)

    // 바깥 클릭 시 드롭다운 닫기
  useEffect(() => {
    const handleClickOutside = (event: MouseEvent) => {
      if (dropdownRef.current && !dropdownRef.current.contains(event.target as Node)) {
        setDropdownOpen(false)
      }
    }
    document.addEventListener('mousedown', handleClickOutside)
    return () => document.removeEventListener('mousedown', handleClickOutside)
  }, [])

    // 로그아웃
  const handleLogout = () => {
    sessionStorage.clear()
    clearUser()
    navigate('/login')
  }

  return (
    <header className="h-20 bg-white border-b border-gray-200 flex items-center justify-between px-6 lg:px-10 shadow-sm">
      {/* 페이지 제목 */}
      <h1 className="text-xl lg:text-2xl font-semibold text-gray-700 tracking-tight">
        📌 {title}
      </h1>

      {/* 사용자 메뉴 */}
      {user && (
        <div className="relative" ref={dropdownRef}>
          <div
            className="flex items-center space-x-2 cursor-pointer select-none"
            onClick={() => setDropdownOpen(!dropdownOpen)}
          >
            <img
              src={`https://api.dicebear.com/7.x/thumbs/svg?seed=${user.name}`}  // 임시 프로필 사진진
              alt="프로필"
              className="w-8 h-8 rounded-full border"
            />
            <span className="text-sm font-medium text-gray-800">{user.name}</span>
          </div>

          {dropdownOpen && (
            <div className="absolute right-0 mt-2 w-44 bg-white border rounded-xl shadow-lg z-50 overflow-hidden">
              <ul className="text-sm text-gray-700 divide-y divide-gray-100">
                <li className="px-4 py-2 hover:bg-gray-50 cursor-pointer">정보 수정</li>
                <li
                  className="px-4 py-2 hover:bg-gray-50 cursor-pointer text-gray-500 hover:text-black"
                  onClick={handleLogout}
                >
                  로그아웃
                </li>
              </ul>
            </div>
          )}
        </div>
      )}
    </header>
  )
}

