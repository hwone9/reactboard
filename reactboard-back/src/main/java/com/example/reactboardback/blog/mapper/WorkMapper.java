package com.example.reactboardback.blog.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface WorkMapper {
    List<Map<String,Object>> getWorkList(Map<String, Object> paramMap);

    int insertWork(Map<String,Object> paramMap);

    int updateWork(Map<String,Object> paramMap);

    int deleteWork(Map<String,Object> paramMap);
}