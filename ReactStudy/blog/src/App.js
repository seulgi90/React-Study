/* eslint-disable */

import React, { useState } from 'react';
import logo from './logo.svg';
import './App.css';

function App() {
  
  // useState('맛집 추천'); 
  // [a,b] 형태로 저장 
  // a= 맛집 추천 데이터가 들어가 있고, b = 맛집 추천  state 정정해주는 함수
 
  //let [글제목, 글제목변경] = useState('맛집 추천!'); 

  let [글제목, 글제목변경] = useState(['참치 추천!', '신발 추천!', '옷 추천!']); 
  // [state 데이터, state 데이터 변경 함수]
  // state : 변수 대신 쓰는 데이터 공간
  // useState()를 이용해 만들어야함
  // useState( ['','']): array, object, 문자, 숫자 등 다 저장가능
  // state 는 변경되면 HTML이 자동으로 재렌더링된다

  let posts  = '참치 맛집';
  // 그냥 변수는 변경되어도 자동 재렌더링이 안된다 -> 새로고침해야한다  

  let [좋아요, 좋아요변경] = useState(0);
  // [state 데이터, state 데이터 변경 함수]

  let [버튼, 버튼변경] = useState('맛집 추천');

  function 제목바꾸기() {
  
    // 글제목변경( ['맛있는집 추천!', '신발 추천!', '옷 추천!'] );

    // var newArray = 글제목에 있던 0번째 데이터를 맛있는집 추천으로 바꿈
    // 글제목변경( newArray );
    // 원본 state 수정X, 특히 Array, Object 자료형
    
    // 복사본을 하나 생성한다
    // var newArray = 글제목; // 잘못된 복사, 그냥 복사하면 큰일나요! -> 값 공유일뿐
    var newArray = [...글제목]; // deep copy해서 수정한다, array =[...], object ={} -> 서로 독립적인 복사
    newArray[0] = '맛있는집 추천!'; // 복사본을 만들어서 수정한다
    글제목변경( newArray );
  }

  function 정렬하기() {
    
    var newArray = [...글제목]; // deep copy해서 수정한다, array =[...], object ={} -> 서로 독립적인 복사
    newArray = newArray.sort();
    버튼변경( newArray );
  }
  
  return (
    <div className="App">
      <div className='black-nav'>
        <div>개발 blog</div>
      </div>

      {/* <button onClick={ ()=>{ 글제목변경( ['맛있는집 추천!', '신발 추천!', '옷 추천!'] ) }}>버튼입니다</button> */}
      <button onClick={ 제목바꾸기 }>제목바꾸기 버튼</button>
      {/* 제목바꾸기() 로 작성하게되면 버튼 누름과 상관없이 함수 바로 실행되기때문에 '()'를 빼고 작성 */}

      <button onClick={ 정렬하기 }>글자순 정렬 버튼</button>

      <div className='list'>
        <h3> { 글제목[0] } <span onClick={ ()=>{ 좋아요변경( 좋아요 + 1) } }>좋아요♥</span> { 좋아요 } </h3> 
        <p>2월 17일 발행</p>
        <hr/>
      </div>
      <div className='list'>
        <h3> { 글제목[1] }</h3>
        <p>2월 18일 발행</p>
        <hr/>
      </div>
      <div className='list'>
        <h3> { 글제목[2] }</h3>
        <p>2월 19일 발행</p>
        <hr/>
      </div>

      {/* <div classNAme="modal">
        <h2>제목</h2>
        <p>날짜</p>
        <p>모달 창 만들는 방법 변ㄱ녕 -> component로 만들어서 이동</p>
      </div> */}

      {/* component 만든 후 원하는 곳에서 사용 = <함수명 />  또는 <Modal></Modal>  */}
      <Modal></Modal>

      <Box name="홍길동" age="10"/>
      <Box name="임꺽정" age="11"/>
      <Box name="유관순" age="12"/>
      <Like />
    </div>

    //  return (
    //   <div></div>
    //   <div></div>
    //   <div></div>
    // ) return 안에 HTML을 짤순 있으나 <div></div>를 평행으로 쓸수 없다 
    // fragment 문법 : <> </> ; 의미없는 <div>쓰기 싫을때나, HTML전체를 묶을때 사용
    
  );
}

// component 문법: HTML을 한 단어로 줄여서 쓸수 있는 방법; 반복적으로 쓰는 HTML을 쓸때, 자주 변경되는 HTML UI들 ; 많이 만들면 state 쓸때 복잡해지는 단점
function Modal() { // 원하는 단어로 작성
  return ( // 원하는 HTML 담고 -> 86번째 줄 
    <div className="modal">
      <h2>제목</h2>
      <p>날짜</p>
      <p>상세내용</p>
    </div>
  )
}

function Box(props) { // props
  return ( // 원하는 HTML 담고 -> 86번째 줄  
    <div className="box">
      <h2>{props.name}</h2>
      <p>{props.age}</p>
    </div>
  )
}

function Like() {

  let [count, setCount] = useState(0); 

  const increase = () => {
    setCount(count + 1)
    console.log(count) // 화면에 3, console.log = 2 => 비동기적 (한박자씩 느리다)
  }
  

  return ( 
    <div>
     <p>{ count }</p>
     <button onClick={ increase }>Like</button>
    </div>
  )
}


export default App;
