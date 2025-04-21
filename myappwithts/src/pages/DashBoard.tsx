import { useEffect, useState } from 'react';
import Layout from '../components/layout/Layout';
import useAxios from '../utils/useAxios';
import { useUserStore } from '../store/userStore';

export default function DashBoard() {
  const [members, setMembers] = useState([]);
  const [error, setError] = useState<string | null>(null);
  const user = useUserStore((state) => state.user)

  useEffect(() => {
    const fetchMembers = async () => {
      try {
        const res = await useAxios.get('/hasRole');
        setMembers(res.data);
      } catch (err: any) {
        if (err.response?.status === 403) {
          setError('권한이 없습니다.');
        } else {
          setError('데이터를 불러오지 못했습니다.');
        }
      }
    };
    fetchMembers();
  }, [user]);

  if (error) return <p className="text-red-500">{error}</p>;

  return (
    <Layout>
      <div>
        <h2>Welcome DashBoard - 권한 테스트 페이지</h2>

        {error ? (
          <p className="text-red-500">{error}</p>
        ) : (
          <div>
            <h1 className="text-lg font-bold mb-4">회원 목록</h1>
            <ul className="space-y-2">
              {members.map((member: any) => (
                <li key={member.id}>{member.email}</li>
              ))}
            </ul>
          </div>
        )}
      </div>
    </Layout>
  );
}
