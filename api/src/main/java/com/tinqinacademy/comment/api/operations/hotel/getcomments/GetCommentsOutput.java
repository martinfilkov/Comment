package com.tinqinacademy.comment.api.operations.hotel.getcomments;

import com.tinqinacademy.comment.api.operations.base.OperationOutput;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class GetCommentsOutput implements OperationOutput {
    private String id;
    private String userId;
    private String content;
    private LocalDateTime publishDate;
    private LocalDateTime lastEditedDate;
    private UUID lastEditedBy;
}
