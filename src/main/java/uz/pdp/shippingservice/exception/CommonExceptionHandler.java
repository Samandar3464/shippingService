package uz.pdp.shippingservice.exception;

import com.google.firebase.messaging.FirebaseMessagingException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.pdp.shippingservice.entity.api.ApiResponse;

import java.io.IOException;
import java.security.SignatureException;
import java.util.List;
import java.util.stream.Stream;

import static uz.pdp.shippingservice.entity.constants.Constants.*;


@RestControllerAdvice
public class CommonExceptionHandler {
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public List<ApiResponse> handleBindingException(BindException e) {
        return Stream.concat(
                e.getBindingResult().getFieldErrors().stream()
                        .map(fieldError -> new ApiResponse(
                                fieldError.getDefaultMessage(),
                                false,
                                fieldError.getField())),
                e.getBindingResult().getGlobalErrors().stream()
                        .map(globalError -> new ApiResponse(
                                globalError.getDefaultMessage(),
                                false,
                                globalError.getObjectName()))
        ).toList();

    }

    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse handleObjectNotException(RecordNotFoundException e) {
        return new ApiResponse(
                e.getMessage()
                , false
                , null);
    }

    @ExceptionHandler(RecordAlreadyExistException.class)
    @ResponseStatus(HttpStatus.ALREADY_REPORTED)
    public ApiResponse handleObjectAlreadyExist(RecordAlreadyExistException e) {
        return new ApiResponse(
                e.getMessage()
                , false
                , null);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse handleUserNotFoundException(UserNotFoundException e) {
        return new ApiResponse(
                USER_NOT_FOUND
                , false
                , null);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    @ResponseStatus(HttpStatus.ALREADY_REPORTED)
    public ApiResponse handleUserNotFoundException(UserAlreadyExistException e) {
        return new ApiResponse(
                USER_ALREADY_EXIST
                , false
                , null);
    }

    @ExceptionHandler(value = {ExpiredJwtException.class, SignatureException.class,
            UnsupportedJwtException.class, MalformedJwtException.class, IllegalArgumentException.class, IOException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiResponse handleAccessTokenTimeExceeded(Exception e) {
        return new ApiResponse(
                TOKEN_TIME_OUT
                , false
                , null);
    }

    @ExceptionHandler(value = {FileInputException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiResponse handleFileInputException(FileInputException e) {
        return new ApiResponse(
                FILE_SIZE_MUST_BU_10MB_OR_LOWER
                , false
                , null);
    }

    @ExceptionHandler(FirebaseConnectionException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiResponse handleFireBaseConnectionException(FirebaseConnectionException e) {
        return new ApiResponse(
                FIREBASE_EXCEPTION
                , false
                , null);
    }

    @ExceptionHandler(FirebaseMessagingException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse fireBaseException(FirebaseMessagingException e) {
        return new ApiResponse(
                FIREBASE_EXCEPTION
                , false
                , null);
    }
//    @ExceptionHandler(value = {InputException.class})
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    public ApiResponse handleInputException(InputException e) {
//        return new ApiResponse(
//                TOKEN_TIME_OUT
//                , false
//                , null);
//    }

    @ExceptionHandler(SmsSendingFailException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiResponse handleSmsSendingFailException(SmsSendingFailException e) {
        return new ApiResponse(
                e.getMessage()
                , false
                , null);
    }

    @ExceptionHandler(AnnouncementNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse announcementNotFoundException(AnnouncementNotFoundException e) {
        return new ApiResponse(
                e.getMessage()
                , false
                , null);
    }

    @ExceptionHandler(AnnouncementAlreadyExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse announcementAlreadyExistException(AnnouncementAlreadyExistException e) {
        return new ApiResponse(
                e.getMessage()
                , false
                , null);
    }


    @ExceptionHandler(CarNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse carNotFound(CarNotFound e) {
        return new ApiResponse(
                CAR_NOT_FOUND
                , false
                , null);
    }

    @ExceptionHandler(CarAlreadyExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse carAlreadyExist(CarAlreadyExistException e) {
        return new ApiResponse(
                CAR_NOT_FOUND
                , false
                , null);
    }

    @ExceptionHandler(SmsServiceBroken.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse notEnoughNotException(SmsServiceBroken e) {
        return new ApiResponse(
                CAN_NOT_TAKE_SMS_SENDING_SERVICE_TOKEN
                , false
                , null);
    }

    @ExceptionHandler(TimeExceededException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiResponse reFreshTokenTimeOut(TimeExceededException e) {
        return new ApiResponse(
                REFRESH_TOKEN_TIME_OUT
                , false
                , null);
    }

    @ExceptionHandler(AnnouncementAvailable.class)
    @ResponseStatus(HttpStatus.ALREADY_REPORTED)
    public ApiResponse announcementAvailable(AnnouncementAvailable e) {
        return new ApiResponse(
                e.getMessage()
                , false
                , null);
    }
}
