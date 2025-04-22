import { memo } from "react";

function Input({
  id,
  text,
  type,
  inputRef,
  value,
  onChange,
  errors
}) {
  return (
    <div>
      <label htmlFor={id} style={{ display: "inline-block", width: "80px" }}>
        {text}
      </label>
      <input
        id={id}
        type={type}
        ref={inputRef}
        value={value}
        onChange={onChange}
        placeholder={text}
      />
      {errors && <p style={{ color: "red" }}>{errors[`${id}Error`]}</p>}
    </div>
  );
}

export default memo(Input);
