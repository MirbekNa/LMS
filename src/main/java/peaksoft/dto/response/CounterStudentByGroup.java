package peaksoft.dto.response;

import lombok.Builder;

@Builder
public record CounterStudentByGroup (int counter,String description){
    public CounterStudentByGroup(int counter, String description) {
        this.counter = counter;
        this.description = description;
    }
}
