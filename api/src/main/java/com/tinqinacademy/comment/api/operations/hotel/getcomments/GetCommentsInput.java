package com.tinqinacademy.comment.api.operations.hotel.getcomments;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.comment.api.operations.base.OperationInput;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class GetCommentsInput implements OperationInput {
    @NotBlank(message = "roomId cannot be blank")
    @JsonIgnore
    private String roomId;
}
