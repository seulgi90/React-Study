import axios from 'axios'
import { useUserStore } from '../store/userStore'


const useAxios = axios.create({
  baseURL: '/api',
})

// 요청 accessToken 헤더 자동 포함
useAxios.interceptors.request.use((config) => {
  const token = sessionStorage.getItem('accessToken')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

// accessToken 만료 시 → refresh 요청
useAxios.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config

    // 401이고 재시도한 적 없는 요청만
    if (
      error.response?.status === 401 &&
      !originalRequest._retry
    ) {
      originalRequest._retry = true // 401- > refresh 요청 -> 실패 -> 요청 같은 무한 루프 방지용

      try {
        const accessToken = sessionStorage.getItem('accessToken')
        const refreshToken = sessionStorage.getItem('refreshToken')

        if (!refreshToken || !accessToken) {
          throw new Error('No refresh token or access token available')
        }

        // refresh 요청
        const res = await axios.post(
          '/api/login/refresh',
          refreshToken,
          {
            headers: {
              'Content-Type': 'application/json',
              Authorization: `Bearer ${accessToken}`,
            },
          }
        )

        const newAccessToken = res.data.accessToken
        sessionStorage.setItem('accessToken', newAccessToken)

        // 실패했던 요청에 새 토큰 적용 후 재시도
        originalRequest.headers.Authorization = `Bearer ${newAccessToken}`
        return useAxios(originalRequest)
        
      } catch (err) {
        // refresh도 실패 → 로그아웃 처리
        sessionStorage.clear()
        const clearUser = useUserStore.getState().clearUser
        clearUser()

        window.location.href = '/login'
        return Promise.reject(err)
      }
    }

    return Promise.reject(error)
  }
)

export default useAxios
