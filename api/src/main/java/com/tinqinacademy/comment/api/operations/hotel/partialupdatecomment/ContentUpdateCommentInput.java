package com.tinqinacademy.comment.api.operations.hotel.partialupdatecomment;

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
public class ContentUpdateCommentInput implements OperationInput {
    @NotBlank(message = "commentId cannot be blank")
    @JsonIgnore
    private String commentId;

    @NotBlank(message = "Content cannot be blank")
    private String content;

    @NotBlank(message = "User id cannot be blank")
    private String userId;
}
