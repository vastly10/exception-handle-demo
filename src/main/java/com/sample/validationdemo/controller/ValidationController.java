package com.sample.validationdemo.controller;

import com.sample.validationdemo.domain.Domain;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import java.io.IOException;

@RestController
@RequestMapping("/validate/api")
@Validated
public class ValidationController {


    @PostMapping("method-argument-test/{max}") //ok
    public ResponseEntity<?> methodArgumentTest(@Max(value = 100) @PathVariable("max") int max) {
        return ResponseEntity.ok(max);
    }

    @PostMapping("primitive-type") //fail
    public ResponseEntity<?> requestBodyPrimitiveTypeTest(@Max(value = 100) @RequestBody int test) {
        //jackson이 json을 Integer로 바로 변환해주지 못한다.
        return ResponseEntity.ok(test);
    }


    @GetMapping("none")
    public ResponseEntity<?> noneTest(@Valid Domain domain) {
        //MethodArgumentNotValidException
        return ResponseEntity.badRequest().body(domain);
    }

    @GetMapping("none/domain")
    public Domain domainTest(@Valid Domain domain) {

        return domain;
    }

    /**
     * ResponseStatusExceptionResolver가 resolve한다.
     * */
    @PostMapping("response-status-exception")
    public ResponseEntity<?> responseStatusExceptionTest() {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "response status exception test", new RuntimeException());
    }

    @PostMapping("checked-error")
    public ResponseEntity<Object> internalErrorTest() throws IOException {

        throw new IOException("io exception");
    }

    @PostMapping("runtime-error")
    public ResponseEntity<?> runtimeErrorTest() {
        throw new ArrayIndexOutOfBoundsException("array exception");
    }

    @PostMapping("errors") // @Validated와 함께 사용 불가.
    public ResponseEntity<?> errorsTest(@Valid @RequestBody  Domain domain, Errors errors) {
        System.out.println(errors.toString());
        return ResponseEntity.ok(domain);
    }

    @PostMapping("bindingresult") // @Validated와 함께 사용 불가.
    public ResponseEntity<?> bindingResultTest(@Valid @RequestBody Domain domain, BindingResult result) {
        System.out.println(result.toString());
        return ResponseEntity.ok(domain);
    }


}
