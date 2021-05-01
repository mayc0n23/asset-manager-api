package br.edu.ifpb.assetmanagerapi.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

import br.edu.ifpb.assetmanagerapi.domain.exception.EntidadeNaoEncontradaException;
import br.edu.ifpb.assetmanagerapi.domain.exception.NegocioException;
import br.edu.ifpb.assetmanagerapi.infrastructure.storage.StorageException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(StorageException.class)
	public ResponseEntity<?> handleStorageException(StorageException ex, WebRequest request) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		String detail = ex.getMessage();
		Problem problem = createProblemBuilder(status, detail).build();
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> handleNegocioException(NegocioException ex, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		String detail = ex.getMessage();
		Problem problem = createProblemBuilder(status, detail).build();
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	//Exceção lançada quando não é encontrada alguma entidade
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> handlerEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex,
					WebRequest request) {

		HttpStatus status = HttpStatus.NOT_FOUND;
		String detail = ex.getMessage();

		Problem problem = createProblemBuilder(status, detail).build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(InvalidGrantException.class)
	public ResponseEntity<?> handleInvalidGrant(InvalidGrantException ex, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		String detail = "Usuário ou senha inválidos";
		Problem problem = createProblemBuilder(status, detail).build();
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<?> handleAccessDenied(AccessDeniedException ex, WebRequest request) {
		HttpStatus status = HttpStatus.FORBIDDEN;
		String detail = "Você não possui permissão para acessar esse recurso";
		
		Problem problem = createProblemBuilder(status, detail).build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	//Exceção que pode ser lançada quando a erros de sintaxe no corpo da requisição
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Throwable rootCause = ExceptionUtils.getRootCause(ex);

		if (rootCause instanceof InvalidFormatException) {
			return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
		} else if (rootCause instanceof PropertyBindingException) {
			return handlePropertyBidingException((PropertyBindingException) rootCause, headers, status, request);
		}
		String detail = "O corpo da requisição está inválido, verifique a sintaxe";

		Problem problem = createProblemBuilder(status,  detail).build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	//Exceção lançada quando tentamos acessar um recurso inexistente
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		String detail = String.format("O recurso %s que você tentou acessar é inexistente.", ex.getRequestURL());

		Problem problem = createProblemBuilder(status, detail).build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		if (ex instanceof MethodArgumentTypeMismatchException) {
			return handleMethodArgumentTypeMismatch((MethodArgumentTypeMismatchException) ex, headers, status, request);
		}

		return super.handleTypeMismatch(ex, headers, status, request);
	}
	
	//Exceção lançada quando o usuario passa o parametro inválido na URL da requisição
	private ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String detail = String.format(
				"O parâmetro de URL '%s' recebeu o valor '%s', "
						+ "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
							ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

		Problem problem = createProblemBuilder(status, detail).build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	//Exceção lançada quando o usuario coloca uma propriedade inexistente na requisição
	private ResponseEntity<Object> handlePropertyBidingException(PropertyBindingException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		String path = ex.getPath().stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));
		String detail = String.format("A propriedade '%s' não existe. Corrija ou remova a propriedade.", path);

		Problem problem = createProblemBuilder(status, detail).build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	//Exceção lançada quando passamos um valor incorreto a uma propriedade
	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		String path = ex.getPath().stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));

		String detail = String.format(
				"A propriedade '%s' recebeu o valor '%s' que é um tipo inválido. "
						+ "Corrija e informe um valor compatível com o tipo %s.",
							path, ex.getValue(), ex.getTargetType().getSimpleName());
		
		Problem problem = createProblemBuilder(status, detail).build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	
	//Exceção lançada quando falta algum campo obrigatorio no corpo da requisição
	private ResponseEntity<Object> handleValidationInternal(Exception ex, BindingResult bindingResult,
				HttpStatus status, HttpHeaders headers, WebRequest request) {

		String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";

		List<Problem.Object> problemObjects = bindingResult.getAllErrors()
				.stream()
				.map(objectError -> {
					String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
					String name = objectError.getObjectName();
					
					if (objectError instanceof FieldError) {
						name = ((FieldError) objectError).getField();
					}
					
					return Problem.Object.builder().name(name).message(message).build();
				}).collect(Collectors.toList());
				
		Problem problem = createProblemBuilder(status, detail).objects(problemObjects)
				.build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		return handleValidationInternal(ex, ex.getBindingResult(), status, headers, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		if (body == null) {
			body = Problem.builder().status(status.value()).detail(status.getReasonPhrase()).build();
		} else if (body instanceof String) {
			body = Problem.builder().status(status.value()).detail((String) body).build();
		}
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}

	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, String detail) {
		return Problem.builder()
				.status(status.value())
				.detail(detail)
				.timestamp(OffsetDateTime.now());
	}
	
}