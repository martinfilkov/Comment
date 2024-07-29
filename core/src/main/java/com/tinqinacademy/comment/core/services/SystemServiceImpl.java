package com.tinqinacademy.comment.core.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import com.tinqinacademy.comment.api.operations.exception.NotFoundException;
import com.tinqinacademy.comment.api.operations.system.deletecomment.DeleteCommentInput;
import com.tinqinacademy.comment.api.operations.system.deletecomment.DeleteCommentOutput;
import com.tinqinacademy.comment.api.operations.system.updatecomment.AdminUpdateCommentInput;
import com.tinqinacademy.comment.api.operations.system.updatecomment.AdminUpdateCommentOutput;
import com.tinqinacademy.comment.persistence.entities.Comment;
import com.tinqinacademy.comment.persistence.repositories.CommentRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class SystemServiceImpl implements SystemService {
    private final CommentRepository commentRepository;
    private final ConversionService conversionService;
    private final ObjectMapper objectMapper;

    @Autowired
    public SystemServiceImpl(CommentRepository commentRepository, ConversionService conversionService, ObjectMapper objectMapper) {
        this.commentRepository = commentRepository;
        this.conversionService = conversionService;
        this.objectMapper = objectMapper;
    }

    @SneakyThrows
    @Override
    public AdminUpdateCommentOutput adminUpdateComment(AdminUpdateCommentInput input) {
        log.info("Start updateComment input: {}", input);

        Optional<Comment> commentOptional = commentRepository.findById(UUID.fromString(input.getCommentId()));
        if (commentOptional.isEmpty()) {
            throw new NotFoundException(String.format("Comment with id %s not found", input.getCommentId()));
        }

        Comment inputComment = conversionService.convert(input, Comment.class);

        JsonNode currentNode = objectMapper.valueToTree(commentOptional.get());
        JsonNode inputNode = objectMapper.valueToTree(inputComment);

        JsonMergePatch patch = JsonMergePatch.fromJson(inputNode);
        Comment updatedComment = objectMapper.treeToValue(patch.apply(currentNode), Comment.class);
        commentRepository.save(updatedComment);

        AdminUpdateCommentOutput output = conversionService.convert(updatedComment, AdminUpdateCommentOutput.class);

        log.info("End updateComment output: {}", output);

        return output;
    }

    @Override
    public DeleteCommentOutput deleteComment(DeleteCommentInput input) {
        log.info("Start deleteComment input: {}", input);

        Comment comment = commentRepository.findById(UUID.fromString(input.getCommentId()))
                .orElseThrow(() -> new NotFoundException(String.format("Comment with id %s does not exist", input.getCommentId())));

        commentRepository.delete(comment);

        DeleteCommentOutput output = new DeleteCommentOutput();

        log.info("End deleteComment output: {}", output);

        return output;
    }
}
