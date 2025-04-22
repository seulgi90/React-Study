import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Layout from '../components/layout/Layout';
import useAxios from '../utils/useAxios';

import { useUserStore } from '../store/userStore';

interface Member {
  email: string;
  name: string;
}

export default function UserList() {
  const [members, setMembers] = useState<Member[]>([]);
  const [error, setError] = useState<string | null>(null);
  const user = useUserStore((state) => state.user);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchMembers = async () => {
      try {
        const res = await useAxios.get('/hasRole');
        setMembers(res.data);
      } catch (err: any) {
        if (err.response?.status === 403) {
          // 권한 없을 때 안내 후 리디렉션
          setError('접근 권한이 없습니다. 메인 페이지로 이동합니다.');

          setTimeout(() => {
            navigate('/', { replace: true });
          }, 1500); // 1.5초 후 메인 페이지로 이동
        } else {
          setError('데이터를 불러오지 못했습니다.');
        }
      }
    };
    fetchMembers();
  }, [user]);

  if (error) return <p className="text-red-500 text-center mt-10">{error}</p>;

  return (
    <Layout>
      <div>
        <h2>UserList Page</h2>

        {error ? (
          <p className="text-red-500">{error}</p>
        ) : (
          <div>
            <h1 className="text-lg font-bold mb-4">회원 목록</h1>
            <ul className="space-y-2">
              {members.map((member) => (
                    <li key={member.email}>
                    {member.name} ({member.email})
                  </li>
              ))}
            </ul>
          </div>
        )}
      </div>
    </Layout>
  );
}
