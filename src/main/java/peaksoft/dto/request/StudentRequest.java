package peaksoft.dto.request;

import lombok.Builder;
import peaksoft.enums.IsBlocked;
import peaksoft.enums.Role;
import peaksoft.enums.StudyFormat;
import peaksoft.validation.EmailValidation;
import peaksoft.validation.PhoneNumberValidation;

@Builder
public record StudentRequest(
        String firstName,
                             String lastName,
                             @EmailValidation
                             String email,
                             @PhoneNumberValidation
                             String phoneNumber,
                             String password,
                             StudyFormat studyFormat,
                             IsBlocked isBlocked,
                             Role role) {

}
