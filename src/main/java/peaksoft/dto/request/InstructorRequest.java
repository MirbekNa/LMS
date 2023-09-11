package peaksoft.dto.request;

import lombok.Builder;
import peaksoft.enums.Role;
import peaksoft.enums.SpecialAction;
import peaksoft.validation.EmailValidation;
import peaksoft.validation.PhoneNumberValidation;


@Builder
public record InstructorRequest(String firstName,
                                String lastName,
                                @EmailValidation(message = "Invalid email format")
                                String email,
                                @PhoneNumberValidation
                                String phoneNumber,
                                String password,
                                SpecialAction specialAction,
                                Role role) {

}
