package com.example.reactboardback.blog.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

import java.util.HashMap;
import java.util.Map;

@Mapper
public interface BoardMapper {
    /*
        mapper xml파일의 resultType 해당하는 클래스에 결과를 담으며,
        N개가 되므로 MutableList로 Return 타입을 설정합니다.
    */
    List<Map<String,Object>> getBoardList(HashMap<String, Object> paramMap);

    int getBoardTotalCount(HashMap<String, Object> paramMap);

    Map<String,Object> getBoardOne(Long idx);

    int insertBoard(Map<String,Object> paramMap);

    int updateBoard(Map<String,Object> paramMap);

    int deleteBoard(Long idx);
}