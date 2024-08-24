package com.tinqinacademy.comment.api.operations.system.deletecomment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.comment.api.operations.base.OperationInput;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class DeleteCommentInput implements OperationInput {
    @NotBlank(message = "comment cannot be blank")
    @JsonIgnore
    @UUID(message = "UUID syntax required")
    private String commentId;
}
