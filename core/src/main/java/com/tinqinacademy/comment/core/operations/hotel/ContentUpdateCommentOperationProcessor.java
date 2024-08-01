package com.tinqinacademy.comment.core.operations.hotel;

import com.tinqinacademy.comment.api.operations.base.Errors;
import com.tinqinacademy.comment.api.operations.exception.NotFoundException;
import com.tinqinacademy.comment.api.operations.hotel.partialupdatecomment.ContentUpdateCommentInput;
import com.tinqinacademy.comment.api.operations.hotel.partialupdatecomment.ContentUpdateCommentOperation;
import com.tinqinacademy.comment.api.operations.hotel.partialupdatecomment.ContentUpdateCommentOutput;
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
public class ContentUpdateCommentOperationProcessor extends BaseOperationProcessor implements ContentUpdateCommentOperation {
    private final CommentRepository commentRepository;

    @Autowired
    public ContentUpdateCommentOperationProcessor(ConversionService conversionService,
                                                     Validator validator,
                                                     ErrorMapper errorMapper, CommentRepository commentRepository) {
        super(conversionService, validator, errorMapper);
        this.commentRepository = commentRepository;
    }

    @Override
    public Either<Errors, ContentUpdateCommentOutput> process(ContentUpdateCommentInput input) {
        return validateInput(input)
                .flatMap(validated -> contentUpdateComment(input));
    }

    private Either<Errors, ContentUpdateCommentOutput> contentUpdateComment(ContentUpdateCommentInput input) {
        return Try.of(() -> {
                    log.info("Start partialUpdateComment input: {}", input);

                    Comment comment = getCommentIfValid(input);
                    comment.setContent(input.getContent());
                    comment.setLastEditedBy(UUID.randomUUID());

                    Comment updatedComment = commentRepository.save(comment);

                    ContentUpdateCommentOutput output = conversionService.convert(updatedComment, ContentUpdateCommentOutput.class);

                    log.info("End partialUpdateComment output: {}", output);

                    return output;
                })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        Case($(instanceOf(NotFoundException.class)), ex -> errorMapper.handleError(ex, HttpStatus.NOT_FOUND)),
                        Case($(), ex -> errorMapper.handleError(ex, HttpStatus.BAD_REQUEST))
                ));
    }

    private Comment getCommentIfValid(ContentUpdateCommentInput input) {
        return commentRepository.findById(UUID.fromString(input.getCommentId()))
                .orElseThrow(() -> new NotFoundException(String.format("Comment with id %s does not exist", input.getCommentId())));
    }
}
