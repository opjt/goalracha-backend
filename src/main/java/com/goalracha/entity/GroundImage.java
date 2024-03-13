package com.goalracha.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Embeddable
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroundImage {

    private String fileDirectory;
    private int ord;

    public void setOrd(int ord) {
        this.ord = ord;
    }



}
