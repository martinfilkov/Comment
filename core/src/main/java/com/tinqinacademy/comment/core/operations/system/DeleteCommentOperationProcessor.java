package com.tinqinacademy.comment.core.operations.system;

import com.tinqinacademy.comment.api.operations.base.Errors;
import com.tinqinacademy.comment.api.operations.exception.NotFoundException;
import com.tinqinacademy.comment.api.operations.system.deletecomment.DeleteCommentInput;
import com.tinqinacademy.comment.api.operations.system.deletecomment.DeleteCommentOperation;
import com.tinqinacademy.comment.api.operations.system.deletecomment.DeleteCommentOutput;
import com.tinqinacademy.comment.core.operations.BaseOperationProcessor;
import com.tinqinacademy.comment.core.utils.ErrorMapper;
import com.tinqinacademy.comment.persistence.entities.Comment;
import com.tinqinacademy.comment.persistence.repositories.CommentRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;

@Slf4j
@Service
public class DeleteCommentOperationProcessor extends BaseOperationProcessor implements DeleteCommentOperation {
    private final CommentRepository commentRepository;

    @Autowired
    public DeleteCommentOperationProcessor(ConversionService conversionService, Validator validator, ErrorMapper errorMapper, CommentRepository commentRepository) {
        super(conversionService, validator, errorMapper);
        this.commentRepository = commentRepository;
    }

    @Override
    public Either<Errors, DeleteCommentOutput> process(DeleteCommentInput input) {
        return validateInput(input)
                .flatMap(validated -> deleteComment(input));
    }

    public Either<Errors, DeleteCommentOutput> deleteComment(DeleteCommentInput input) {
        return Try.of(() -> {
                    log.info("Start deleteComment input: {}", input);

                    Comment comment = getCommentIfExists(input);

                    commentRepository.delete(comment);

                    DeleteCommentOutput output = DeleteCommentOutput.builder().build();

                    log.info("End deleteComment output: {}", output);

                    return output;
                })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        Case($(instanceOf(NotFoundException.class)), ex -> errorMapper.handleError(ex, HttpStatus.NOT_FOUND)),
                        Case($(), ex -> errorMapper.handleError(ex, HttpStatus.BAD_REQUEST))
                ));
    }

    private Comment getCommentIfExists(DeleteCommentInput input) {
        return commentRepository.findById(UUID.fromString(input.getCommentId()))
                .orElseThrow(() -> new NotFoundException(String.format("Comment with id %s does not exist", input.getCommentId())));
    }
}
