import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
// BrowserRouter : 보통의 웹사이트 URL
// HashRouter : localhost:3000/#/movie 처럼 무엇인가 붙는다
import Home from "./routes/Home";
import MovieHome from "./routes/MovieHome";
import Detail from "./routes/Detail";
import ToDoList from "./routes/ToDoList";
import CoinTracker from "./routes/CoinTracker";

// router는 url을 보고있는 component고 router는 우리에게 Home component를 보여주게 될거야
function App() {

  // Switch :  Route(=URL)를 찾는다 ->  찾으면 컴포넌트를 렌더링
  // Swith -> Routes 업데이트되면서 Route 컴포넌트 사이에 자식컴포넌트<Home />을 넣지 않고
  // element={<Home />} lement prop에 자식 컴포넌트를 할당하도록 변경됨 : 버전 6
  // Route path="/movie/:id"  ':' 를 기입해줘야 변수로 받을수있다 없으면 그냥 text id가 되버림
  // => 변수 추가후 Movie는 prop으로 id를 받고 있지 않기때문에 부모 컴포넌트(Home)에서 prop 추가 후 자식 컴포넌트(Movie)에 prop 추가
  return (
    <Router>
      <Routes> 
        <Route path="/" element={<Home />} />
        <Route path="/moviehome" element={<MovieHome />} />
        <Route path="/movie/:id" element={<Detail />} />
        <Route path="/todolist" element={<ToDoList />} />
        <Route path="/cointracker" element={<CoinTracker />} />
      </Routes>
    </Router>
  )
} 

export default App;
