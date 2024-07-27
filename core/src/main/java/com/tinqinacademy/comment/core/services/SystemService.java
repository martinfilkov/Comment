package com.tinqinacademy.comment.core.services;

import com.tinqinacademy.comment.api.operations.system.deletecomment.DeleteCommentInput;
import com.tinqinacademy.comment.api.operations.system.deletecomment.DeleteCommentOutput;
import com.tinqinacademy.comment.api.operations.system.updatecomment.UpdateCommentInput;
import com.tinqinacademy.comment.api.operations.system.updatecomment.UpdateCommentOutput;

public interface SystemService {
    UpdateCommentOutput updateComment(UpdateCommentInput input);
    DeleteCommentOutput deleteComment(DeleteCommentInput input);
}
