package peaksoft.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneNumberValidate.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE,
        ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})

public @interface PhoneNumberValidation {

    String message() default "Phone number should start with country code:'+996' of 13 symbols";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
