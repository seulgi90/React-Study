import { useState, useEffect } from "react";
import Movie from "../components/Movie";
import { Link } from "react-router-dom";

function MovieHome(){

  const [loading, setLoading] = useState(true);
  const [movies, setMovies] = useState([]);
  
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
        {movies.map((movie) => (
          <Movie 
            key={movie.id}
            id={movie.id}
            // 자바스크립트에서는 medium_cover_image가 아닌mediumCoverImage로 쓰지만
            // 내가 만든 컴포넌트라 자유롭게 작명 가능 -> coverImg
            // 그러나 { movie.medium_cover_image } 에서는 API에서 가져오므로 API 정보와 똑같이 써야함
            coverImg={movie.medium_cover_image}
            title={movie.title}
            summary={movie.summary}
            genres={movie.genres}
          />
        ))}
      </div>
      )}
    </div>
  );
}
export default MovieHome;