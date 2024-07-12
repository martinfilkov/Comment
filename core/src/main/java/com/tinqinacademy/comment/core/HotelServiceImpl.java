package com.tinqinacademy.comment.core;

import com.tinqinacademy.comment.api.operations.hotel.getcomments.GetCommentsInput;
import com.tinqinacademy.comment.api.operations.hotel.getcomments.GetCommentsOutput;
import com.tinqinacademy.comment.api.operations.hotel.getcomments.GetCommentsOutputList;
import com.tinqinacademy.comment.api.operations.hotel.partialupdatecomment.PartialUpdateCommentInput;
import com.tinqinacademy.comment.api.operations.hotel.partialupdatecomment.PartialUpdateCommentOutput;
import com.tinqinacademy.comment.api.operations.hotel.publishcomment.PublishCommentInput;
import com.tinqinacademy.comment.api.operations.hotel.publishcomment.PublishCommentOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class HotelServiceImpl implements HotelService{
    @Override
    public GetCommentsOutputList getComments(GetCommentsInput input) {
        log.info("Start getComments input: {}", input);

        GetCommentsOutput singleOutput = GetCommentsOutput.builder()
                .id(input.getRoomId())
                .firstName("Martin")
                .lastName("Filkov")
                .content("Nice")
                .publishDate(LocalDate.now())
                .lastEditedBy(LocalDate.now())
                .lastEditedDate(LocalDate.now())
                .build();

        GetCommentsOutputList output = GetCommentsOutputList.builder()
                .comments(List.of(singleOutput))
                .build();

        log.info("End getRoomIds output: {}", output);

        return output;
    }

    @Override
    public PublishCommentOutput publishComment(PublishCommentInput input) {
        log.info("Start publishComment input: {}", input);

        PublishCommentOutput output = PublishCommentOutput.builder()
                .id(input.getRoomId())
                .build();

        log.info("End publishComment output: {}", output);

        return output;
    }

    @Override
    public PartialUpdateCommentOutput partialUpdateComment(PartialUpdateCommentInput input) {
        log.info("Start partialUpdateComment input: {}", input);

        PartialUpdateCommentOutput output = PartialUpdateCommentOutput.builder()
                .id(input.getCommentId())
                .build();

        log.info("End partialUpdateComment output: {}", output);

        return output;
    }
}
