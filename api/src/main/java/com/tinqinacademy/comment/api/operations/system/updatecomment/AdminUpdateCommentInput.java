package com.tinqinacademy.comment.api.operations.system.updatecomment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.comment.api.operations.base.OperationInput;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

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

    @UUID(message = "Room id must cover the UUID syntax")
    @NotBlank(message = "Room id not provided")
    private String roomId;

    @NotBlank(message = "Content cannot be blank")
    private String content;

    @UUID(message = "User id must cover the UUID syntax")
    @NotBlank(message = "User id cannot be blank")
    private String userId;
}
