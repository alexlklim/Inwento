package com.alex.asset.product.mappers;

import com.alex.asset.product.domain.Comment;
import com.alex.asset.product.dto.CommentDTO;

import java.util.ArrayList;
import java.util.List;

public class CommentMapper {
    public static List<CommentDTO> toDTOs(List<Comment> comments){
        List<CommentDTO> DTOs = new ArrayList<>();
        for (Comment comment: comments){
            DTOs.add(toDTO(comment));
        }
        return  DTOs;
    }
    private static CommentDTO toDTO(Comment comment){
        return new CommentDTO().toBuilder()
                .comment(comment.getComment())
                .created(comment.getCreated())
                .userName(comment.getUser().getFirstname() + " " + comment.getUser().getLastname())
                .build();
    }
}
