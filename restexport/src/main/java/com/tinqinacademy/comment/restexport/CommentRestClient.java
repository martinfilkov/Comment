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

@Headers({"Content-Type: application/json"})
public interface CommentRestClient {
    // Hotel
    @RequestLine("GET /api/hotel/{roomId}/comment")
    GetCommentsOutputList getComments(@Param("roomId") String roomId);

    @RequestLine("POST /api/hotel/{roomId}/comment")
    PublishCommentOutput publishComment(@Param("roomId") String roomId, PublishCommentInput request);

    @RequestLine("PATCH /api/hotel/comment/{commentId}")
    ContentUpdateCommentOutput contentUpdate(@Param("commentId") String commentId, ContentUpdateCommentInput request);

    // System
    @RequestLine("PUT /api/system/comment/{commentId}")
    AdminUpdateCommentOutput adminUpdateComment(@Param("commentId") String commentId, AdminUpdateCommentInput request);

    @RequestLine("DELETE /api/system/comment/{commentId}")
    DeleteCommentOutput deleteComment(@Param("commentId") String commentId);
}
