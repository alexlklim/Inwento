package com.alex.asset.comments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Long> {


    @Query("SELECT c " +
            "FROM Comment c " +
            "WHERE c.product.id = ?1 " +
            "ORDER BY c.created DESC")
    List<Comment> getCommentsByProductId(Long productId);
}
