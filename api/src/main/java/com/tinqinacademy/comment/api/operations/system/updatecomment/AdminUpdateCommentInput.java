package com.tinqinacademy.comment.api.operations.system.updatecomment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.comment.api.operations.base.OperationInput;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @UUID(message = "UUID syntax required")
    private String commentId;

    @NotBlank(message = "Room id not provided")
    @UUID(message = "UUID syntax required")
    private String roomId;

    @NotBlank(message = "Content cannot be blank")
    @Size(max = 50)
    private String content;

    @NotBlank(message = "User id cannot be blank")
    @UUID(message = "UUID syntax required")
    private String userId;
}
