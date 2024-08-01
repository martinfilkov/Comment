package com.tinqinacademy.comment.api.operations.system.deletecomment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.comment.api.operations.base.OperationInput;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class DeleteCommentInput implements OperationInput {
    @JsonIgnore
    private String commentId;
}
