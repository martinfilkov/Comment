package com.tinqinacademy.comment.api.operations.system.updatecomment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@ToString
public class AdminUpdateCommentInput {
    @JsonIgnore
    private String commentId;

    @NotBlank(message = "Room cannot be blank")
    private String roomNumber;

    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @NotBlank(message = "Content cannot be blank")
    private String content;
}
