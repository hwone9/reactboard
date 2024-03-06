import React, { useState } from "react";
import axios from "axios";
import useAsync from "../../hooks/useAsync";

async function getUser(id) {
  const response = await axios.get(
    `https://jsonplaceholder.typicode.com/users/${id}`
  );
  return response.data;
}

function User({id}) {
  const [state] = useAsync(()=>getUser(id), [id]);//callback :함수로 전달해야하므로 arrow function 사용해서 전달
  const {loading, data:user, error} = state;// state.data 를 users 키워드로 조회
  if (loading) return <div>로딩중..</div>;
  if (error) return <div>에러가 발생했습니다</div>;
  if (!user) return null;
  return (
    <div>
      <p>name : {user.username}</p>
      <p>email : {user.email}</p>
    </div>
  )

}

// useAsync 에서는 Promise 의 결과를 바로 data 에 담기 때문에,
// 요청을 한 이후 response 에서 data 추출하여 반환하는 함수를 따로 만들었습니다.
async function getUsers() {
  const response = await axios.get(
    'https://jsonplaceholder.typicode.com/users'
  );
  return response.data;
}

function Users() {
  const [userid, setUserid] = useState('');
  const [state, refetch] = useAsync(getUsers, [], true);
  
  const {loading, data:users, error} = state;// state.data 를 users 키워드로 조회

  if (loading) return <div>로딩중..</div>;
  if (error) return <div>에러가 발생했습니다</div>;
  if (!users) return <button onClick={refetch}>reload</button>;
  return (
    <>
      <ul>
        {state.data.map((user) => (
          <li key={user.id} onClick={()=>{setUserid(user.id)}}>
            {user.username} ({user.name})
          </li>
        ))}
      </ul>
      <button onClick={refetch}>reload</button>
      <hr />
      {
        userid && <User id={userid}/>
      }
    </>
  );
}

export default Users;
