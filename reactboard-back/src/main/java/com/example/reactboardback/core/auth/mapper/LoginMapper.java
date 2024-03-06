package com.example.reactboardback.core.auth.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface LoginMapper {

    int joinInfo(Map<String, Object> paramMap);

    Map<String,Object> selectUserLoginInfo(Map<String, Object> paramMap);

}