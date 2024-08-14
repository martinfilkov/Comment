package com.tinqinacademy.comment.api.operations.hotel.publishcomment;

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
public class PublishCommentInput implements OperationInput {
    @NotBlank(message = "roomId cannot be blank")
    @JsonIgnore
    private String roomId;

    @NotBlank(message = "Content cannot be blank")
    private String content;

    @NotBlank(message = "User id cannot be blank")
    private String userId;
}
