package com.tinqinacademy.comment.core.converters;

import com.tinqinacademy.comment.api.operations.hotel.publishcomment.PublishCommentInput;
import com.tinqinacademy.comment.persistence.entities.Comment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class PublishCommentInputToCommentConverter implements Converter<PublishCommentInput, Comment> {
    @Override
    public Comment convert(PublishCommentInput input) {
        log.info("Start converting from PublishCommentInput to Comment with input: {}", input);

        Comment output = Comment.builder()
                .roomId(UUID.fromString(input.getRoomId()))
                .userId(UUID.fromString(input.getUserId()))
                .content(input.getContent())
                .lastEditedBy(UUID.fromString(input.getUserId()))
                .build();

        log.info("End converting from PublishCommentInput to Comment with output: {}", output);
        return output;
    }
}
