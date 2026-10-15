package com.employeesservice.employees;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import com.employeesservice.employees.configuration.GlobalExceptionHandler;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleResponseStatusExceptionReturnsCorrectStatusAndBody() {
        ResponseStatusException exception = new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Employee not found");

        ResponseEntity<Map<String, Object>> response = handler.handleResponseStatusException(exception);

        assertEquals(
                HttpStatus.NOT_FOUND,
                response.getStatusCode());

        assertNotNull(response.getBody());

        assertEquals(
                404,
                response.getBody().get("status"));

        assertEquals(
                "Employee not found",
                response.getBody().get("error"));

        assertNotNull(response.getBody().get("timestamp"));
    }

    @Test
    void handleGlobalExceptionReturnsInternalServerError() {
        Exception exception = new IllegalStateException("unexpected");

        ResponseEntity<Map<String, Object>> response = handler.handleGlobalException(exception);

        assertEquals(
                HttpStatus.INTERNAL_SERVER_ERROR,
                response.getStatusCode());

        assertNotNull(response.getBody());

        assertEquals(
                500,
                response.getBody().get("status"));

        assertEquals(
                "An unexpected error occurred: unexpected",
                response.getBody().get("error"));

        assertNotNull(response.getBody().get("timestamp"));
    }
}
