package com.tinqinacademy.comment.api.operations.hotel.partialupdatecomment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@ToString
public class PartialUpdateCommentInput {
    @JsonIgnore
    private String commentId;

    @NotBlank(message = "Content cannot be blank")
    private String content;
}
