import { useState } from 'react';
import { Address, TypeTest } from '../../types/typeTest.ts';
import TypeTestStore from './TypeTestStore.tsx';
import TypeTestPage from './TypeTestPage.tsx';


function TestPage() {

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
// useState<TypeTest> : 제네릭
  const [ typeTest, setTypeTest ] = useState<TypeTest>(data);
  const changeAddress = (address: Address) => {
    setTypeTest({...typeTest, address: address});
  }
  const showBestMenu = (name:string) => {
    return name;
  }

  return (
    <div>
      <TypeTestStore info={typeTest} changeAddress={changeAddress} />
      <br/>
      <TypeTestPage name="TypeTestPage name props" showBestMenu={showBestMenu} />
    </div>
  );
}

export default TestPage;