import TodoList from './TodoList.tsx';
import {QueryClient, QueryClientProvider} from '@tanstack/react-query';
import { ReactQueryDevtools } from '@tanstack/react-query-devtools'


const queryClient = new QueryClient();

const TodoListPage = () => {
  return (
    <div>
      <QueryClientProvider client={queryClient}>
        <TodoList />
        <ReactQueryDevtools initialIsOpen={false} />
      </QueryClientProvider>

    </div>
  );
};

export default TodoListPage;
