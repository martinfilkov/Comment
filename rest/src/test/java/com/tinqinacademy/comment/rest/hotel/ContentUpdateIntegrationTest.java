package com.tinqinacademy.comment.rest.hotel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.comment.api.operations.base.CommentMappings;
import com.tinqinacademy.comment.api.operations.hotel.partialupdatecomment.ContentUpdateCommentInput;
import com.tinqinacademy.comment.persistence.entities.Comment;
import com.tinqinacademy.comment.persistence.repositories.CommentRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ContentUpdateIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Comment savedComment;

    @BeforeEach
    public void createComment() {
        Comment comment = Comment.builder()
                .userId(UUID.fromString("7f84cc8b-7d98-4d2d-9bd2-7c8b023f77b8"))
                .roomId(UUID.randomUUID())
                .content("test")
                .lastEditedDate(LocalDateTime.now())
                .lastEditedBy(UUID.randomUUID())
                .build();
        savedComment = commentRepository.save(comment);
    }

    @AfterEach
    public void cleanComments(){
        this.commentRepository.deleteAll();
    }

    @Test
    public void testContentUpdate_success() throws Exception {
        ContentUpdateCommentInput contentInput = ContentUpdateCommentInput.builder()
                .commentId(savedComment.getId().toString())
                .content("test123123")
                .userId("7f84cc8b-7d98-4d2d-9bd2-7c8b023f77b8")
                .build();

        String input = objectMapper.writeValueAsString(contentInput);

        MvcResult mvcResult = mockMvc.perform(patch(CommentMappings.CONTENT_UPDATE_COMMENT, savedComment.getId().toString())
                        .content(input)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Comment updatedComment = commentRepository.findById(savedComment.getId())
                .orElseThrow();

        Assertions.assertEquals("test123123", updatedComment.getContent());
    }

    @SneakyThrows
    @Test
    public void return_404_comment_not_found() {
        ContentUpdateCommentInput contentInput = ContentUpdateCommentInput.builder()
                .commentId(savedComment.getId().toString())
                .content("test123123")
                .userId("7f84cc8b-7d98-4d2d-9bd2-7c8b023f77b8")
                .build();

        String input = objectMapper.writeValueAsString(contentInput);

        mockMvc.perform(patch(CommentMappings.CONTENT_UPDATE_COMMENT, UUID.randomUUID())
                        .content(input)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @SneakyThrows
    @ParameterizedTest
    @ValueSource(strings = {"", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"})
    public void return_bad_request_content_invalid(String content) {
        ContentUpdateCommentInput contentInput = ContentUpdateCommentInput.builder()
                .commentId(savedComment.getId().toString())
                .content(content)
                .userId("7f84cc8b-7d98-4d2d-9bd2-7c8b023f77b8")
                .build();

        String input = objectMapper.writeValueAsString(contentInput);

        mockMvc.perform(patch(CommentMappings.CONTENT_UPDATE_COMMENT, savedComment.getId().toString())
                        .content(input)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
