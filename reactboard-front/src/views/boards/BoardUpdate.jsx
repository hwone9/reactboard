import React, {useState} from "react";
import {useLocation, useNavigate, useParams} from "react-router-dom";
import * as util from "../../js/Util";
import { Box, Button, Card, CardContent, Stack, TextField } from "@mui/material";

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
                <Button variant="contained" onClick={updateBoard}>수정</Button>
                <Button variant="outlined" onClick={backToList}>취소</Button>
            </Stack>
        </Box>
    )
}

export default BoardUpdate;