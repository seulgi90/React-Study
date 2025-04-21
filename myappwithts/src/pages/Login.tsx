import { useState } from "react";
import { useNavigate } from "react-router-dom";

import { useUserStore } from "../store/userStore";
import useAxios from "../utils/useAxios";

export default function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const setUser = useUserStore((state) => state.setUser);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError("");

    try {
      const response = await useAxios.post("login", { email, password });

      const { userInfo, accessToken, refreshToken } = response.data;

      // 토큰 sessionStorage에 저장
      sessionStorage.setItem("accessToken", accessToken);
      sessionStorage.setItem("refreshToken", refreshToken);

      // user 정보는 store 저장
      setUser(userInfo);

      navigate("/");
      console.log('login 성공===', userInfo )
    } catch (err) {
      console.error("로그인 에러:", err);
      setError("로그인 실패");
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-white px-4">
      <div className="w-full max-w-sm border border-gray-200 rounded-xl p-6 shadow-md">
        <h2 className="text-xl font-semibold text-gray-800 mb-6 text-center">
          로그인
        </h2>

        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label className="block text-sm text-gray-600 mb-1">이메일</label>
            <input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              className="w-full px-4 py-2 border border-gray-300 rounded-md text-sm focus:outline-none focus:ring focus:ring-gray-300"
              placeholder="you@example.com"
            />
          </div>

          <div>
            <label className="block text-sm text-gray-600 mb-1">비밀번호</label>
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              className="w-full px-4 py-2 border border-gray-300 rounded-md text-sm focus:outline-none focus:ring focus:ring-gray-300"
              placeholder="••••••••"
            />
          </div>

          {error && <p className="text-sm text-red-500">{error}</p>}

          <button
            type="submit"
            className="w-full bg-black text-white py-2 rounded-lg text-sm font-medium hover:opacity-90 transition"
          >
            로그인
          </button>
        </form>
      </div>
    </div>
  );
}
