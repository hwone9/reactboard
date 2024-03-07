import React from "react";
import {useNavigate} from "react-router-dom";
import * as util from "../../../js/Util";
import Button from '@mui/material/Button';
import { Box, Card, CardContent, Divider, Stack, TextField, Typography } from "@mui/material";

const Board = ({idx, title, contents, createdBy}) => {
    console.log(idx);
    const navigate = useNavigate();
    const moveToUpdate = () => {
        navigate(`/board/update/${idx}` , {state: {"idx":idx, "title":title, "contents":contents, "createdBy":createdBy}});//파라미터 전달
    }
    
    const deleteBoard = async () => {
        if (window.confirm('게시글을 삭제하시겠습니까?')) {
            let resp = await util.process("delete",`/board/${idx}`, null);
            if (resp.RESULT==="SUCCESS") {
                navigate('/board');
            }
        }
    }
    
    const moveToList = () => {
        navigate("/board");
    }
    return(
        <Box>
            <Card sx={{ p: 0, position: 'relative' }}
                  elevation={9}
                  variant={undefined} >
                <CardContent>
                    <Typography variant="h1">{title}</Typography>
                    <Typography variant="body1" color="textSecondary" sx={{margin: '10px 0 10px 3px'}}>{createdBy}</Typography>
                    <Divider sx={{ bgcolor: '#eee' , margin: '15px 0'}} />
                    <TextField value={contents} label="내용"
                                defaultValue="내용을 입력해주세요."
                                sx={{width: '100%'}}
                                multiline disabled
                                rows={3} />
                </CardContent>
            </Card>
            
            <Stack direction="row" spacing={2} sx={{marginTop: '25px'}}>
                <Button variant="contained" onClick={moveToUpdate}>수정</Button>
                <Button variant="outlined" color="error" onClick={deleteBoard}>삭제</Button>
                <Button variant="outlined" onClick={moveToList}>목록</Button>
            </Stack>
        </Box>
    )
}

export default Board;