import { memo } from "react";
// memo : 부모가 리렌더링 되었더라도 자삭의 props가 바뀌었을 때만 리렌더링된다

function LoginIdInput({ id, text, type, loginRef, loginId, onChangeLoginId, errors }) {

  return (
    <div>
      <label
        htmlFor={id}
        style={{ display: "inline-block", width: "80px" }}
      >
        {text}
      </label>
      <input
        id={id}
        type={type}
        placeholder={text}
        ref={loginRef}
        value={loginId}
        onChange={onChangeLoginId}
      />
      {errors.loginIdError && (
        <p style={{ color: "red" }}>{errors.loginIdError}</p>
      )}
    </div>
  );
}

export default memo(LoginIdInput);