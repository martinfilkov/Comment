package com.tinqinacademy.comment.api.operations.hotel.getcomments;

import com.tinqinacademy.comment.api.operations.base.OperationOutput;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class GetCommentsOutputList implements OperationOutput {
    List<GetCommentsOutput> comments;
}
