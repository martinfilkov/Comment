package com.tinqinacademy.comment.core.operations.hotel;

import com.tinqinacademy.comment.api.operations.base.Errors;
import com.tinqinacademy.comment.api.operations.hotel.getcomments.GetCommentsInput;
import com.tinqinacademy.comment.api.operations.hotel.getcomments.GetCommentsOperation;
import com.tinqinacademy.comment.api.operations.hotel.getcomments.GetCommentsOutput;
import com.tinqinacademy.comment.api.operations.hotel.getcomments.GetCommentsOutputList;
import com.tinqinacademy.comment.core.operations.BaseOperationProcessor;
import com.tinqinacademy.comment.core.utils.ErrorMapper;
import com.tinqinacademy.comment.persistence.repositories.CommentRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static io.vavr.API.*;

@Slf4j
@Service
public class GetCommentsOperationProcessor extends BaseOperationProcessor implements GetCommentsOperation {
    private final CommentRepository commentRepository;

    @Autowired
    public GetCommentsOperationProcessor(CommentRepository commentRepository,
                                         ConversionService conversionService,
                                         Validator validator,
                                         ErrorMapper errorMapper) {
        super(conversionService, validator, errorMapper);
        this.commentRepository = commentRepository;
    }

    @Override
    public Either<Errors, GetCommentsOutputList> process(GetCommentsInput input) {
        return validateInput(input)
                .flatMap(validated -> getComments(input));
    }

    private Either<Errors, GetCommentsOutputList> getComments(GetCommentsInput input) {
        return Try.of(() -> {
                            log.info("Start getComments input: {}", input);

                            List<GetCommentsOutput> outputList = commentRepository.findByRoomId(UUID.fromString(input.getRoomId())).stream()
                                    .map(comment -> conversionService.convert(comment, GetCommentsOutput.class))
                                    .toList();


                            GetCommentsOutputList output = GetCommentsOutputList.builder()
                                    .comments(outputList)
                                    .build();

                            log.info("End getRoomIds output: {}", output);

                            return output;
                        }
                )
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        Case($(), ex -> errorMapper.handleError(ex, HttpStatus.BAD_REQUEST))
                ));
    }
}
