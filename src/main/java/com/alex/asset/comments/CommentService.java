package com.alex.asset.comments;


import com.alex.asset.product.domain.Product;
import com.alex.asset.security.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepo commentRepo;

    public Comment createComment(String comment, Product product, User user) {
        return new Comment().toBuilder()
                .comment(comment)
                .user(user)
                .product(product)
                .build();

    }


}
