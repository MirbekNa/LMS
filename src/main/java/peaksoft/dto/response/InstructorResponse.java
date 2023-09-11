package peaksoft.dto.response;

import lombok.Builder;
import peaksoft.enums.SpecialAction;

@Builder

public record InstructorResponse(Long id,String firstName, String lastName, String phoneNumber, SpecialAction specialAction) {
    public InstructorResponse(Long id,String firstName, String lastName, String phoneNumber, SpecialAction specialAction) {
        this.id=id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.specialAction = specialAction;
    }


}
