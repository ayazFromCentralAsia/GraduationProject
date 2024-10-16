package com.example.delivery.service.Validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = ValidPhoneNumberValidator.class)

public @interface ValidPhoneNumber {
    String message() default "Invalid phone number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
