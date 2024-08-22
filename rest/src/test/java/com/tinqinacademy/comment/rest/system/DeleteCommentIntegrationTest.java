package com.tinqinacademy.comment.rest.system;

import com.tinqinacademy.comment.api.operations.base.CommentMappings;
import com.tinqinacademy.comment.persistence.entities.Comment;
import com.tinqinacademy.comment.persistence.repositories.CommentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

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

    @MockBean
    private CommentRepository commentRepository;

    @Test
    public void testDeleteComment_success() throws Exception {
        UUID commentId = UUID.randomUUID();

        Comment comment = Comment.builder()
                .id(commentId)
                .userId(UUID.fromString("7f84cc8b-7d98-4d2d-9bd2-7c8b023f77b8"))
                .roomId(UUID.randomUUID())
                .content("test")
                .lastEditedDate(LocalDateTime.now())
                .lastEditedBy(UUID.randomUUID())
                .build();

        when(commentRepository.findById(any(UUID.class)))
                .thenReturn(Optional.of(comment));

        mockMvc.perform(delete(CommentMappings.DELETE_COMMENT, commentId))
                .andExpect(status().isAccepted());
    }
}
