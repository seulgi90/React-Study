import { create } from 'zustand'
import { persist } from 'zustand/middleware'

type User = {
  email: string
  name: string
  roleNames: string[]
}

interface UserState {
  user: User | null
  setUser: (user: User) => void
  clearUser: () => void
}

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
