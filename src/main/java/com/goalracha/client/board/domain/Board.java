package com.goalracha.client.board.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Entity
@Table(name = "board")
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Board {
    @Id
    @SequenceGenerator(name = "boards_seq_gen", sequenceName = "boards_seq",initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "boards_seq_gen")

    @Column(name = "b_no", nullable = false)
    private Long bno; //게시판 일련번호

    @Column(name = "title", nullable = false)
    private String title; //제목

    @Column(name = "content", length = 4000, nullable = false)
    private String content; //내용

    @Column(name = "createdate", nullable = false)
    private Date createdate; //작성일시

    @Column(name = "editdate")
    private Date editdate; //수정일시

    @Column(name = "state")
    private Long state; //게시판 상태

    @Column(name = "u_no", nullable = false)
    private Long uno; //유저 일련번호

    // CRUD 테스트 수정
    public void changeTitle(String title) {
        this.title = title;
    }
    public void changeContent(String content) {
        this.content = content;
    }
    public void changeEditdate(Date editdate) {
        this.editdate = editdate;
    }
}