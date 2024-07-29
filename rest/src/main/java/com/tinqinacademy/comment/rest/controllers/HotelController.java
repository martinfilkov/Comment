package com.tinqinacademy.comment.rest.controllers;

import com.tinqinacademy.comment.api.operations.hotel.getcomments.GetCommentsInput;
import com.tinqinacademy.comment.api.operations.hotel.getcomments.GetCommentsOutputList;
import com.tinqinacademy.comment.api.operations.hotel.partialupdatecomment.ContentUpdateCommentInput;
import com.tinqinacademy.comment.api.operations.hotel.partialupdatecomment.ContentUpdateCommentOutput;
import com.tinqinacademy.comment.api.operations.hotel.publishcomment.PublishCommentInput;
import com.tinqinacademy.comment.api.operations.hotel.publishcomment.PublishCommentOutput;
import com.tinqinacademy.comment.core.services.HotelService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class HotelController {
    private final HotelService hotelService;

    @Autowired
    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned comments"),
            @ApiResponse(responseCode = "403", description = "User not authorized")
    })
    @GetMapping(URLMapping.GET_COMMENTS)
    public ResponseEntity<GetCommentsOutputList> getComments(@PathVariable("roomId") String id){
        GetCommentsInput input = GetCommentsInput.builder()
                .roomId(id)
                .build();

        GetCommentsOutputList output = hotelService.getComments(input);

        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully published a comment"),
            @ApiResponse(responseCode = "403", description = "User not authorized")
    })
    @PostMapping(URLMapping.PUBLISH_COMMENT)
    public ResponseEntity<PublishCommentOutput> publishComment(
            @PathVariable("roomId") String id,
            @Valid @RequestBody PublishCommentInput request){
        PublishCommentInput input = request.toBuilder()
                .roomId(id)
                .build();

        PublishCommentOutput output = hotelService.publishComment(input);

        return new ResponseEntity<>(output, HttpStatus.CREATED);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated comment"),
            @ApiResponse(responseCode = "403", description = "User not authorized"),
            @ApiResponse(responseCode = "404", description = "Comment not found")
    })
    @PutMapping(URLMapping.CONTENT_UPDATE_COMMENT)
    public ResponseEntity<ContentUpdateCommentOutput> contentUpdate(
            @PathVariable("commentId") String id,
            @Valid @RequestBody ContentUpdateCommentInput request
            ){
        ContentUpdateCommentInput input = request.toBuilder()
                .commentId(id)
                .build();

        ContentUpdateCommentOutput output = hotelService.contentUpdateComment(input);

        return new ResponseEntity<>(output, HttpStatus.OK);
    }
}
