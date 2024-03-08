package com.goalracha.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "image")
@Getter
@ToString(exclude = "imageList")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "i_no_seq")
    @SequenceGenerator(name = "i_no_seq", sequenceName = "INO_SEQ")
    private Long i_no;
    private Long g_no;
    private String iname;
    private boolean delFlag;

    public void changeDel(boolean delFlag) {
        this.delFlag = delFlag;
    }

    @ElementCollection
    @Builder.Default
    private List<ProductImage> imageList = new ArrayList<>();

    public void changeName(String name) {
        this.iname = name;
    }

    public void addImage(ProductImage image) {
        image.setOrd(this.imageList.size());
        imageList.add(image);
    }

    public void addImageString(String fileName) {
        ProductImage productImage =
                ProductImage.builder().fileName(fileName).build();
        addImage(productImage);
    }

    public void clearList() {
        this.imageList.clear();
    }
}