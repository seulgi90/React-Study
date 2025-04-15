export default function LoginIdInput({ id, text, loginRef, loginId, onChangeLoginId, errors }) {

  return (
    <div>
      <label
        htmlFor={id}
        style={{ display: "inline-block", width: "80px" }}
      >
        아이디
      </label>
      <input
        ref={loginRef}
        id={id}
        type={text}
        value={loginId}
        onChange={onChangeLoginId}
        placeholder="아이디"
      />
      {errors.loginIdError && (
        <p style={{ color: "red" }}>{errors.loginIdError}</p>
      )}
    </div>
  );
}
