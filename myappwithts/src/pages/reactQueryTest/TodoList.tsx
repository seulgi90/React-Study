import { useQuery} from '@tanstack/react-query';
import { useState } from 'react';

const TodoList = () => {
  const [isEnabledQuery, setIsEnabledQuery] = useState(false);

  const { data, isPending, isFetching, refetch, isError, error } = useQuery({
    queryKey: ["todoList"], // 각 쿼리를 식별하기 위해 사용하는 고유 한 값
    queryFn: async () => {
      const response = await fetch(
        "https://jsonplaceholder.typicode.com/todos"
        // "https://jsonplaceholder.typicodeccc.com/todos" // 에러 발생용 주소
      );
      return await response.json();
    },
    retry: false, // true : 실패 시 무한으로 재시도
    // enabled: isEnabledQuery, // 쿼리 자동 실행 여부 , 기본값 true
    // refetchInterval: 3000, // 실시간 데이터가 필요한 경우 사용
    // refetchOnWindowFocus: false, // 브라우저 창이 다시 포커스를 얻었을 때 쿼리를 자동으로 다시 실행할지 여부를 제어, 기본값 true
    staleTime: 3000
  })

  if (isError) {
    return "에러 발생 : " + error.message;
  }

  return (
    <>
      <p>isPending: {isPending ? "isPending...." : "완료"}</p>
      <p>isFetching :{isFetching ? "isFetching...." : "완료"}</p>

      <button
        onClick={() => setIsEnabledQuery(true)}
        style={{ backgroundColor: "#007BFF", color: "white"}}
      >조회하기</button>
      <hr style={{ margin: "15px 0" }} />
      <button
        onClick={() => {refetch()}}
        style={{ backgroundColor: "#007BFF", color: "white"}}
      >refetch</button>
      <ul>
          {data?.map(item => (
            <li key={item.id}>{item.title}</li>
          ))}
      </ul>
    </>
  );
};

export default TodoList;
