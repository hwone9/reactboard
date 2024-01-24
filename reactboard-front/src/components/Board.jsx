import React from "react";
import {useNavigate} from "react-router-dom";
import * as util from "../js/Util";

const Board = ({idx, title, contents, createdBy}) => {
    console.log(idx);
    const navigate = useNavigate();
    const moveToUpdate = () => {
        navigate(`/update/${idx}` , {state: {"idx":idx, "title":title, "contents":contents, "createdBy":createdBy}});//파라미터 전달
    }
    
    const deleteBoard = async () => {
        if (window.confirm('게시글을 삭제하시겠습니까?')) {
            let resp = await util.process("delete",`/board/${idx}`, null);
            if (resp.success) {
                navigate('/board');
            }
        }
    }
    
    const moveToList = () => {
        navigate("/board");
    }
    return(
        <div>
            <h2>{title}</h2>
            <h5>{createdBy}</h5>
            <hr/>
            <p>{contents}</p>
            <div>
                <button onClick={moveToUpdate}>수정</button>
                <button onClick={deleteBoard}>삭제</button>
                <button onClick={moveToList}>목록</button>
            </div>
        </div>
    )
}

export default Board;