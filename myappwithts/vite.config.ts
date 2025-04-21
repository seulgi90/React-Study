// vite.config.ts
import { defineConfig, loadEnv } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig(({ mode }) => {
  const env = loadEnv(mode, process.cwd())

  return {
    plugins: [react()],
    define: {
      'process.env': env, // process.env를 환경 변수로 정의
    },
    server: {
      proxy: {
        '/api': {
          target: `${env.VITE_BACKEND_URL}`,
          changeOrigin: true,
          secure: false
        }
      }
    }
  }
})
