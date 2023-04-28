import { Link } from "react-router-dom";

function Home() {
    return (
        <div>
            <h2>
                <Link to={`/moviehome`}>영화 게시판</Link>
            </h2>
            <h2>
                <Link to={`/todolist`}>ToDo 게시판</Link>
            </h2>
            <h2>
                <Link to={`/cointracker`}>코인 전환 게시판</Link>
            </h2>
        </div>
    );
};
export default Home;