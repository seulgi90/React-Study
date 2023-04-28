import PropTypes from "prop-types";
import { Link } from "react-router-dom";
// Link: 브라우저 새로고침 없이도 유저를 다른 페이지로 이동시켜주는 컴포넌트

// Movie Component가 { medium_cover_image, title, summary, genres } 이 정보들을 parent component로 부터 받아온다
// prop명은 자유롭게 작성할수 있다 medium_cover_image -> coverImg
// prop는 object 형태이다 =>  prop {id:1234, title:...., summary:....}
// Movie({ coverImg, title, summary, genres, id })는 prop에 들어있는 item을 펼쳐쓰는 형태임을 기억!
function Movie({ coverImg, title, summary, genres, id }) {
    return (
        <div>
            {/* <img src={medium_cover_image} /> */}
            {/* 이미지 element들을 alt속성을 가짐  */}
            <img src={coverImg} alt={title}/>
            <h2>
                {/* <Link to="/movie">{title}</Link> */}
                {/* backtick (``)으로 문자열을 감싸고 ${} 안에 변수나 표현식을 넣어 사용 != (''): 문자열로 인식하게됨 주의*/}
                <Link to={`/movie/${id}`}>{title}</Link>
            </h2>
            <p>{summary}</p>
            <ul>
                <li>
                    {genres.map((g) => (<li key={g}>{g}</li>))}
                </li>
            </ul>
    </div>);
}

Movie.propTypes ={
    coverImg: PropTypes.string.isRequired,
    title: PropTypes.string.isRequired,
    summary: PropTypes.string.isRequired,
    genres:PropTypes.arrayOf(PropTypes.string).isRequired,
    id: PropTypes.number.isRequired,
};

export default Movie;