import { useState } from 'react';
import { TypeTest } from '../types/typeTest.ts';


function TypeTest() {

  let data: TypeTest = {
    name: '타입스크립트',
    title: '타입스크립트 연습',
    content: '타입스크립트 연습 중입니다',
    address: {
      city: '서울',
      zipcode: 9999,
    },
    menu: [
      {  name: '복숭아', price: 10000},
      {  name: '사과', price: 3000},
      {  name: '멜론', price: 20000},
    ]
  }

  const { typeTest, setTypeTest } = useState<TypeTest>(data);

  return (
    <div>
    <p>{typeTest}</p>
    </div>
  );
}

export default TypeTest;