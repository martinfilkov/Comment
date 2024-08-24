package com.tinqinacademy.comment.rest.system;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.comment.api.operations.base.CommentMappings;
import com.tinqinacademy.comment.persistence.entities.Comment;
import com.tinqinacademy.comment.persistence.repositories.CommentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class DeleteCommentIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CommentRepository commentRepository;

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
    public void cleanComments() {
        this.commentRepository.deleteAll();
    }

    @Test
    public void testDeleteComment_success() throws Exception {
        MvcResult mvcResult = mockMvc.perform(delete(CommentMappings.DELETE_COMMENT, savedComment.getId().toString()))
                .andExpect(status().isAccepted())
                .andReturn();

        Assertions.assertEquals(0, commentRepository.findAll().size());
    }

    @Test
    public void return_404_comment_not_found() throws Exception {
        mockMvc.perform(delete(CommentMappings.DELETE_COMMENT, UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }
}
