package com.tinqinacademy.comment.api.operations.hotel.publishcomment;

import com.tinqinacademy.comment.api.operations.base.OperationOutput;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class PublishCommentOutput implements OperationOutput {
    private String id;
}
