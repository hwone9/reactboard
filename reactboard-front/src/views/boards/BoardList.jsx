import React, { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import * as util from "src/js/Util";
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import { Box, Button, IconButton, InputBase, MenuItem, Pagination, Select, Stack } from '@mui/material';
import SearchIcon from '@mui/icons-material/Search';

const BoardList = () => {
    const navigate = useNavigate();

    const [boardList, setBoardList] = useState([]);
    // const [pageList, setPageList] = useState([]);

    const [curPage, setCurPage] = useState(0); //현재 페이지 세팅
    // const [prevBlock, setPrevBlock] = useState(0); //이전 페이지 블록
    // const [nextBlock, setNextBlock] = useState(0); //다음 페이지 블록
    const [lastPage, setLastPage] = useState(0); //마지막 페이지

    const [search, setSearch] = useState({
        page: 1,
        sk: '',
        sv: '',
    });

    const getBoardList = async () => {
        if (search.page === curPage) return; //현재 페이지와 누른 페이지가 같으면 return

        const queryString = Object.entries(search)
            .map((e) => e.join('='))
            .join('&');

        let resp = await util.process("get",`/board?${queryString}`, null); // 2) 게시글 목록 데이터에 할당
        if (resp.RESULT==="SUCCESS") {
            setBoardList(resp.RES.data); // 3) boardList 변수에 할당
            const pngn = resp.RES.pagination;

            const { totalPageCnt } = pngn;

            // setCurPage(search.page);
            // setPrevBlock(prevBlock);
            // setNextBlock(nextBlock);
            setLastPage(totalPageCnt);

            // const tmpPages = [];
            // for (let i = startPage; i <= endPage; i++) {
            //     tmpPages.push(i);
            // }
            // setPageList(tmpPages);
        }
    };

    const moveToWrite = () => {
        navigate('/board/write');
    };

    const onClick = (event) => {
        // let value = event.target.value;
        let value = event.target.outerText;
        setSearch({
            ...search,
            page: value,
        });

        getBoardList();
    };

    const onChange = (event) => {
        const { value, name } = event.target; //event.target에서 name과 value만 가져오기
        setSearch({
            ...search,
            [name]: value,
        });
    };

    const onSearch = () => {
        if (search.sk !== '' && search.sv !== '') {
            setSearch({
                ...search,
                page: 1,
            });
            setCurPage(0);
            getBoardList();
        } else {
            alert("구분 선택하세용");
        }
    };

    useEffect(() => {
        getBoardList(); // 1) 게시글 목록 조회 함수 호출
    }, [search]);

    return (
        <Box>
            <Box>
                <Select name='sk' defaultValue={search.sk}
                        displayEmpty autoWidth sx={{height: '40px'}}
                        onChange={onChange} >
                    <MenuItem value="">선택</MenuItem>
                    <MenuItem value="title">제목</MenuItem>
                    <MenuItem value="contents">내용</MenuItem>
                </Select>
                <InputBase sx={{ ml: 1, flex: 1 }} placeholder="검색어를 입력하세요"
                            type='text' name='sv' onChange={onChange} />
                <IconButton type="button" sx={{ p: '10px' }} aria-label="search" onClick={onSearch}>
                    <SearchIcon />
                </IconButton>
                
                <Button variant="contained" onClick={moveToWrite}>글쓰기</Button>    
            </Box>
            
            <TableContainer component={Paper}>
                <Table sx={{ minWidth: 650 }} aria-label="simple table">
                    <TableHead>
                    <TableRow>
                        <TableCell>No</TableCell>
                        <TableCell align="right">title</TableCell>
                        <TableCell align="right">contents</TableCell>
                        <TableCell align="right">writer</TableCell>
                        <TableCell align="right">created at</TableCell>
                    </TableRow>
                    </TableHead>
                    <TableBody>
                    {boardList.map((row) => (
                        <TableRow key={row.idx}
                                sx={{ '&:last-child td, &:last-child th': { border: 0 } }} >
                            <TableCell component="th" scope="row"> {row.idx}</TableCell>
                            <TableCell align="right">
                                <Link to={`/board/${row.idx}`}>{row.title}</Link>
                            </TableCell>
                            <TableCell align="right">{row.contents}</TableCell>
                            <TableCell align="right">{row.createdBy}</TableCell>
                            <TableCell align="right">{row.createdAt}</TableCell>
                        </TableRow>
                    ))}
                    </TableBody>
                </Table>
            </TableContainer>

            <Stack alignItems="center" sx={{marginTop: "30px"}}>
                <Pagination count={lastPage} 
                            color="primary" 
                            sx={{textAlign: "center"}}
                            onChange={(e) => onClick(e)} />
            </Stack>
        </Box>
    );
};

export default BoardList;