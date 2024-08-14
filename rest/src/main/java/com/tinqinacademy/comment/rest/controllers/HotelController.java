package com.tinqinacademy.comment.rest.controllers;

import com.tinqinacademy.comment.api.operations.base.Errors;
import com.tinqinacademy.comment.api.operations.base.CommentMappings;
import com.tinqinacademy.comment.api.operations.hotel.getcomments.GetCommentsInput;
import com.tinqinacademy.comment.api.operations.hotel.getcomments.GetCommentsOperation;
import com.tinqinacademy.comment.api.operations.hotel.getcomments.GetCommentsOutputList;
import com.tinqinacademy.comment.api.operations.hotel.partialupdatecomment.ContentUpdateCommentInput;
import com.tinqinacademy.comment.api.operations.hotel.partialupdatecomment.ContentUpdateCommentOperation;
import com.tinqinacademy.comment.api.operations.hotel.partialupdatecomment.ContentUpdateCommentOutput;
import com.tinqinacademy.comment.api.operations.hotel.publishcomment.PublishCommentInput;
import com.tinqinacademy.comment.api.operations.hotel.publishcomment.PublishCommentOperation;
import com.tinqinacademy.comment.api.operations.hotel.publishcomment.PublishCommentOutput;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class HotelController extends BaseController{
    private final GetCommentsOperation getCommentsOperation;
    private final PublishCommentOperation publishCommentOperation;
    private final ContentUpdateCommentOperation contentUpdateCommentOperation;


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned comments"),
            @ApiResponse(responseCode = "403", description = "User not authorized")
    })
    @GetMapping(CommentMappings.GET_COMMENTS)
    public ResponseEntity<?> getComments(@PathVariable("roomId") String id) {
        GetCommentsInput input = GetCommentsInput.builder()
                .roomId(id)
                .build();

        Either<Errors, GetCommentsOutputList> output = getCommentsOperation.process(input);
        return handleResponse(output, HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully published a comment"),
            @ApiResponse(responseCode = "403", description = "User not authorized")
    })
    @PostMapping(CommentMappings.PUBLISH_COMMENT)
    public ResponseEntity<?> publishComment(
            @PathVariable("roomId") String id,
            @RequestBody PublishCommentInput request) {
        PublishCommentInput input = request.toBuilder()
                .roomId(id)
                .build();

        Either<Errors, PublishCommentOutput> output = publishCommentOperation.process(input);

        return handleResponse(output, HttpStatus.CREATED);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated comment"),
            @ApiResponse(responseCode = "403", description = "User not authorized"),
            @ApiResponse(responseCode = "404", description = "Comment not found")
    })
    @PatchMapping(CommentMappings.CONTENT_UPDATE_COMMENT)
    public ResponseEntity<?> contentUpdate(
            @PathVariable("commentId") String id,
            @RequestBody ContentUpdateCommentInput request
    ) {
        ContentUpdateCommentInput input = request.toBuilder()
                .commentId(id)
                .build();

        Either<Errors, ContentUpdateCommentOutput> output = contentUpdateCommentOperation.process(input);

        return handleResponse(output, HttpStatus.OK);
    }
}
