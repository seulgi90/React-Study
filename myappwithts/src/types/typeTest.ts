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
  zipcode: number;
}

export type Menu = {
  name: string;
  price: number;
}