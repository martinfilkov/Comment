package com.tinqinacademy.comment.core.converters;

import com.tinqinacademy.comment.api.operations.system.updatecomment.AdminUpdateCommentOutput;
import com.tinqinacademy.comment.persistence.entities.Comment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CommentToAdminUpdateCommentOutputConverter implements Converter<Comment, AdminUpdateCommentOutput> {
    @Override
    public AdminUpdateCommentOutput convert(Comment input) {
        log.info("Start converting from Comment to AdminUpdateCommentOutput with input: {}", input);

        AdminUpdateCommentOutput output = AdminUpdateCommentOutput.builder()
                .id(input.getId().toString())
                .build();

        log.info("End converting from Comment to AdminUpdateCommentOutput with output: {}", output);
        return output;
    }
}
