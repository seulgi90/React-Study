import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
// useParams : url에 있는 값을 반환해주는 함수, 특히 변경되는 값

function Detail(){

    const [movie,setMovie] = useState([]);

    // useParams() : Router는 바로 이 변수 값을 넘겨준다
    // const X = useParams();
    // console.log(X); // id값이 나옴
    const { id } = useParams();
    console.log(id);

    // useParams()로 받아온 id를 이용해 API로부터 정보를 fetch `movie_id=${id}` 할 수 있다
    const getMovie = async () => {
        const json = await (
            await fetch(`https://yts.mx/api/v2/movie_details.json?movie_id=${id}`)
        ).json();
        setMovie(json.data.movie);
        console.log(json);
    };

    useEffect(() => {
    // await 함수는 async 함수 내부에 있지않으면 사용할 수 없다 -> 이동 getMovie()
    //    const json = await (
    //     await fetch(`https://yts.mx/api/v2/movie_details.json?movie_id=${id}`)
    //    ).json();
       getMovie();
    }, []);
     
    return (
        <div>
            <h1>Detail</h1>
            <h2>{movie.title}</h2>
            <img src={movie.small_cover_image} />
            <h3>{movie.description_full}</h3>
        </div>
    );
}

export default Detail;  