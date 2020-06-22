package com.sample.validationdemo.controller;

import com.sample.validationdemo.domain.Domain;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;

@RestController
@RequestMapping("/validate/api")
@Validated
public class ValidationController {


    @PostMapping("method-argument-test/{max}")
    public ResponseEntity<?> methodArgumentTest(@Max(value = 100) @PathVariable("max") int max) {
        //만약 get을 못쓴다면?
        //ConstraintViolationException 메소드레벨 폼데이터에서는 잘 된다. 그리고 @PathVariable에서도 잘 된다. 그러나 RequestBody에서는 바로 primitive 타입으로 받는 것은 어렵다.
        return ResponseEntity.ok(max);
    }

    @PostMapping("primitive-type")
    public ResponseEntity<?> requestBodyPrimitiveTypeTest(@Max(value = 100) @RequestBody int test) {
        //이건 애초에 안된다. jackson이 json을 Integer로 바로 변환해주지 못한다.
        return ResponseEntity.ok(test);
    }

    //Errors 객체 사용 -> 500에러 발생? ?는 내가 이해하지 못한 부분 -> 이게 원래는 해당 파라미터로 Errors를 선언한다면 에러처리를 ExceptionHandler에서 수행하지 않고 컨트롤러로 위임하는데
    // @Validated가 선언되어있으면 그냥 에러가 나버린다.
    @PostMapping("errors")
    public ResponseEntity<? extends Domain> errorsTest(@Valid @RequestBody  Domain domain, Errors errors) {
        System.out.println(errors.toString());
        return ResponseEntity.ok(domain);
    }

    //BindingResult객체 사용 -> 500에러 발생?
    @PostMapping("bindingresult")
    public ResponseEntity<? extends Domain> bindingResultTest(@Valid @RequestBody Domain domain, BindingResult result) {
        //validated 어노테이션이 달려있으면 여기 들어오기 전에 나가리고, 없으면 안으로 들어온다. 이 안에서 에러처리를 하면 된다.
        System.out.println("hello");
        System.out.println(result.toString());
        return ResponseEntity.ok(domain);
    }

    //아무 것도 사용 X
    @PostMapping("none")
    public ResponseEntity<? extends Domain> noneTest(@Valid @RequestBody Domain domain) {
        //MethodArgumentNotValidException
        return ResponseEntity.ok(domain);
    }

    @PostMapping("internal-error")
    public ResponseEntity<Object> internalErrorTest() {
        throw new RuntimeException("runtime exception");
    }

    //validator를 빈으로 등록한다면 ? 똑같음. 부트에서 미리 만들어주는 듯 하다.

//    @ExceptionHandler({BindException.class})
//    public ResponseEntity<?> handleBindingException(BindException be) {
//        //BindingResult vs Errors
//        be.getFieldErrors().stream().forEach(System.out::println);
//        return ResponseEntity.badRequest().body(be.getMessage());
//    }
    //객체로 그냥 보내주기
//    @ExceptionHandler({BindException.class})
//    public Object handleBindingException(BindException be) {
//        //getGlobalErrors는 아무 것도 안나옴
//        return be.getFieldErrors();
//    }

    // BindingResult, Errors, initBinder
    //Errors : binding 에러나 validation 에러를 모아놓는 객체
    //BindingResult : validate 메서드를 수행하기 위해 존재하는 것. 예를 들어 bindingResult를 받은 후에 그것에 오류가 존재하는지를 확인하는 용도로 사용된 것이기 때문에 현재는 어노테이션으로 대체
    //validated에 대해서도 정확하게 이해하기



}
