package com.example.reactboardback.controller;

import com.example.reactboardback.entity.BoardEntity;
import com.example.reactboardback.service.BoardService;
import com.example.reactboardback.util.Header;
import com.example.reactboardback.util.Search;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class BoardController {
    private final BoardService boardService;

    //Http Get 방식으로 주소 가장 뒤 /board로 접근
    @GetMapping("/board")
    Header<List<BoardEntity>> getBoardList(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, Search search) {
        return boardService.getBoardList(page, size, search);
    }

    //idx의 데이터 1개를 조회한다.
    @GetMapping("/board/{idx}")
    Header<BoardEntity> getBoardOne(@PathVariable Long idx) {
        return boardService.getBoardOne(idx);
    }

    @PostMapping("/board")
    Header<BoardEntity> createBoard(@RequestBody Map<String,Object> paramMap) {
        return boardService.insertBoard(paramMap);
    }

    @PatchMapping("/board")
    Header<BoardEntity> updateBoard(@RequestBody Map<String,Object> paramMap) {
        return boardService.updateBoard(paramMap);
    }

    @DeleteMapping("/board/{idx}")
    Header<String> deleteBoard(@PathVariable Long idx) {
        return boardService.deleteBoard(idx);
    }
}