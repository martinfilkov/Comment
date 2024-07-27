package com.tinqinacademy.comment.core.services;

import com.tinqinacademy.comment.api.operations.exception.NotFoundException;
import com.tinqinacademy.comment.api.operations.hotel.getcomments.GetCommentsInput;
import com.tinqinacademy.comment.api.operations.hotel.getcomments.GetCommentsOutput;
import com.tinqinacademy.comment.api.operations.hotel.getcomments.GetCommentsOutputList;
import com.tinqinacademy.comment.api.operations.hotel.partialupdatecomment.PartialUpdateCommentInput;
import com.tinqinacademy.comment.api.operations.hotel.partialupdatecomment.PartialUpdateCommentOutput;
import com.tinqinacademy.comment.api.operations.hotel.publishcomment.PublishCommentInput;
import com.tinqinacademy.comment.api.operations.hotel.publishcomment.PublishCommentOutput;
import com.tinqinacademy.comment.persistence.entities.Comment;
import com.tinqinacademy.comment.persistence.repositories.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class HotelServiceImpl implements HotelService {
    private final CommentRepository commentRepository;
    private final ConversionService conversionService;

    @Autowired
    public HotelServiceImpl(CommentRepository commentRepository, ConversionService conversionService) {
        this.commentRepository = commentRepository;
        this.conversionService = conversionService;
    }

    @Override
    public GetCommentsOutputList getComments(GetCommentsInput input) {
        log.info("Start getComments input: {}", input);

        List<GetCommentsOutput> outputList = commentRepository.findByRoomId(UUID.fromString(input.getRoomId())).stream()
                .map(comment -> conversionService.convert(comment, GetCommentsOutput.class))
                .toList();


        GetCommentsOutputList output = GetCommentsOutputList.builder()
                .comments(outputList)
                .build();

        log.info("End getRoomIds output: {}", output);

        return output;
    }

    @Override
    public PublishCommentOutput publishComment(PublishCommentInput input) {
        log.info("Start publishComment input: {}", input);

        Comment commentToSave = conversionService.convert(input, Comment.class);

        Comment savedComment = commentRepository.save(commentToSave);

        PublishCommentOutput output = conversionService.convert(savedComment, PublishCommentOutput.class);

        log.info("End publishComment output: {}", output);

        return output;
    }

    @Override
    public PartialUpdateCommentOutput partialUpdateComment(PartialUpdateCommentInput input) {
        log.info("Start partialUpdateComment input: {}", input);

        Comment comment = commentRepository.findById(UUID.fromString(input.getCommentId()))
                .orElseThrow(() -> new NotFoundException(String.format("Comment with id %s does not exist", input.getCommentId())));

        comment.setContent(input.getContent());

        commentRepository.save(comment);

        PartialUpdateCommentOutput output = PartialUpdateCommentOutput.builder()
                .id(input.getCommentId())
                .build();

        log.info("End partialUpdateComment output: {}", output);

        return output;
    }
}
