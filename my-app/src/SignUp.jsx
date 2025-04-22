import { useMemo, useState } from "react";
import LoginIdInput from "./components/LoginIdInput";
import Input from "./components/Input";
import useInput from "./hooks/useInput";

// useMemo : 객체의 참조 유지

function SignUp() {
  const [loginId, loginRef, onChangeLoginId] = useInput("");
  const [password, passwordRef, onChangePassword] = useInput("");
  const [name, nameRef, onChangeName] = useInput("");

  const [errors, setErrors] = useState({});

  const onLogin = () => {
    if (!loginId.trim()) {
      setErrors({ loginIdError: "아이디를 입력해주세요." });
      loginRef.current.focus();
      return;
    }

    if (!password.trim()) {
      setErrors({ passwordError: "비밀번호를 입력해주세요." });
      passwordRef.current.focus();
      return;
    }

    if (!name.trim()) {
      setErrors({ nameError: "이름을 입력해주세요." });
      nameRef.current.focus();
      return;
    }

    setErrors({});
  };

  const obj = useMemo(() => ({}), []);

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
        <LoginIdInput
          id="loginId"
          text="아이디"
          type="text"
          loginRef={loginRef}
          loginId={loginId}
          onChangeLoginId={onChangeLoginId}
          errors={errors}
        />
        <Input
          id="password"
          text="비밀번호"
          type="password"
          inputRef={passwordRef}
          value={password}
          onChange={onChangePassword}
          errors={errors}
        />
        <Input
          id="name"
          text="이름"
          type="text"
          inputRef={nameRef}
          value={name}
          onChange={onChangeName}
          errors={errors}
        />
        <button onClick={onLogin}>회원가입</button>
      </div>
    </>
  );
}

export default SignUp;
