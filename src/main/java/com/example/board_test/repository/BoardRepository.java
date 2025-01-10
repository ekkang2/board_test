package com.example.board_test.repository;

import com.example.board_test.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {

    // 목록 페이징 처리
    Page<Board> findAll(Pageable pageable);

}
