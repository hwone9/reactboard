import React, {useState} from "react";
import {useNavigate} from "react-router-dom";
import * as util from "../js/Util";

const BoardWrite = () => {
    const navigate = useNavigate();
    const [board, setBoard] = useState({
        "title": "",
        "contents": "",
        "createdBy": ""
    });
    const onChange = (event)=>{
        const { value, name } = event.target; //event.target에서 name과 value만 가져오기
        setBoard({
            ...board,
            [name]: value,
        });
    }
    // 2) 게시글 목록 데이터에 할당
    const saveBoard = async () => {
        let resp = await util.process("post",`/board`, board);
        if (resp.success) {
            navigate('/board');
        }
    };
    const backToList = () => {
        navigate("/board");
    }

    return (
        <div>
            <div>
                <span>제목</span>
                <input type="text" name="title" value={board.title} onChange={onChange}/>
            </div>
            <br/>
            <div>
                <span>작성자</span>
                <input
                    type="text"
                    name="createdBy"
                    value={board.createdBy}
                    onChange={onChange}
                />
            </div>
            <br/>
            <div>
                <span>내용</span>
                <textarea
                    name="contents"
                    cols="30"
                    rows="10"
                    value={board.contents}
                    onChange={onChange}
                ></textarea>
            </div>
            <br/>
            <div>
                <button onClick={saveBoard}>저장</button>
                <button onClick={backToList}>취소</button>
            </div>
        </div>
    )
}

export default BoardWrite