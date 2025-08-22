import { useQuery} from '@tanstack/react-query';
import { useState } from 'react';


const TodoList = () => {

  const [id, setId] = useState(0)

  const { data, isPending, isFetching} = useQuery({
    queryKey: ["todoList"], // 각 쿼리를 식별하기 위해 사용하는 고유 한 값
    queryFn: async () => {
      const response = await fetch("https://jsonplaceholder.typicode.com/todos");
      return await response.json();
    },
  })

 // data: dataDetail처럼 별칭으로 구조분해
  const { data:dataDetail } = useQuery({
    queryKey: ["todoList", id],  // 각 쿼리를 식별하기 위해 사용하는 고유 한 값
    queryFn: async () => {
      const response = await fetch(`https://jsonplaceholder.typicode.com/todos/${id}`);
      return await response.json();
    },
    enabled: !!id // id의 초기값이 0면 false로 호출 막음
  })


  return (
    <>
      <p>isPending: {isPending ? 'isPending....' : '완료'}</p>
      <p>isFetching :{isFetching ? 'isFetching....' : '완료'}</p>

      <ul>
        {data?.map((item) => (
          <li
            key={item.id}
            onClick={() => {
              setId(item.id);
            }}
          >
            {`${item.id} > ${item.title}`}
          </li>
        ))}
      </ul>
    </>
  );
};

export default TodoList;
