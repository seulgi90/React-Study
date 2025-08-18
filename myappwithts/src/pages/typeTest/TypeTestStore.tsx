import { Address, TypeTest } from '../../types/typeTest.ts';


interface Props {
  info: TypeTest,
  // changeAddress(address:Address): () => void; // 함수를 리턴, 핸들러를 "리턴". 이벤트에 그대로 넘겨 나중에 실행 - 버튼 이벤트 등에 사용 할때
  changeAddress(address:Address): void // 즉시 실행- 호출 시점에 곧바로 상태 변경, 반환값 없음
}

function TypeTestStore({info, changeAddress}:Props) {
  return (
    <div>
      <div>
        <h3>{info.title}</h3>
        <p>{info.content}</p>
        <p>{info.address.city}</p>
      </div>
    </div>

  );
}

export default TypeTestStore;