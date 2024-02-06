package com.example.reactboardback.controller;

import com.example.reactboardback.service.WorkService;
import com.example.reactboardback.util.Header;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class WorkController {
    private final WorkService workService;

    @GetMapping("/work")
    Header<List<Map<String,Object>>> getWorkList(Map<String,Object> param) {
        param.put("createdBy", "user01");//로그인한 사용자
        return workService.getWorkList(param);
    }

    @PostMapping("/work")
    Header<List<Map<String,Object>>> insertWork(@RequestBody Map<String,Object> param) {
        param.put("createdBy", "user01");//로그인한 사용자
        return workService.insertWork(param);
    }

    @PatchMapping("/work")
    Header<List<Map<String,Object>>> updateWork(@RequestBody Map<String,Object> param) {
        param.put("createdBy", "user01");//로그인한 사용자
        return workService.updateWork(param);
    }

    @DeleteMapping("/work/{idx}")
    Header<String> deleteBoard(@PathVariable Long idx, Map<String,Object> param) {
        param.put("createdBy", "user01");//로그인한 사용자
        param.put("idx", idx);
        return workService.deleteWork(param);
    }
}