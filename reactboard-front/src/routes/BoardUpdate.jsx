import React, {useState} from "react";
import {useLocation, useNavigate, useParams} from "react-router-dom";
import * as util from "../js/Util";

const BoardUpdate = () => {
    const navigate = useNavigate();
    const { idx } = useParams(); // /board/:idx와 동일한 변수명으로 데이터를 꺼낼 수 있습니다.
    const {state} = useLocation();
    const [board, setBoard] = useState(state);

    const onChange = (event) => {
        const { value, name } = event.target; //event.target에서 name과 value만 가져오기
        setBoard({
            ...board,
            [name]: value,
        });
    }
    
    const updateBoard = async () => {
        let resp = await util.process("patch",`/board`, board);
        if (resp.success) {
            navigate(`/board/${idx}`);
        }
    }
    
    const backToList = () => {
        navigate(`/board/${idx}`);
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
                <button onClick={updateBoard}>수정</button>
                <button onClick={backToList}>취소</button>
            </div>
        </div>
    )
}

export default BoardUpdate;