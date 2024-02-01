import React, {useState} from "react";
import {useNavigate} from "react-router-dom";
import * as util from "../../js/Util";
import { Box, Button, Card, CardContent, Stack, TextField } from "@mui/material";

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
    const saveBoard = () => {
        let resp = util.callApi("post",`/board`, board);
        if (resp.success) {
            navigate('/board');
        }
    };
    const backToList = () => {
        navigate("/board");
    }

    return (
        <Box>
            <Card sx={{ p: 0, position: 'relative' }}
                  elevation={9}
                  variant={undefined}>
                <CardContent>
                    <TextField label="제목" variant="standard" sx={{width: '100%', marginTop: '25px'}}
                                type="text" name="title" value={board.title}
                                onChange={onChange} />

                    <TextField label="작성자" variant="standard" sx={{width: '100%', marginTop: '25px'}}
                                type="text" name="createdBy" value={board.createdBy}
                                onChange={onChange} />
                    
                    <TextField label="내용" sx={{width: '100%', marginTop: '25px'}} multiline rows={3}
                                name="contents" value={board.contents} onChange={onChange} />
                </CardContent>
            </Card>
            <Stack direction="row" spacing={2} sx={{marginTop: '25px'}}>
                <Button variant="contained" onClick={saveBoard}>저장</Button>
                <Button variant="outlined" onClick={backToList}>취소</Button>
            </Stack>
        </Box>
    )
}

export default BoardWrite