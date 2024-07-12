package com.tinqinacademy.comment.core;

import com.tinqinacademy.comment.api.operations.system.deletecomment.DeleteCommentInput;
import com.tinqinacademy.comment.api.operations.system.deletecomment.DeleteCommentOutput;
import com.tinqinacademy.comment.api.operations.system.updatecomment.UpdateCommentInput;
import com.tinqinacademy.comment.api.operations.system.updatecomment.UpdateCommentOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SystemServiceImpl implements SystemService{
    @Override
    public UpdateCommentOutput updateComment(UpdateCommentInput input) {
        log.info("Start updateComment input: {}", input);

        UpdateCommentOutput output = UpdateCommentOutput.builder()
                .id(input.getCommentId())
                .build();

        log.info("End updateComment output: {}", output);

        return output;
    }

    @Override
    public DeleteCommentOutput deleteComment(DeleteCommentInput input) {
        log.info("Start deleteComment input: {}", input);

        DeleteCommentOutput output = new DeleteCommentOutput();

        log.info("End deleteComment output: {}", output);

        return output;
    }
}
