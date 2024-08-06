package com.tinqinacademy.comment.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.comment.api.operations.base.CommentMappings;
import com.tinqinacademy.comment.api.operations.system.deletecomment.DeleteCommentInput;
import com.tinqinacademy.comment.api.operations.system.updatecomment.AdminUpdateCommentInput;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SystemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void givenUpdateInput_whenUpdateComment_thenReturnUpdateCommentId() throws Exception {
        AdminUpdateCommentInput input = AdminUpdateCommentInput.builder()
                .commentId("1")
                .firstName("Martin")
                .lastName("Filkov")
                .roomNumber("12B")
                .content("CONTENT")
                .build();

        String requestBody = objectMapper.writeValueAsString(input);

        mockMvc.perform(put(CommentMappings.ADMIN_UPDATE_COMMENT, input.getCommentId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").value("1"));

    }

    @Test
    public void givenDeleteInput_whenDeleteComment_thenDeleteComment() throws Exception{
        DeleteCommentInput input = DeleteCommentInput.builder()
                .commentId("1")
                .build();

        String requestBody = objectMapper.writeValueAsString(input);

        mockMvc.perform(delete(CommentMappings.DELETE_COMMENT, input.getCommentId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isAccepted());
    }
}