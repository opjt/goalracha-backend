package com.goalracha.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@ToString(exclude = "imageList")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroundImage {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "i_no_seq")
    @SequenceGenerator(name = "i_no_seq", sequenceName = "INO_SEQ")
    @Column(name = "i_no")
    private Long iNo;
    @Column(nullable = false)
    private boolean delFlag;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="g_no", referencedColumnName="g_no")
    private Ground ground;

    public void changeDel(boolean delFlag) {
        this.delFlag = delFlag;
    }
    @ElementCollection
    @Builder.Default
    private List<GroundImageList> imageList = new ArrayList<>();


    public void addImage(GroundImageList image) {
        image.setOrd(this.imageList.size());
        imageList.add(image);
    }

    public void addImageString(String fileName) {
        GroundImageList groundImageList =
                GroundImageList.builder().fileName(fileName).build();
        addImage(groundImageList);
    }

    public void clearList() {
        this.imageList.clear();
    }
}
