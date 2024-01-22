import React, {useEffect, useState} from "react";
import axios from "axios";
import {Link, useNavigate} from "react-router-dom";

const BoardList = () => {
    const navigate = useNavigate();
    const [boardList, setBoardList] = useState([]);//boardList 초기화
    const getBoardList = async () => {
        const resp = await (await axios.get('/board')).data;
        setBoardList(resp.data);//boardList 변수에 set
        const paging = resp.pagination;
        console.log(paging);
    }

    const createBtn = ()=>{
        navigate('/write');
    }

    useEffect(()=>{
        getBoardList();//목록 조회 함수 호출
    },[]);//최초 렌더링시 호출

 return(
     <div>
         <div>
             <ul>
                 {boardList.map((board) => (
                     <li key={board.idx}>
                         <Link to={`/board/${board.idx}`}>{board.title}</Link>
                     </li>
                 ))}
             </ul>
         </div>
         <div>
             <button onClick={createBtn}>글쓰기</button>
         </div>

     </div>
 )
}

export default BoardList;