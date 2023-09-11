package peaksoft.service;

import peaksoft.dto.request.SignInRequest;
import peaksoft.dto.request.SignRequest;
import peaksoft.dto.request.SignUpRequest;
import peaksoft.dto.response.AuthenticationResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.exceptions.AlreadyExistException;
import peaksoft.exceptions.BadCredentialException;

public interface AuthenticationService {
    AuthenticationResponse signUp(SignUpRequest signUpRequest) throws AlreadyExistException;

    AuthenticationResponse signIn(SignInRequest signInRequest) throws BadCredentialException;
    void init();

    SimpleResponse signIn2(SignRequest signRequest);

    SimpleResponse deleteUserById(Long id);

    SimpleResponse deleteUserByEmail(String email);
}
