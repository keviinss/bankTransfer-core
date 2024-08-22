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
public class TransferPayload {

    private String transfer_id;

    @NotEmpty(message = "sender_account_is_required")
    private String sender_account_id;

    @NotEmpty(message = "receiver_account_is_required")
    private String receiver_account_id;

    @NotNull(message = "amount_is_required")
    private Integer amount;

    private AccountPayload accountPayload;

}
