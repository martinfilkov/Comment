package com.tinqinacademy.comment.core.services;

import com.tinqinacademy.comment.api.operations.system.deletecomment.DeleteCommentInput;
import com.tinqinacademy.comment.api.operations.system.deletecomment.DeleteCommentOutput;
import com.tinqinacademy.comment.api.operations.system.updatecomment.AdminUpdateCommentInput;
import com.tinqinacademy.comment.api.operations.system.updatecomment.AdminUpdateCommentOutput;

public interface SystemService {
    AdminUpdateCommentOutput adminUpdateComment(AdminUpdateCommentInput input);
    DeleteCommentOutput deleteComment(DeleteCommentInput input);
}
