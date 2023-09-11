package peaksoft.dto.response;

import lombok.Builder;

@Builder

public record GroupResponse(String groupName, String imageLink, String description) {
    public GroupResponse(String groupName, String imageLink, String description) {
        this.groupName = groupName;
        this.imageLink = imageLink;
        this.description = description;
    }


}
