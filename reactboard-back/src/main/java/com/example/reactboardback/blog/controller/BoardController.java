package com.example.reactboardback.blog.controller;

import com.example.reactboardback.blog.service.BoardService;
import com.example.reactboardback.util.Header;
import com.example.reactboardback.util.Search;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class BoardController {
    private final BoardService boardService;

    //Http Get 방식으로 주소 가장 뒤 /board로 접근
    @GetMapping("/board")
    Map<String,Object> getBoardList(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, Search search) {
        Map<String, Object> retrunMap = new HashMap<>();
        try {
            retrunMap.put("RESULT", "SUCCESS");
            retrunMap.put("RES", boardService.getBoardList(page, size, search));
        } catch (Exception e) {
            retrunMap.put("RESULT", "ERROR");
            retrunMap.put("ERR_MSG", e.getMessage());
        }
        return retrunMap;
    }

    //idx의 데이터 1개를 조회한다.
    @GetMapping("/board/{idx}")
    Map<String,Object> getBoardOne(@PathVariable Long idx) {
        Map<String, Object> retrunMap = new HashMap<>();
        try {
            retrunMap.put("RESULT", "SUCCESS");
            retrunMap.put("RES", boardService.getBoardOne(idx) );
        } catch (Exception e) {
            retrunMap.put("RESULT", "ERROR");
            retrunMap.put("ERR_MSG", e.getMessage());
        }
        return retrunMap;
    }

    @PostMapping("/board")
    Map<String,Object> createBoard(@RequestBody Map<String,Object> paramMap) {
        Map<String, Object> retrunMap = new HashMap<>();
        try {
            retrunMap.put("RESULT", "SUCCESS");
            retrunMap.put("RES", boardService.insertBoard(paramMap) );
        } catch (Exception e) {
            retrunMap.put("RESULT", "ERROR");
            retrunMap.put("ERR_MSG", e.getMessage());
        }
        return retrunMap;
    }

    @PatchMapping("/board")
    Map<String,Object> updateBoard(@RequestBody Map<String,Object> paramMap) {
        Map<String, Object> retrunMap = new HashMap<>();
        try {
            retrunMap.put("RESULT", "SUCCESS");
            retrunMap.put("RES", boardService.updateBoard(paramMap) );
        } catch (Exception e) {
            retrunMap.put("RESULT", "ERROR");
            retrunMap.put("ERR_MSG", e.getMessage());
        }
        return retrunMap;
    }

    @DeleteMapping("/board/{idx}")
    Map<String,Object> deleteBoard(@PathVariable Long idx) {
        Map<String, Object> retrunMap = new HashMap<>();
        try {
            retrunMap.put("RESULT", "SUCCESS");
            retrunMap.put("RES", boardService.deleteBoard(idx) );
        } catch (Exception e) {
            retrunMap.put("RESULT", "ERROR");
            retrunMap.put("ERR_MSG", e.getMessage());
        }
        return retrunMap;
    }
}