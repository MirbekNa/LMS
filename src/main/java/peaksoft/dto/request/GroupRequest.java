package peaksoft.dto.request;

import lombok.Builder;

@Builder
public record GroupRequest(String groupName, String imageLink, String description) {
    public GroupRequest(String groupName, String imageLink, String description) {
        this.groupName = groupName;
        this.imageLink = imageLink;
        this.description = description;
    }
}
