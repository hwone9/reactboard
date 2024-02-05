package com.example.reactboardback.service;

import com.example.reactboardback.mapper.WorkMapper;
import com.example.reactboardback.util.Header;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class WorkService {
    private final WorkMapper workMapper;

    public Header<List<Map<String,Object>>> getWorkList(Map<String,Object> param) {
        List<Map<String,Object>> WorkList = workMapper.getWorkList(param);
        return Header.OK(WorkList);
    }

    public Header<List<Map<String,Object>>> insertWork(Map<String,Object> paramMap) {
        if (workMapper.insertWork(paramMap) > 0) {
            return Header.OK();
        } else {
            return Header.ERROR("ERROR");
        }
    }

    public Header<List<Map<String,Object>>> updateWork(Map<String,Object> paramMap) {
        if (workMapper.updateWork(paramMap) > 0) {
            return Header.OK();
        } else {
            return Header.ERROR("ERROR");
        }
    }

    public Header<List<Map<String,Object>>> updateWorkDone(Map<String,Object> paramMap) {
        if (workMapper.updateWorkDone(paramMap) > 0) {
            return Header.OK();
        } else {
            return Header.ERROR("ERROR");
        }
    }

    public Header<String> deleteWork(Map<String,Object> paramMap) {
        if (workMapper.deleteWork(paramMap) > 0) {
            return Header.OK();
        } else {
            return Header.ERROR("ERROR");
        }
    }
}