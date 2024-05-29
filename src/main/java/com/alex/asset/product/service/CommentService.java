package com.alex.asset.product.service;


import com.alex.asset.product.domain.Comment;
import com.alex.asset.product.domain.Product;
import com.alex.asset.security.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    public Comment createComment(String comment, Product product, User user) {
        return new Comment().toBuilder()
                .comment(comment)
                .user(user)
                .product(product)
                .build();

    }


}
