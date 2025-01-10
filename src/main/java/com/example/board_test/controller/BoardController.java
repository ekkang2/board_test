package com.example.board_test.controller;

import com.example.board_test.dto.BoardDTO;
import com.example.board_test.entity.Board;
import com.example.board_test.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Controller
public class BoardController {


    private final BoardService boardService;

    // 글 목록
    @GetMapping("/board/list")
    public String list(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        log.info("page : " + page);
        log.info("size : " + size);
        Page<BoardDTO> boardDTO = boardService.selectBoardAll(page, size);
        model.addAttribute("boardDTO", boardDTO);
        model.addAttribute("totalPages", boardDTO.getTotalPages());
        model.addAttribute("currentPage", page);
        log.info("boardDTO : " + boardDTO);
        log.info("totalPages : " + boardDTO.getTotalPages());
        log.info("currentPage : " + page);

        return "/board/list";
    }

    // 글 작성
    @GetMapping("/board/write")
    public String write() {
        return "/board/write";
    }

    // 글 작성
    @PostMapping("/board/write")
    public String writePost(BoardDTO boardDTO) {
        log.info("boardDTO : " + boardDTO);
        boardService.insertBoard(boardDTO);

        return "redirect:/board/list";
    }

    // 글 보기
    @GetMapping("/board/view")
    public String view(int no, Model model) {
        log.info(no);

        BoardDTO boardDTO = boardService.selectBoard(no);
        model.addAttribute("boardDTO", boardDTO);
        return "/board/view";
    }


    // 글 수정
    @GetMapping("/board/modify")
    public String modify(int no, Model model) {
        log.info(no);

        BoardDTO boardDTO = boardService.selectBoard(no);
        model.addAttribute("boardDTO", boardDTO);
        return "/board/modify";
    }

    // 글 수정
    @PostMapping("/board/modify/{no}")
    public String modifyPost(@PathVariable("no") int no, BoardDTO boardDTO) {
        log.info("no : " + no);
        boardService.updateBoard(no, boardDTO);

        return "redirect:/board/list";
    }

    // 글 삭제
    @GetMapping("/board/delete")
    public String deletePost(int no) {
        boardService.deleteBoard(no);

        return "redirect:/board/list";
    }




}
