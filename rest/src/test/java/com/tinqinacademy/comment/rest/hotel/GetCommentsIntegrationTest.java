package com.tinqinacademy.comment.rest.hotel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.comment.api.operations.base.CommentMappings;
import com.tinqinacademy.comment.persistence.entities.Comment;
import com.tinqinacademy.comment.persistence.repositories.CommentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class GetCommentsIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Comment savedComment;

    private UUID roomId;

    @BeforeEach
    public void createComment() {
        roomId = UUID.randomUUID();
        Comment comment = Comment.builder()
                .userId(UUID.fromString("7f84cc8b-7d98-4d2d-9bd2-7c8b023f77b8"))
                .roomId(roomId)
                .content("test")
                .lastEditedDate(LocalDateTime.now())
                .lastEditedBy(UUID.randomUUID())
                .build();
        savedComment = commentRepository.save(comment);
    }

    @AfterEach
    public void cleanComments() {
        this.commentRepository.deleteAll();
    }

    @Test
    public void testGetComments_success() throws Exception {
        mockMvc.perform(get(CommentMappings.GET_COMMENTS, roomId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comments").isArray())
                .andExpect(jsonPath("$.comments[0].content").value(savedComment.getContent()));
    }
}
