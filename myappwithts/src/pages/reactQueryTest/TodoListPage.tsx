import TodoList from './TodoList.tsx';
import {QueryClient, QueryClientProvider} from '@tanstack/react-query';

const queryClient = new QueryClient();

const TodoListPage = () => {
  return (
    <div>
      <QueryClientProvider client={queryClient}>
        <TodoList />
      </QueryClientProvider>

    </div>
  );
};

export default TodoListPage;
