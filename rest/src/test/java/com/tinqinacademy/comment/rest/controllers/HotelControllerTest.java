package com.tinqinacademy.comment.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.comment.api.operations.base.URLMapping;
import com.tinqinacademy.comment.api.operations.hotel.partialupdatecomment.ContentUpdateCommentInput;
import com.tinqinacademy.comment.api.operations.hotel.publishcomment.PublishCommentInput;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class HotelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void givenRoomId_whenGetComments_thenReturnCommentsList() throws Exception {
        String roomId = "1";
        mockMvc.perform(get(URLMapping.GET_COMMENTS, roomId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comments").exists())
                .andExpect(jsonPath("$.comments[0].firstName").value("Martin"));
    }

    @Test
    public void givenCommentInput_whenPublishComment_thenReturnPublishCommentId() throws Exception {
        PublishCommentInput input = PublishCommentInput.builder()
                .roomId("1")
                .firstName("Martin")
                .lastName("Filkov")
                .content("No content")
                .build();

        String requestBody = objectMapper.writeValueAsString(input);

        mockMvc.perform(post(URLMapping.PUBLISH_COMMENT, input.getRoomId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    public void givenUpdateInput_whenPartialUpdateComment_thenReturnUpdateCommentId() throws Exception {
        ContentUpdateCommentInput input = ContentUpdateCommentInput.builder()
                .commentId("1")
                .content("CONTENT")
                .build();

        String requestBody = objectMapper.writeValueAsString(input);

        mockMvc.perform(patch(URLMapping.CONTENT_UPDATE_COMMENT, input.getCommentId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").value("1"));
    }
}