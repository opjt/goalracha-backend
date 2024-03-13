package com.goalracha.dto.board;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {

    private Long bno; //게시판 일련번호
    private String title; //제목
    private String content; //내용

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") // 년월일시분초
    private Date createdate; //작성일시
 //   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") // 년월일시분초
    private Date editdate; //수정일시

    private Long state; //게시판 상태
    private Long uno; //유저 일련번호

    @JsonIgnore
    public Date getCreatedate() {
        return createdate;
    }

}
