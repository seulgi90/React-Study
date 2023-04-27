import { useState, useEffect } from "react";

function App() {

  const [loading, setLoading] = useState(true);
  const [movies, setMovies] = useState([]);

  // 방법 1
  // useEffect (() => {
  //   fetch('https://yts.mx/api/v2/list_movies.json?minimum_rating=8.8&sort_by=year')
  //   .then((response) => response.json())
  //   .then((json) => {
  //     setMovies(json.data.movies);
  //     setLoading(false);
  //   });
  // },[]);

  // 방법2
  // const getMovies = async () => {
  //   const response = await fetch('https://yts.mx/api/v2/list_movies.json?minimum_rating=8.8&sort_by=year');
  //   const json = await response.json();
  //   setMovies(json.data.getMovies);
  //   setLoading(false);
  // };
  
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
      <div>{movies.map((movie) => (
        <div key={movie.id}>
          <img src={movie.medium_cover_image} />
          <h2>{movie.title}</h2>
          <p>{movie.summary}</p>
          <ul>
            <li>
              {movie.genres.map((g) => (<li key={g}>{g}</li>))}
            </li>
          </ul>
        </div>))}
      </div>
      )}
    </div>
  );
} 

export default App;
