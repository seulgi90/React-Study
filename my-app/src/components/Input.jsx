export default function Input({
  id,
  text,
  nameRef,
  name,
  onChangeName,
  errors
}) {
  return (
    <div>
      <label htmlFor={id}>이름</label>
      <input
        ref={nameRef}
        type={text}
        value={name}
        onChange={onChangeName}
        placeholder="이름"
      />
      {errors && <p style={{ color: "red" }}>{errors.nameError}</p>}
    </div>
  );
}
