package peaksoft.dto.response;

import lombok.Builder;
import peaksoft.enums.IsBlocked;
import peaksoft.enums.StudyFormat;



@Builder
public record StudentResponse(Long id, String firstName, String lastName,
                              String phoneNumber, String email, StudyFormat studyFormat, IsBlocked isBlocked) {
    public StudentResponse(Long id, String firstName, String lastName, String phoneNumber, String email, StudyFormat studyFormat, IsBlocked isBlocked) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.studyFormat = studyFormat;
        this.isBlocked = isBlocked;
    }
}
