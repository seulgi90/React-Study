import { useRef, useState, useCallback } from "react";
// useCallback : 함수의 참조를 유지

// cutom hook
export default function useInput(initValue) {

    const [value, setValue] = useState(initValue);
    const ref = useRef(null);

    const onChange = useCallback((e) => {
        setValue(e.target.value);
    }, []); 
    // onChange 참조를 유지하여 리렌더링 방지
    // useCallback 사용하지 않을 경우 -> 리액트19 에서는  react compiler 가 최적화를 해줌, 따로 설치 필요

    return  [ value, ref, onChange ] ;
}