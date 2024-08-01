package com.tinqinacademy.comment.core.operations.system;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import com.tinqinacademy.comment.api.operations.base.Errors;
import com.tinqinacademy.comment.api.operations.exception.NotFoundException;
import com.tinqinacademy.comment.api.operations.system.updatecomment.AdminUpdateCommentInput;
import com.tinqinacademy.comment.api.operations.system.updatecomment.AdminUpdateCommentOperation;
import com.tinqinacademy.comment.api.operations.system.updatecomment.AdminUpdateCommentOutput;
import com.tinqinacademy.comment.core.operations.BaseOperationProcessor;
import com.tinqinacademy.comment.core.utils.ErrorMapper;
import com.tinqinacademy.comment.persistence.entities.Comment;
import com.tinqinacademy.comment.persistence.repositories.CommentRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;

@Slf4j
@Service
public class AdminUpdateCommentOperationProcessor extends BaseOperationProcessor implements AdminUpdateCommentOperation {
    private final CommentRepository commentRepository;
    private final ObjectMapper mapper;

    public AdminUpdateCommentOperationProcessor(ConversionService conversionService,
                                                Validator validator,
                                                ErrorMapper errorMapper, CommentRepository commentRepository, ObjectMapper mapper) {
        super(conversionService, validator, errorMapper);
        this.commentRepository = commentRepository;
        this.mapper = mapper;
    }

    @Override
    public Either<Errors, AdminUpdateCommentOutput> process(AdminUpdateCommentInput input) {
        return validateInput(input)
                .flatMap(validated -> adminUpdateComment(input));
    }

    public Either<Errors, AdminUpdateCommentOutput> adminUpdateComment(AdminUpdateCommentInput input) {
        return Try.of(() -> {
                    log.info("Start updateComment input: {}", input);

                    Comment comment = getCommentIfValid(input);

                    Comment inputComment = conversionService.convert(input, Comment.class);

                    JsonNode currentNode = mapper.valueToTree(comment);
                    JsonNode inputNode = mapper.valueToTree(inputComment);

                    JsonMergePatch patch = JsonMergePatch.fromJson(inputNode);
                    Comment updatedComment = mapper.treeToValue(patch.apply(currentNode), Comment.class);
                    commentRepository.save(updatedComment);

                    AdminUpdateCommentOutput output = conversionService.convert(updatedComment, AdminUpdateCommentOutput.class);

                    log.info("End updateComment output: {}", output);

                    return output;
                })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        Case($(instanceOf(NotFoundException.class)), ex -> errorMapper.handleError(ex, HttpStatus.NOT_FOUND)),
                        Case($(), ex -> errorMapper.handleError(ex, HttpStatus.BAD_REQUEST))
                ));
    }

    private Comment getCommentIfValid(AdminUpdateCommentInput input) {
        Optional<Comment> commentOptional = commentRepository.findById(UUID.fromString(input.getCommentId()));
        if (commentOptional.isEmpty()) {
            throw new NotFoundException(String.format("Comment with id %s not found", input.getCommentId()));
        }
        return commentOptional.get();
    }
}
