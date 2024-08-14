package com.tinqinacademy.comment.rest.controllers;

import com.tinqinacademy.comment.api.operations.base.Errors;
import com.tinqinacademy.comment.api.operations.base.CommentMappings;
import com.tinqinacademy.comment.api.operations.system.deletecomment.DeleteCommentInput;
import com.tinqinacademy.comment.api.operations.system.deletecomment.DeleteCommentOperation;
import com.tinqinacademy.comment.api.operations.system.deletecomment.DeleteCommentOutput;
import com.tinqinacademy.comment.api.operations.system.updatecomment.AdminUpdateCommentInput;
import com.tinqinacademy.comment.api.operations.system.updatecomment.AdminUpdateCommentOperation;
import com.tinqinacademy.comment.api.operations.system.updatecomment.AdminUpdateCommentOutput;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SystemController extends BaseController{
    private final AdminUpdateCommentOperation adminUpdateCommentOperation;
    private final DeleteCommentOperation deleteCommentOperation;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated comment"),
            @ApiResponse(responseCode = "403", description = "User not authorized"),
            @ApiResponse(responseCode = "404", description = "Comment not found")
    })
    @PutMapping(CommentMappings.ADMIN_UPDATE_COMMENT)
    public ResponseEntity<?> adminUpdateComment(
            @PathVariable("commentId") String id,
            @RequestBody AdminUpdateCommentInput request
    ) {
        AdminUpdateCommentInput input = request.toBuilder()
                .commentId(id)
                .build();

        Either<Errors, AdminUpdateCommentOutput> output = adminUpdateCommentOperation.process(input);

        return handleResponse(output, HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Successfully deleted comment"),
            @ApiResponse(responseCode = "403", description = "User not authorized"),
            @ApiResponse(responseCode = "404", description = "Comment not found")
    })
    @DeleteMapping(CommentMappings.DELETE_COMMENT)
    public ResponseEntity<?> deleteComment(@PathVariable("commentId") String id) {
        DeleteCommentInput input = DeleteCommentInput.builder()
                .commentId(id)
                .build();

        Either<Errors, DeleteCommentOutput> output = deleteCommentOperation.process(input);

        return handleResponse(output, HttpStatus.ACCEPTED);
    }
}
