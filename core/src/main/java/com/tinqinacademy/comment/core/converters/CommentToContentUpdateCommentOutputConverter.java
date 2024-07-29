package com.tinqinacademy.comment.core.converters;

import com.tinqinacademy.comment.api.operations.hotel.partialupdatecomment.ContentUpdateCommentOutput;
import com.tinqinacademy.comment.persistence.entities.Comment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CommentToContentUpdateCommentOutputConverter implements Converter<Comment, ContentUpdateCommentOutput> {

    @Override
    public ContentUpdateCommentOutput convert(Comment input) {
        log.info("Start converting from Comment to ContentUpdateCommentOutput with input: {}", input);

        ContentUpdateCommentOutput output = ContentUpdateCommentOutput.builder()
                .id(input.getId().toString())
                .build();

        log.info("End converting from Comment to ContentUpdateCommentOutput with output: {}", output);
        return output;
    }
}
