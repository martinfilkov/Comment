package com.tinqinacademy.comment.rest.hotel;

import com.tinqinacademy.comment.api.operations.base.CommentMappings;
import com.tinqinacademy.comment.persistence.entities.Comment;
import com.tinqinacademy.comment.persistence.repositories.CommentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PublishCommentIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentRepository commentRepository;

    @AfterEach
    public void cleanComments(){
        this.commentRepository.deleteAll();
    }

    @Test
    public void testPublishComment_success() throws Exception {
        UUID roomId = UUID.randomUUID();
        UUID commentId = UUID.randomUUID();

        String input = """
                {
                  "content": "test",
                  "userId": "00000000-0000-0000-0000-00000000000"
                }
                """;

        Comment comment = Comment.builder()
                .id(commentId)
                .userId(UUID.randomUUID())
                .roomId(roomId)
                .content("test")
                .lastEditedDate(LocalDateTime.now())
                .lastEditedBy(UUID.randomUUID())
                .build();

       when(commentRepository.save(any(Comment.class)))
               .thenReturn(comment);

       mockMvc.perform(post(CommentMappings.PUBLISH_COMMENT, roomId.toString())
                       .content(input)
                       .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.id").value(commentId.toString()));
    }
}
