import { useState, useEffect } from "react";
import { Link } from "react-router-dom";

function App() {
  const [toDo, setToDo] = useState("");
  const [toDos,setToDos] = useState([]);

  const onChange = (event) => setToDo(event.target.value);

  const onSubmit = (event) => {
    event.preventDefault();
    if (toDo === "") {
      return;
    }
    setToDos((currentArray => [...currentArray, toDo])); // 입력 순서대로 
    // setToDos((currentArray => [toDo, ...currentArray])); // 최근 입력이 위쪽으로
    // 함수를 호출해서 수정하는 방식
    // currentArray = 현재 state = toDos 를 받아와서
    // toDo 라는 새로운 Array로 currentArray 합쳐서 return

    setToDo(""); // 단순히 값만 보내서 수정하는 방식 
  };
  
  console.log("toDo",toDo);
  console.log("toDos",toDos);

  return (
    <div>
      <h1>My To Dos ({toDos.length})</h1>
      <form onSubmit={onSubmit}>
        <input onChange={onChange} value={toDo} type="text" placeholder="Write your to do...." />
        <button>Add To Do </button>
      </form>
      <hr />
      <ul>
        {toDos.map((toDos, index) => <li key={index}>{toDos}</li>)} 
      </ul>
      <hr />

      <h2>
        <Link to={`/`}>게시판 목록으로</Link>
      </h2>
    </div>

  );
  // {toDos.map()} : map은 하나의 array에 있는 item을 내가 원하는 무엇이든지로 바꿔주는 역할 
  // toDos.map((toDos) toDos 대신 item 등 다양하게 작성 가능
  // key : 고유성 부여, 주로 데이터의 Id를 사용
  // https://ko.legacy.reactjs.org/docs/lists-and-keys.html
}

export default App;
