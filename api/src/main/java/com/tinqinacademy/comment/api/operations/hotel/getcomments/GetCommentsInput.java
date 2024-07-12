package com.tinqinacademy.comment.api.operations.hotel.getcomments;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class GetCommentsInput {
    @JsonIgnore
    private String roomId;
}
