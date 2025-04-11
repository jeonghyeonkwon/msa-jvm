package jeonghyeon.msa.popularbatch.entity.popularposts;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@Getter
public class Posts {

    @Id
    private Long id;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @Column(name = "rankScore")
    private Long rankScore;

    @Column(name = "commentCount")
    private Long commentCount;



}
