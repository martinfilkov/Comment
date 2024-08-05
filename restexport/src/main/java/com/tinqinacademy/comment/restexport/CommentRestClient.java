package com.tinqinacademy.comment.restexport;

import com.tinqinacademy.comment.api.operations.base.URLMapping;
import com.tinqinacademy.comment.api.operations.hotel.getcomments.GetCommentsOutputList;
import com.tinqinacademy.comment.api.operations.hotel.partialupdatecomment.ContentUpdateCommentInput;
import com.tinqinacademy.comment.api.operations.hotel.partialupdatecomment.ContentUpdateCommentOutput;
import com.tinqinacademy.comment.api.operations.hotel.publishcomment.PublishCommentInput;
import com.tinqinacademy.comment.api.operations.hotel.publishcomment.PublishCommentOutput;
import com.tinqinacademy.comment.api.operations.system.deletecomment.DeleteCommentOutput;
import com.tinqinacademy.comment.api.operations.system.updatecomment.AdminUpdateCommentInput;
import com.tinqinacademy.comment.api.operations.system.updatecomment.AdminUpdateCommentOutput;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient("comment-service")
public interface CommentRestClient {
    //Hotel
    @GetMapping(URLMapping.GET_COMMENTS)
    ResponseEntity<GetCommentsOutputList> getComments(@Param("id") String id);

    @PostMapping(URLMapping.PUBLISH_COMMENT)
    ResponseEntity<PublishCommentOutput> publishComment(@Param("id") String id, PublishCommentInput request);

    @PutMapping(URLMapping.CONTENT_UPDATE_COMMENT)
    ResponseEntity<ContentUpdateCommentOutput> contentUpdate(@Param("id") String id, ContentUpdateCommentInput request);

    //System
    @PatchMapping(URLMapping.ADMIN_UPDATE_COMMENT)
    ResponseEntity<AdminUpdateCommentOutput> adminUpdateComment(@Param("id") String id, AdminUpdateCommentInput request);

    @DeleteMapping(URLMapping.DELETE_COMMENT)
    ResponseEntity<DeleteCommentOutput> deleteComment(@Param("id") String id);
}
