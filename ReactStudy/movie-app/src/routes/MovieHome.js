import { useState, useEffect } from "react";
import Movie from "../components/Movie";
import { Link } from "react-router-dom";
import Pagination from "../components/Pagination";

function MovieHome(){

  const [loading, setLoading] = useState(true);
  const [movies, setMovies] = useState([]);

  const [keyword, setKeyword] = useState("");
  const onChange = (event) => setKeyword(event.target.value);
  
  const searchKeyword = movies.filter((movie) => {
    return movie.title.toLocaleLowerCase().includes(keyword.toLocaleLowerCase())
  });

  const [limit, setLimit] = useState(10);
  const [page, setPage] = useState(1);
  const offset = (page - 1) * limit;
  
  // 방법3
  const getMovies = async () => {
    const json = await (await fetch('https://yts.mx/api/v2/list_movies.json?minimum_rating=8.8&sort_by=year')).json();
    setMovies(json.data.movies);
    setLoading(false);
  };
  useEffect(() => {
    getMovies();
  },[]);

  return (
    <div>
      {loading ? <h1>loading...</h1> : (
      <div>
        <h2>
          <Link to={`/`}>게시판 목록으로</Link>
        </h2>

        <form onSubmit={searchKeyword}>
          <input onChange={onChange} value={keyword} type="text" placeholder="search..."></input>
          <button>제목 검색</button>
        </form>

        {searchKeyword.slice(offset, offset + limit).map((movie) => (
          <Movie 
            key={movie.id}
            id={movie.id}
            // 자바스크립트에서는 medium_cover_image가 아닌mediumCoverImage로 쓰지만
            // 내가 만든 컴포넌트라 자유롭게 작명 가능 -> coverImg
            // 그러나 { movie.medium_cover_image } 에서는 API에서 가져오므로 API 정보와 똑같이 써야함
            coverImg={movie.medium_cover_image}
            title={movie.title}
            summary={movie.summary.length > 400 ?  `${movie.summary.slice(0,400)}...` : movie.summary}
            genres={movie.genres}  
          />
        ))}

        <label>
          페이지 당 표시할 게시물 수:
          <select
            type="number"
            value={limit}
            onChange={({ target: { value } }) => setLimit(Number(value))}
          >
            <option value="10">10</option>
            <option value="12">12</option>
            <option value="20">20</option>
            <option value="50">50</option>
            <option value="100">100</option>
          </select>
        </label>

        <Pagination
          total={movies.length}
          limit={limit}
          page={page}
          setPage={setPage}
        />

      </div>
      )}
    </div>
  );
}
export default MovieHome;