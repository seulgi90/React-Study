import { useState } from "react"

function App() {
  const [emailId, setEmailId] = useState('');


  return (
    <>
      <div>
        <div>
        <input
          type="text"
          value={emailId}
          onChange={(e) => setEmailId(e.target.value)}
          placeholder="이메일 아이디"
        />
        <span style={{ marginLeft: '4px' }}>@nmplus.co.kr</span>
        </div>
        <input type="password" />
        <button>로그인</button>
      </div>

    </>
  )
}

export default App
