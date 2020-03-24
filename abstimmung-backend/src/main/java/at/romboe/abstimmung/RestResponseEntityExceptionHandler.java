package at.romboe.abstimmung;

import javax.persistence.EntityExistsException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

// https://www.baeldung.com/exception-handling-for-rest-with-spring

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
	protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
		String bodyOfResponse = "ubbs";
		HttpStatus status = HttpStatus.CONFLICT;
		if (ex instanceof IllegalArgumentException) {
			bodyOfResponse = "Illegal arguments.";
			status = HttpStatus.BAD_REQUEST;
		}
		else if (ex instanceof EntityExistsException) {
			bodyOfResponse = "Already exists.";
		}
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), status, request);
	}
}
