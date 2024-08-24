package com.tinqinacademy.comment.rest.hotel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.comment.api.operations.base.CommentMappings;
import com.tinqinacademy.comment.api.operations.hotel.publishcomment.PublishCommentInput;
import com.tinqinacademy.comment.persistence.entities.Comment;
import com.tinqinacademy.comment.persistence.repositories.CommentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PublishCommentIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    public ObjectMapper objectMapper;

    @AfterEach
    public void cleanComments() {
        this.commentRepository.deleteAll();
    }

    @Test
    public void testPublishComment_success() throws Exception {
        UUID roomId = UUID.randomUUID();

        PublishCommentInput publishInput = PublishCommentInput.builder()
                .content("test")
                .roomId(roomId.toString())
                .userId(UUID.randomUUID().toString())
                .build();

        String input = objectMapper.writeValueAsString(publishInput);

        MvcResult mvcResult = mockMvc.perform(post(CommentMappings.PUBLISH_COMMENT, roomId.toString())
                        .content(input)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        Comment first = commentRepository.findAll().getFirst();

        Assertions.assertEquals("test", first.getContent());
    }


    @ParameterizedTest
    @ValueSource(strings = {"", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"})
    public void return_400_for_bad_content(String content) throws Exception {
        UUID roomId = UUID.randomUUID();

        PublishCommentInput publishInput = PublishCommentInput.builder()
                .content(content)
                .roomId(roomId.toString())
                .userId(UUID.randomUUID().toString())
                .build();

        String input = objectMapper.writeValueAsString(publishInput);

        mockMvc.perform(post(CommentMappings.PUBLISH_COMMENT, roomId.toString())
                        .content(input)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
