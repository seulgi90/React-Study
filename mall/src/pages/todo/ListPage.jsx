import React from 'react';
import {useSearchParams} from "react-router-dom";

function ListPage() {
    // 쿼리스트링에서 추출
    const [queryParams] = useSearchParams();
    const page =queryParams.get('page') ? parseInt(queryParams.get('page')) : 1
    const size =queryParams.get('size') ? parseInt(queryParams.get('size')) : 10

    return (
        <div className="p-4 w-full bg-orange-200 ">
            <div className="text-3xl font-extrabold">
                Todo List Page Component
            </div>
        </div>
    );
}

export default ListPage;