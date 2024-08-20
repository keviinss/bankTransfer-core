package com.bank.danamon.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserPayload {

    private String user_id;

    @NotEmpty(message = "first_name_is_required")
    private String first_name;

    @NotEmpty(message = "last_name_is_required")
    private String last_name;

    @NotEmpty(message = "email_is_required")
    private String email;

    @NotEmpty(message = "phone_number_is_required")
    private String phone_number;

    @NotEmpty(message = "address_is_required")
    private String address;

    @NotNull(message = "date_of_birth_is_required")
    private String date_of_birth;

}
