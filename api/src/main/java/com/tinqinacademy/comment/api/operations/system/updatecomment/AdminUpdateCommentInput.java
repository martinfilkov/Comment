package com.tinqinacademy.comment.api.operations.system.updatecomment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.comment.api.operations.base.OperationInput;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@ToString
public class AdminUpdateCommentInput implements OperationInput {
    @NotBlank(message = "commentId cannot be blank")
    @JsonIgnore
    private String commentId;
    private String roomId;
    private String content;
    private String userId;
}
