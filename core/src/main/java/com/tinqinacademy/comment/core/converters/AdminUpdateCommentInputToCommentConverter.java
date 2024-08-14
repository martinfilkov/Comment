package com.tinqinacademy.comment.core.converters;

import com.tinqinacademy.comment.api.operations.system.updatecomment.AdminUpdateCommentInput;
import com.tinqinacademy.comment.persistence.entities.Comment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class AdminUpdateCommentInputToCommentConverter implements Converter<AdminUpdateCommentInput, Comment> {
    @Override
    public Comment convert(AdminUpdateCommentInput input) {
        log.info("Start converting from AdminUpdateCommentInput to Comment with input: {}", input);

        Comment output = Comment.builder()
                .id(UUID.fromString(input.getCommentId()))
                .content(input.getContent())
                .roomId(UUID.fromString(input.getRoomId()))
                .userId(UUID.fromString(input.getUserId()))
                .build();

        log.info("Start converting from AdminUpdateCommentInput to Comment with output: {}", output);
        return output;
    }
}
