import React, {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import * as util from "../../js/Util";
import Board from "../../components/Board";


const BoardDetail = () => {
    const { idx } = useParams(); // /board/:idx와 동일한 변수명으로 데이터를 꺼낼 수 있습니다.
    const [board, setBoard] = useState({});
    const [loading, setLoading] = useState(false);

    const getBoardDetail = () => {
        let resp = util.callApi("get",`/board/${idx}`, null);
        if (resp.success) {
            resp = resp.resData;
            setBoard(resp.data);
            setLoading(true);
        }
    }

    useEffect(()=>{
        getBoardDetail();
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);

    return(
        <div>
            {!loading ? (
                    <h2>Loading...</h2>
            ) : (
                <Board
                    idx={board.idx}
                    title={board.title}
                    contents={board.contents}
                    createdBy={board.createdBy}
                />
            )}
        </div>
    )
}

export default BoardDetail;