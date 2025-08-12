import React from 'react';
import { Menu } from '../../types/typeTest.ts';

interface Props extends Omit<Menu, 'price'> {
  showBestMenu(name: string): string
  // 이미 선언되어있는 타입이있는데 또 쓴다고? 놉!
  // name: string;
  // price: number;
}

// 인터페이스가 아닌 type에 확장 할 경우
type OwnProps = Menu & {
  showBestMenu(name: string): string
}

// rsc 설정
const TypeTestPage = ({name}:Props) => {
  return (
    <div>
      {name}
    </div>
  );
};

export default TypeTestPage;
