import { create } from 'zustand'
import { persist } from 'zustand/middleware'

type User = {
  roles: any
  email: string
  name: string
  roleNames: string[]
}

interface UserState {
  user: User | null
  setUser: (user: User) => void
  clearUser: () => void
}
// persist: Zustand 미들웨어, 상태를 브라우저 저장소(localStorage, sessionStorage 등)에 자동 저장/복원, 첫 번째 인자: 상태와 액션 정의 함수 , 두 번째 인자: 옵션 객체
export const useUserStore = create<UserState>()(
    persist(
      (set) => ({
        user: null,
        setUser: (user) => set({ user }),
        clearUser: () => set({ user: null }),
      }),
      {
        name: 'userInfo', // sessionStorage에 저장될 key
        storage: {
          getItem: (key) => {
            const value = sessionStorage.getItem(key);
            return value ? JSON.parse(value) : null;
          },
          setItem: (key, value) => {
            sessionStorage.setItem(key, JSON.stringify(value));
          },
          removeItem: (key) => sessionStorage.removeItem(key),
        },
      }
    )
  )
