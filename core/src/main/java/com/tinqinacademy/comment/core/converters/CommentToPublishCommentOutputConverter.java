package com.tinqinacademy.comment.core.converters;

import com.tinqinacademy.comment.api.operations.hotel.publishcomment.PublishCommentOutput;
import com.tinqinacademy.comment.persistence.entities.Comment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CommentToPublishCommentOutputConverter implements Converter<Comment, PublishCommentOutput> {
    @Override
    public PublishCommentOutput convert(Comment input) {
        log.info("Start converting from Comment to PublishCommentOutput with input: {}", input);

        PublishCommentOutput output = PublishCommentOutput.builder()
                .id(input.getId().toString())
                .build();

        log.info("End converting from Comment to PublishCommentOutput with output: {}", output);
        return output;
    }
}
