package com.sample.validationdemo.domain;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Domain {

    @NotNull
    @Min(value = 10)
    private Integer min;
    @NotNull
    @Max(value = 100)
    private Integer max;
    @NotNull
    @Future
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    @NotNull
    @Email
    private String email;
}
