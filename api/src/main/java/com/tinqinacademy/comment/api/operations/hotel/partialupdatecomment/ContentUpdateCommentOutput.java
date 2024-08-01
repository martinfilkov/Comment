package com.tinqinacademy.comment.api.operations.hotel.partialupdatecomment;

import com.tinqinacademy.comment.api.operations.base.OperationOutput;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ContentUpdateCommentOutput implements OperationOutput {
    private String id;
}
