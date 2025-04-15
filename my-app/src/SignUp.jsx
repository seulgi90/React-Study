import { useState, useRef } from "react";
import LoginIdInput from "./components/LoginIdInput";
import Input from "./components/Input";

function SignUp() {
  const loginRef = useRef(null);
  const passwordRef = useRef(null);
  const nameRef =  useRef(null);

  const [loginId, setLoginId] = useState("");
  const [password, setPassword] = useState("");
  const [name, setName] = useState("");
  const [phone, setPhone] = useState("");
  const [errors, setErrors] = useState({});

  const onChangeLoginId = (e) => {
    setLoginId(e.target.value);
  };

  const onChangePassword = (e) => {
    setPassword(e.target.value);
  };

  const onChangeName = (e) => {
    setName(e.target.value);
  };

  const onChangePhone = (e) => {
    setPhone(e.target.value);
  };

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

    if(!name.trim()) {
      setErrors({ nameError: "이름을 입력해주세요."});
      nameRef.current.focus();
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
        <LoginIdInput id="loginId" text="아이디" loginRef={loginRef} loginId={loginId} onChangeLoginId={onChangeLoginId} errors={errors} />
        <div>
          <label
            htmlFor="password"
            style={{ display: "inline-block", width: "80px" }}
          >
            비밀번호
          </label>
          <input
            id="password"
            type="password"
            value={password}
            onChange={onChangePassword}
            placeholder="비밀번호"
          />
          {errors.passwordError && (
            <p style={{ color: "red" }}>{errors.passwordError}</p>
          )}
        </div>
          <Input id="name" text="이름" nameRef={nameRef} name={name} onChangeName={onChangeName} errors={errors} />
        <button onClick={onLogin}>회원가입</button>
      </div>
    </>
  );
}

export default SignUp;
