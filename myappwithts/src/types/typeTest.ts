export type TypeTest = {
  name: string;
  title: string;
  content: string;
  address: Address;
  // address: {
  //   city: string;
  //   zipcode: number;
  // },
  menu: Menu[]
  // menu: {
  //   name: string;
  //   price: number;
  // }[]
}

export type Address = {
  city: string;
  zipcode?: number; // Omit 대신 ? 넣어서 optional property로 설정
}

// Address에서 zipcode를 안보여주고싶다고 이렇게 만든다? 놉!
// export type AddressWithoutZipCode = {
//   city: string;
// }

// 일부 제거할 경우 'Omit' 사용!
export type AddressWithoutZipCode = Omit<Address, 'zipcode'>

// 일부만 가져올 경우 'Pick' 사용
export type TypeTestOnlyTitle = Pick<TypeTest, 'title'>

export type Menu = {
  name: string;
  price: number;
}

// api 응답 값도 타입으로 설정해서 받을수 있음
// 단건 응답
export type ItemResponse<T> = {
  data: T;
}

// 목록 응답 (페이징 포함)
export type ListResponse<T> = {
  data: T[];
  // page: number;       // 현재 페이지 번호
  // totalPage: number;  // 전체 페이지 수
  // size: number;       // 페이지 크기
  // totalItems?: number; // 필요 시 전체 건수
} & PageMeta

export type PageMeta = {
  page: number;       // 현재 페이지 번호
  totalPage: number;  // 전체 페이지 수
  size: number;       // 페이지 크기
  totalItems?: number; // 필요 시 전체 건수
};

export type TypeTestResponse = ListResponse<TypeTest>
export type AddressResponse = ItemResponse<Address>

// 인터페이스와 타입 차이
interface Person {
  name: string;
}
interface Person {
  age: number;
}
// 병합됨 -> type은 이렇게 두 번 선언하면 오류
const p: Person = { name: 'Kim', age: 20 };
// 타입은 유니온 타입 가능 -> 인터페이스는 유니온 타입 안 됨
type Status = 'success' | 'error'; // 가능