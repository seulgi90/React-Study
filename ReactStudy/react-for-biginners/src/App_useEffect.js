import { useState, useEffect } from "react";
 
function App() {

  const [counter,setValue] = useState(0); // 1번 
  const onClick = () => setValue((prev) => prev + 1); // 2번 -> 버튼-> 제목
  
  const [keyword,setKeyword] = useState(""); 
  const onChange = (event) => setKeyword(event.target.value); // 작성후 input value={}를 줘서 state와 연결
  // onChange() 이 작동할때는  argument로 event로 받아서 
  // event.target.value : 이벤트를 발생시킨 input에서 value를 받아서
  // value를 setKeyword를 넣어준다 -> keyword를 input의 value로 사용

  console.log("i run all the time");
  // const iRunOnlyOnce = () => {
  //   console.log("i run only once");
  // };
  // useEffect(effect: EffectCallback, deps?: DependencyList
  // EffectCallback :  실행시키고 싶은 코드, DependencyList : 존재하면 해당 리스트의 값이 변화할때만 실행
  // DependencyList: [] 로 작성시, react.js가 지켜볼 대상이 없기 때문에 코드가 한번만 실행됨을 의미
  // useEffect(iRunOnlyOnce, []);
  // stste가 변해도 코드가 단 한번만 실행되게하는 function
  // useEffect(() => {
  //   console.log("call the api");
  // }, []);

  // useEffect(() => {
  //   if (keyword !== "" && keyword.length > 5) {

  //     console.log("search for", keyword);
  //   }
  // }, [keyword]); // keyword가 변화할 떄 코드를 실행하고 싶다면 [keyword]

  useEffect(() => {
      console.log("i run only once");
    }, []);

  useEffect(() => {
    console.log("i run when 'keyword' changes.");
  }, [keyword]);

  useEffect(() => {
    console.log("i run when 'counter' changes.");
  }, [counter]);

  useEffect(() => {
    console.log("i run when 'keyword &counter' changes.");
  }, [keyword, counter]);

  return (
    <div>
      <input 
        value={keyword} 
        // state와 연결
        // 
        onChange={onChange}  // event listener 연결
        type="text" 
        placeholder="Search here...." 
      />
      <h1>{counter}</h1>
      <button onClick={onClick}>Click me</button> 
    </div>
  );
}

export default App;
