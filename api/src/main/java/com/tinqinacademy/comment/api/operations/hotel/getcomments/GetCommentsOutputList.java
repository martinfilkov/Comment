package com.tinqinacademy.comment.api.operations.hotel.getcomments;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class GetCommentsOutputList {
    List<GetCommentsOutput> comments;
}
