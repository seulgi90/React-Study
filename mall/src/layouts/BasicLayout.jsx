import React from 'react';
import BasicMenu from "../components/menus/BasicMenu.jsx";

function BasicLayout({children}) {
    return (
        <>
            {/* 기존헤더대신BasicMenu*/ }
            <BasicMenu/>
            {/* 상단여백my-5 제거*/}
            <div className="bg-white my-5 w-full flex flex-col space-y-1 md:flex-row md:space-x-1 md:space-y0">
                {/* 상단여백py-40 변경flex 제거*/}
                <main className="bg-sky-300 md:w-2/3 lg:w-3/4 px-5 py-5">{children}</main>
                {/* 상단여백py-40 제거flex 제거*/}
                <aside className="bg-green-300 md:w-1/3 lg:w-1/4 px-5 flex py-5">
                    <h1 className="text-2xl md:text-4xl">Sidebar</h1>
                </aside>
            </div>
        </>
    )
}

export default BasicLayout;