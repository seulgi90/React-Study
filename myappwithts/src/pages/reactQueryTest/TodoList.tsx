import { useQuery} from '@tanstack/react-query';

const TodoList = () => {

  const { data, isPending, isFetching, refetch, isError, error } = useQuery({
    queryKey: ["todoList"], // 각 쿼리를 식별하기 위해 사용하는 고유 한 값
    queryFn: async () => {
      const response = await fetch(
        // "https://jsonplaceholder.typicode.com/todos"
        "https://jsonplaceholder.typicodeccc.com/todos" // 에러 발생용 주소
      );
      return await response.json();
    },
    retry: false // true : 실패 시 무한으로 재시도
  })

  if (isError) {
    return "에러 발생 : " + error.message;
  }

  return (
    <>
      <p>isPending: {isPending ? "isPending...." : "완료"}</p>
      <p>isFetching :{isFetching ? "isFetching...." : "완료"}</p>
      <button onClick={() => {
        refetch();
      }}>refetch</button>
      <ul>
          {data?.map(item => (
            <li key={item.id}>{item.title}</li>
          ))}
      </ul>
    </>
  );
};

export default TodoList;
