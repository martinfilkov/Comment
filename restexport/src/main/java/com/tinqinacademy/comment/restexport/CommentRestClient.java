package com.tinqinacademy.comment.restexport;

import com.tinqinacademy.comment.api.operations.hotel.getcomments.GetCommentsOutputList;
import com.tinqinacademy.comment.api.operations.hotel.partialupdatecomment.ContentUpdateCommentInput;
import com.tinqinacademy.comment.api.operations.hotel.partialupdatecomment.ContentUpdateCommentOutput;
import com.tinqinacademy.comment.api.operations.hotel.publishcomment.PublishCommentInput;
import com.tinqinacademy.comment.api.operations.hotel.publishcomment.PublishCommentOutput;
import com.tinqinacademy.comment.api.operations.system.deletecomment.DeleteCommentOutput;
import com.tinqinacademy.comment.api.operations.system.updatecomment.AdminUpdateCommentInput;
import com.tinqinacademy.comment.api.operations.system.updatecomment.AdminUpdateCommentOutput;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.http.ResponseEntity;

@Headers({"Content-Type: application/json"})
public interface CommentRestClient {
    // Hotel
    @RequestLine("GET /api/hotel/comments/{id}")
    ResponseEntity<GetCommentsOutputList> getComments(@Param("id") String id);

    @RequestLine("POST /api/hotel/comments/{id}")
    ResponseEntity<PublishCommentOutput> publishComment(@Param("id") String id, PublishCommentInput request);

    @RequestLine("PUT /api/hotel/comments/{id}")
    ResponseEntity<ContentUpdateCommentOutput> contentUpdate(@Param("id") String id, ContentUpdateCommentInput request);

    // System
    @RequestLine("PATCH /api/system/comments/{id}")
    @Headers({"Content-Type: application/json-patch+json"})
    ResponseEntity<AdminUpdateCommentOutput> adminUpdateComment(@Param("id") String id, AdminUpdateCommentInput request);

    @RequestLine("DELETE /api/system/comments/{id}")
    ResponseEntity<DeleteCommentOutput> deleteComment(@Param("id") String id);
}
