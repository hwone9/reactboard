package com.example.reactboardback.blog.controller;

import com.example.reactboardback.blog.service.WorkService;
import com.example.reactboardback.util.Header;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class WorkController {
    private final WorkService workService;

    @GetMapping("/work")
    Map<String,Object> getWorkList(Map<String,Object> param) {
        Map<String, Object> retrunMap = new HashMap<>();
        try {
            param.put("createdBy", "user01");//로그인한 사용자
            retrunMap.put("RES", workService.getWorkList(param) );
            retrunMap.put("RESULT", "SUCCESS");
        } catch (Exception e) {
            retrunMap.put("RESULT", "ERROR");
            retrunMap.put("ERR_MSG", e.getMessage());
        }
        return retrunMap;
    }

    @PostMapping("/work")
    Map<String,Object> insertWork(@RequestBody Map<String,Object> param) {
        param.put("createdBy", "user01");//로그인한 사용자
        return workService.insertWork(param);
    }

    @PatchMapping("/work")
    Map<String,Object> updateWork(@RequestBody Map<String,Object> param) {
        param.put("createdBy", "user01");//로그인한 사용자
        return workService.updateWork(param);
    }

    @DeleteMapping("/work/{idx}")
    Map<String,Object> deleteBoard(@PathVariable Long idx, Map<String,Object> param) {
        param.put("createdBy", "user01");//로그인한 사용자
        param.put("idx", idx);
        return workService.deleteWork(param);
    }
}