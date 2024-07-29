package com.tinqinacademy.comment.rest.controllers;

import com.tinqinacademy.comment.api.operations.system.deletecomment.DeleteCommentInput;
import com.tinqinacademy.comment.api.operations.system.deletecomment.DeleteCommentOutput;
import com.tinqinacademy.comment.api.operations.system.updatecomment.AdminUpdateCommentInput;
import com.tinqinacademy.comment.api.operations.system.updatecomment.AdminUpdateCommentOutput;
import com.tinqinacademy.comment.core.services.SystemService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SystemController {
    private final SystemService systemService;

    @Autowired
    public SystemController(SystemService systemService) {
        this.systemService = systemService;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated comment"),
            @ApiResponse(responseCode = "403", description = "User not authorized"),
            @ApiResponse(responseCode = "404", description = "Comment not found")
    })
    @PatchMapping(URLMapping.ADMIN_UPDATE_COMMENT)
    public ResponseEntity<AdminUpdateCommentOutput> adminUpdateComment(
            @PathVariable("commentId") String id,
            @Valid @RequestBody AdminUpdateCommentInput request
            ){
        AdminUpdateCommentInput input = request.toBuilder()
                .commentId(id)
                .build();

        AdminUpdateCommentOutput output = systemService.adminUpdateComment(input);

        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Successfully deleted comment"),
            @ApiResponse(responseCode = "403", description = "User not authorized"),
            @ApiResponse(responseCode = "404", description = "Comment not found")
    })
    @DeleteMapping(URLMapping.DELETE_COMMENT)
    public ResponseEntity<DeleteCommentOutput> deleteComment(@PathVariable("commentId") String id){
        DeleteCommentInput input = DeleteCommentInput.builder()
                .commentId(id)
                .build();

        DeleteCommentOutput output = systemService.deleteComment(input);

        return new ResponseEntity<>(output, HttpStatus.ACCEPTED);
    }
}
