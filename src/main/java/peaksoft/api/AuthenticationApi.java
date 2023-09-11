package peaksoft.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.SignInRequest;
import peaksoft.dto.request.SignRequest;
import peaksoft.dto.request.SignUpRequest;
import peaksoft.dto.response.AuthenticationResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.service.AuthenticationService;
import peaksoft.exceptions.AlreadyExistException;
import peaksoft.exceptions.BadCredentialException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "AuthenticationApi")
public class AuthenticationApi {
    private final AuthenticationService authenticationService;

    @PostMapping("/signUp")
    @Operation(summary = "Sign up", description = "To sign up fill ")
    AuthenticationResponse signUp(@RequestBody SignUpRequest signUpRequest) throws AlreadyExistException {
        return authenticationService.signUp(signUpRequest);
    }

    @PostMapping("/signIn")
    AuthenticationResponse signIn(@RequestBody SignInRequest signInRequest) throws BadCredentialException {
        return authenticationService.signIn(signInRequest);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse signUp2(@RequestBody @Valid SignRequest signRequest) {
        return authenticationService.signIn2(signRequest);
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse deleteUser(@PathVariable Long id) {
        return authenticationService.deleteUserById(id);
    }
    @PostMapping("/delete")
    SimpleResponse deleteUserByEmail(@RequestParam String email){
        return authenticationService.deleteUserByEmail(email);
    }
}
