import { useState, useEffect } from "react";

// function Hello() {
//   useEffect(() => {
//     console.log("component created :)");
//     return () => console.log("component destroyed :(");
//     // Cleanup function
//     // component 가 사라지거나 없어질 때
//   });
//   return <h1>Hello</h1>;
// }

function Hello() {

  function byFn() { 
    console.log("bye :(");
  }
  function hiFn() { // component가 생성될 때 hiFn 실행되고
    console.log("component created :)");
    return byFn; // component가 파괴될 때는 hiFn이 return한 function 실행
  }
  useEffect(hiFn, []);
  return <h1>Hello</h1>;
}

function App() {
  const [showing, setShowing] = useState(false);
  const onClick = () => setShowing((prev) => !prev);
  return (
    <div>
      {showing ? <Hello /> : null}
      <button onClick={onClick}>{showing ? "Hide" : "show"}</button>
    </div>
  );
}

export default App;
