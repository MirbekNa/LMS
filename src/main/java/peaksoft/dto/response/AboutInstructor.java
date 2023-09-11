package peaksoft.dto.response;

import lombok.Builder;
import peaksoft.enums.SpecialAction;

import java.util.Map;

@Builder
public record AboutInstructor (String firstName , String lastName, String phoneNumber,
                               SpecialAction specialAction, Map<String,String>students,Map<String,Integer> counter){
    public AboutInstructor(String firstName, String lastName, String phoneNumber,
                           SpecialAction specialAction, Map<String, String> students,Map<String,Integer> counter) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.specialAction = specialAction;
        this.students = students;
        this.counter = counter;

    }
}
