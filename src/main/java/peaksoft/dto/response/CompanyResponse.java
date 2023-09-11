package peaksoft.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import peaksoft.enums.Country;
@Getter
@Setter
@Builder

public class CompanyResponse {

    private String name;
    private Country country;
    private String address;
    private String phoneNumber;

    public CompanyResponse(String name, Country country, String address, String phoneNumber) {
        this.name = name;
        this.country = country;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public CompanyResponse() {
    }
}
