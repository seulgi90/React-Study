import { useQuery} from '@tanstack/react-query';

const TodoList = () => {

  const { data, isPending, isFetching, refetch } = useQuery({
    queryKey: ["todoList"], // 각 쿼리를 식별하기 위해 사용하는 고유 한 값
    queryFn: async () => {
      const response = await fetch(
        "https://jsonplaceholder.typicode.com/todos"
      );
      return await response.json();
    }
  })

  console.log('isPending', isPending)
  console.log('isFetching', isFetching)

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
