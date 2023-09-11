package peaksoft.dto.request;

import peaksoft.enums.Country;
import peaksoft.validation.PhoneNumberValidation;


public record CompanyRequest(String name,

                             Country country,
                             String address,
                             @PhoneNumberValidation
                             String phoneNumber) {
    public CompanyRequest(String name, Country country,
                          String address, String phoneNumber) {
        this.name = name;
        this.country = country;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}
