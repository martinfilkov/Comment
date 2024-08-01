package com.tinqinacademy.comment.core.operations.hotel;

import com.tinqinacademy.comment.api.operations.base.Errors;
import com.tinqinacademy.comment.api.operations.hotel.publishcomment.PublishCommentInput;
import com.tinqinacademy.comment.api.operations.hotel.publishcomment.PublishCommentOperation;
import com.tinqinacademy.comment.api.operations.hotel.publishcomment.PublishCommentOutput;
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

import static io.vavr.API.*;


@Slf4j
@Service
public class PublishCommentOperationProcessor extends BaseOperationProcessor implements PublishCommentOperation {
    private final CommentRepository commentRepository;

    @Autowired
    public PublishCommentOperationProcessor(CommentRepository commentRepository,
                                            ConversionService conversionService,
                                            Validator validator,
                                            ErrorMapper errorMapper) {
        super(conversionService, validator, errorMapper);
        this.commentRepository = commentRepository;
    }

    @Override
    public Either<Errors, PublishCommentOutput> process(PublishCommentInput input) {
        return validateInput(input)
                .flatMap(validated -> publishComment(input));
    }

    private Either<Errors, PublishCommentOutput> publishComment(PublishCommentInput input) {

        return Try.of(() -> {
                    log.info("Start publishComment input: {}", input);

                    Comment commentToSave = conversionService.convert(input, Comment.class);

                    Comment savedComment = commentRepository.save(commentToSave);

                    PublishCommentOutput output = conversionService.convert(savedComment, PublishCommentOutput.class);

                    log.info("End publishComment output: {}", output);

                    return output;
                })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        Case($(), ex -> errorMapper.handleError(ex, HttpStatus.BAD_REQUEST))
                ));
    }
}
