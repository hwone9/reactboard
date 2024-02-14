package com.example.reactboardback.blog.service;

import com.example.reactboardback.blog.mapper.BoardMapper;
import com.example.reactboardback.util.Header;
import com.example.reactboardback.util.Pagination;
import com.example.reactboardback.util.Search;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardMapper boardMapper;

    public Header<List<Map<String,Object>>> getBoardList(int page, int size, Search search) {
        HashMap<String, Object> paramMap = new HashMap<>();

        if (page <= 1) {    //페이지가 1 이하로 입력되면 0으로 고정,
            paramMap.put("page", 0);
        } else {            //페이지가 2 이상
            paramMap.put("page", (page - 1) * size);
        }
        paramMap.put("size", size);
        paramMap.put("sk", search.getSk());
        paramMap.put("sv", search.getSv());

        List<Map<String,Object>> boardList = boardMapper.getBoardList(paramMap);
        Pagination pagination = new Pagination(
                boardMapper.getBoardTotalCount(paramMap),
                page,
                size,
                10
        );

        return Header.OK(boardList, pagination);
    }

    public Header<Map<String,Object>> getBoardOne(Long idx) {
        return Header.OK(boardMapper.getBoardOne(idx));
    }

    public Header<Map<String,Object>> insertBoard(Map<String,Object> paramMap) {
//        BoardEntity entity = boardSaveDto.toEntity();
        if (boardMapper.insertBoard(paramMap) > 0) {
            return Header.OK();
        } else {
            return Header.ERROR("ERROR");
        }
    }

    public Header<Map<String,Object>> updateBoard(Map<String,Object> paramMap) {
//        BoardEntity entity = boardSaveDto.toEntity();
        if (boardMapper.updateBoard(paramMap) > 0) {
            return Header.OK();
        } else {
            return Header.ERROR("ERROR");
        }
    }

    public Header<String> deleteBoard(Long idx) {
        if (boardMapper.deleteBoard(idx) > 0) {
            return Header.OK();
        } else {
            return Header.ERROR("ERROR");
        }
    }
}