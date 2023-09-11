package peaksoft.dto.request;

import peaksoft.enums.Role;

public record SignUpRequest(String name, String email, String password, Role role) {
}
