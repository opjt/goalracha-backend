package com.goalracha.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroundImageList {

    private String fileName;
    @Column(nullable = false, length = 10)
    private int ord;

    public void setOrd(int ord) {
        this.ord = ord;
    }
}
