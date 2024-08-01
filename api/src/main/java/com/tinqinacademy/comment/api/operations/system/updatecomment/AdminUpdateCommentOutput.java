package com.tinqinacademy.comment.api.operations.system.updatecomment;

import com.tinqinacademy.comment.api.operations.base.OperationOutput;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class AdminUpdateCommentOutput implements OperationOutput {
    private String id;
}
