package peaksoft.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class EmailValidator implements ConstraintValidator<EmailValidation,String> {

private String message;

    @Override
    public void initialize(EmailValidation constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        boolean isValid = email.matches(emailRegex) && email.length() <= 320;

        if (!isValid) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation();
        }

        return isValid;
    }
}
