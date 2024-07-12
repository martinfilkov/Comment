package com.tinqinacademy.comment.api.operations.system.deletecomment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class DeleteCommentInput {
    @JsonIgnore
    private String commentId;
}
