package com.alex.asset.comments;

import java.util.ArrayList;
import java.util.List;

public class CommentMapper {
    public static List<CommentDTO> toDTO(List<Comment> comments){
        List<CommentDTO> DTOs = new ArrayList<>();
        for (Comment comment: comments){
            DTOs.add(entityToDto(comment));
        }

        return  DTOs;
    }



    private static CommentDTO entityToDto(Comment comment){
        return new CommentDTO().toBuilder()
                .comment(comment.getComment())
                .created(comment.getCreated())
                .userName(comment.getUser().getFirstname() + " " + comment.getUser().getLastname())
                .build();
    }
}
