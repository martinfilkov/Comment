package com.tinqinacademy.comment.api.operations.hotel.getcomments;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class GetCommentsOutput {
    private String id;
    private String firstName;
    private String lastName;
    private String content;
    private LocalDateTime publishDate;
    private LocalDateTime lastEditedDate;
    private UUID lastEditedBy;
}
