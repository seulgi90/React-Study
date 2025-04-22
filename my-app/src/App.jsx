import { useState, useRef } from "react";
import LoginIdInput from "./components/LoginIdInput";
import Input from "./components/Input";
import useInput from "./hooks/useInput";

function App() {
  const [loginId, loginRef, onChangeLoginId] = useInput("");
  const [password, passwordRef, onChangePassword] = useInput("");
  const [errors, setErrors] = useState({});

  const onLogin = () => {
    if (!loginId.trim()) {
      setErrors("아이디를 입력해주세요.");
      loginRef.current.focus();
      return;
    }

    if (!password.trim()) {
      setErrors("비밀번호를 입력해주세요.");
      passwordRef.current.focus();
      return;
    }
    setErrors({});
  };

  return (
    <>
      <div
        style={{
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          justifyContent: "center",
          height: "100vh",
        }}
        className="login-container"
      >
        <LoginIdInput id="loginId" text="ID" type="text" loginRef={loginRef} loginId={loginId} onChangeLoginId={onChangeLoginId} errors={errors} />
        <Input
          id="password"
          text="Password"
          type="password"
          inputRef={passwordRef}
          value={password}
          onChange={onChangePassword}
          errors={errors}
        />
        <button onClick={onLogin}>로그인</button>
        </div>
    </>
  );
}

export default App;
