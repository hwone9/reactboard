package com.example.reactboardback.entity;

//spring boot 3에는 Jakarta EE 9이 포함됨에 따라 javax 관련 패키지명이 javax에서 jakarta로 변경되었습니다.
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "TB_BOARD")
public class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String title;
    private String contents;
    private String createdBy;
    private Date createdAt;
}