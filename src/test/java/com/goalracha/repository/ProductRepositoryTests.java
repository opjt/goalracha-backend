package com.goalracha.repository;

import java.util.UUID;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import lombok.extern.log4j.Log4j2;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Log4j2
public class ProductRepositoryTests {
    @Autowired
    ProductRepository productRepository;

    @Test
    public void testInsert() {
        for (int i = 0; i < 10; i++) {
            Product product = Product.builder().iname("상품" + i).build();
            // 2 개의 이미지 파일 추가
            product.addImageString(UUID.randomUUID().toString() + "-" + "IMAGE1.jpg");
            product.addImageString(UUID.randomUUID().toString() + "-" + "IMAGE2.jpg");
            productRepository.save(product);
            log.info("-------------------");
        }
    }

    @Transactional
    @Test
    public void testRead() {
        Long i_no = 2L;
        Optional<Product> result = productRepository.findById(i_no);
        Product product = result.orElseThrow();
        log.info(product); // --------- 1
        log.info(product.getImageList()); // ---------------------2
    }

    @Test
    public void testRead2() {
        Long i_no = 2L;
        Optional<Product> result = productRepository.selectOne(i_no);
        Product product = result.orElseThrow();
        log.info(product);
        log.info(product.getImageList());
    }

    @Commit
    @Transactional
    @Test
    public void testDelte() {
        Long i_no = 2L;
        productRepository.updateToDelete(i_no, true);
    }

    @Test
    public void testUpdate() {
        Long i_no = 2L;
        Product product = productRepository.selectOne(i_no).get();
        product.changeName("2 번 상품");
        // 첨부파일 수정
        product.clearList();
        product.addImageString(UUID.randomUUID().toString() + "-" +
                "NEWIMAGE1.jpg");
        product.addImageString(UUID.randomUUID().toString() + "-" +
                "NEWIMAGE2.jpg");
        product.addImageString(UUID.randomUUID().toString() + "-" +
                "NEWIMAGE3.jpg");
        productRepository.save(product);

    }
}