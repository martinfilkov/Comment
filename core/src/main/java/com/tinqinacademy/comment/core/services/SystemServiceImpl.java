package com.tinqinacademy.comment.core.services;

import com.tinqinacademy.comment.api.operations.exception.NotFoundException;
import com.tinqinacademy.comment.api.operations.system.deletecomment.DeleteCommentInput;
import com.tinqinacademy.comment.api.operations.system.deletecomment.DeleteCommentOutput;
import com.tinqinacademy.comment.api.operations.system.updatecomment.UpdateCommentInput;
import com.tinqinacademy.comment.api.operations.system.updatecomment.UpdateCommentOutput;
import com.tinqinacademy.comment.persistence.entities.Comment;
import com.tinqinacademy.comment.persistence.repositories.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class SystemServiceImpl implements SystemService{
    private final CommentRepository commentRepository;

    @Autowired
    public SystemServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public UpdateCommentOutput updateComment(UpdateCommentInput input) {
        log.info("Start updateComment input: {}", input);

        UpdateCommentOutput output = UpdateCommentOutput.builder()
                .id(input.getCommentId())
                .build();

        log.info("End updateComment output: {}", output);

        return output;
    }

    @Override
    public DeleteCommentOutput deleteComment(DeleteCommentInput input) {
        log.info("Start deleteComment input: {}", input);

        Comment comment = commentRepository.findById(UUID.fromString(input.getCommentId()))
                .orElseThrow(() -> new NotFoundException(String.format("Comment with id %s does not exist", input.getCommentId())));

        commentRepository.delete(comment);

        DeleteCommentOutput output = new DeleteCommentOutput();

        log.info("End deleteComment output: {}", output);

        return output;
    }
}
