package com.tinqinacademy.comment.core.services;

import com.tinqinacademy.comment.api.operations.hotel.getcomments.GetCommentsInput;
import com.tinqinacademy.comment.api.operations.hotel.getcomments.GetCommentsOutputList;
import com.tinqinacademy.comment.api.operations.hotel.partialupdatecomment.PartialUpdateCommentInput;
import com.tinqinacademy.comment.api.operations.hotel.partialupdatecomment.PartialUpdateCommentOutput;
import com.tinqinacademy.comment.api.operations.hotel.publishcomment.PublishCommentInput;
import com.tinqinacademy.comment.api.operations.hotel.publishcomment.PublishCommentOutput;

public interface HotelService {
    GetCommentsOutputList getComments(GetCommentsInput input);
    PublishCommentOutput publishComment(PublishCommentInput input);
    PartialUpdateCommentOutput partialUpdateComment(PartialUpdateCommentInput input);
}
