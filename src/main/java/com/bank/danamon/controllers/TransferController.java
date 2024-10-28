package com.bank.danamon.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bank.danamon.helpers.ResponseJson;
import com.bank.danamon.models.AccountModel;
import com.bank.danamon.models.TransferModel;
import com.bank.danamon.request.TransferPayload;
import com.bank.danamon.services.AccountService;
import com.bank.danamon.services.TransferService;

@CrossOrigin()
@RestController
public class TransferController {

    @Autowired
    TransferService transferService;

    @Autowired
    AccountService accountService;

    @PostMapping("transfer/create")
    public ResponseEntity<Object> create(@Valid @RequestBody TransferPayload payload, Errors errors) {
        ResponseJson<Object> response = new ResponseJson<>();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        response.setStatus_code(httpStatus.value());

        // Handle validation errors
        if (errors.hasErrors()) {
            List<String> messages = errors.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            response.setMessages(String.join(", ", messages));
            return ResponseEntity.status(httpStatus).body(response);
        }

        // Retrieve sender and receiver accounts
        AccountModel sender = accountService.findOne(payload.getSender_account_id());
        if (sender == null) {
            response.setMessages("Sender id does not exist");
            return ResponseEntity.status(httpStatus).body(response);
        }

        AccountModel receiver = accountService.findOne(payload.getReceiver_account_id());
        if (receiver == null) {
            response.setMessages("Receiver id does not exist");
            return ResponseEntity.status(httpStatus).body(response);
        }

        // Check sender's balance
        if (sender.getBalance() < payload.getAmount()) {
            response.setMessages("Balance is not enough");
            return ResponseEntity.status(httpStatus).body(response);
        }

        // Perform balance update and transfer
        sender.setBalance(sender.getBalance() - payload.getAmount());
        receiver.setBalance(receiver.getBalance() + payload.getAmount());
        accountService.save(sender);
        accountService.save(receiver);

        // Create and save transfer record
        TransferModel transfer = new TransferModel();
        transfer.setSender_account_id(payload.getSender_account_id());
        transfer.setReceiver_account_id(payload.getReceiver_account_id());
        transfer.setAmount(payload.getAmount());
        response.setData(transferService.save(transfer));

        // Set success status
        httpStatus = HttpStatus.OK;
        response.setStatus_code(httpStatus.value());

        return ResponseEntity.status(httpStatus).body(response);
    }
}
