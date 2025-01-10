package com.example.board_test.service;

import com.example.board_test.dto.BoardDTO;
import com.example.board_test.entity.Board;
import com.example.board_test.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final ModelMapper modelMapper;

    // 글 등록
    public BoardDTO insertBoard(BoardDTO boardDTO) {

        log.info("service로 들어오는 boardDTO : " + boardDTO);

        Board board = modelMapper.map(boardDTO, Board.class);
        Board savedBoard = boardRepository.save(board);

        return modelMapper.map(savedBoard, BoardDTO.class);

    }

    // 글 목록
    public Page<BoardDTO> selectBoardAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Board> boardEntities = boardRepository.findAll(pageable);
        log.info("boardEntities : " + boardEntities);

        return boardEntities.map(board -> modelMapper.map(board, BoardDTO.class));
    }

    // 글 상세
    public BoardDTO selectBoard(int no) {
        log.info("no : " + no);

        Optional<Board> board = boardRepository.findById(no);
        if (board.isPresent()) {
            return modelMapper.map(board.get(), BoardDTO.class);
        } else {
            throw new RuntimeException("Board not found with no: " + no);
        }
    }

    // 글 수정
    @Transactional
    public void updateBoard(int no, BoardDTO boardDTO) {
        // 게시글 조회
        Optional<Board> optionalBoard = boardRepository.findById(no);

        if (optionalBoard.isPresent()) {
            // 기존 게시글 가져오기
            Board board = optionalBoard.get();

            // 수정된 내용 반영
            board.setTitle(boardDTO.getTitle());
            board.setContent(boardDTO.getContent());
            board.setWriter(boardDTO.getWriter());

            boardRepository.save(board);
        } else {
            throw new RuntimeException("Board not found with no: " + no);
        }
    }

    // 글 삭제
    public void deleteBoard(int no) {
        boardRepository.deleteById(no);
    }

}
