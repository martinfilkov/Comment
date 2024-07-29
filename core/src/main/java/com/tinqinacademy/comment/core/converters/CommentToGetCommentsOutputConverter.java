package com.tinqinacademy.comment.core.converters;

import com.tinqinacademy.comment.api.operations.hotel.getcomments.GetCommentsOutput;
import com.tinqinacademy.comment.persistence.entities.Comment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@Component
public class CommentToGetCommentsOutputConverter implements Converter<Comment, GetCommentsOutput> {
    @Override
    public GetCommentsOutput convert(Comment input) {
        log.info("Start converting from Comment to GetCommentsOutput with input: {}", input);

        GetCommentsOutput output = GetCommentsOutput.builder()
                .id(input.getId().toString())
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .content(input.getContent())
                .publishDate(input.getPublishDate())
                .lastEditedDate(input.getLastEditedDate())
                .lastEditedBy(input.getLastEditedBy())
                .build();

        log.info("End converting rom Comment to GetCommentsOutput with output: {}", output);
        return output;
    }
}
