package com.tinqinacademy.comment.api.operations.system.updatecomment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.comment.api.operations.base.OperationInput;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@ToString
public class AdminUpdateCommentInput implements OperationInput {
    @JsonIgnore
    private String commentId;
    private String roomNumber;
    private String firstName;
    private String lastName;
    private String content;
}
