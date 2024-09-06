package com.bank.danamon.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
    public ResponseEntity<Object> create(@Valid @RequestBody TransferPayload payload, Errors errors,
            HttpServletRequest httpRequest) {
        ResponseJson<Object> response = new ResponseJson<>();
        HttpStatus httpstatus = HttpStatus.BAD_REQUEST;
        response.setStatus_code(httpstatus.value());

        if (errors.hasErrors()) {
            List<String> messages = new ArrayList<>();
            for (ObjectError error : errors.getAllErrors()) {
                messages.add(error.getDefaultMessage());
            }
            response.setMessages(String.join(", ", messages));
        } else {

            AccountModel sender = accountService.findOne(payload.getSender_account_id());
            AccountModel receiver = accountService.findOne(payload.getReceiver_account_id());

            if (sender != null) {
                if (receiver != null) {
                    if (sender.getBalance() > payload.getAmount()) {
                        TransferModel transfer = new TransferModel();
                        transfer.setSender_account_id(payload.getSender_account_id());
                        transfer.setReceiver_account_id(payload.getReceiver_account_id());
                        transfer.setAmount(payload.getAmount());

                        Integer addBalance = transfer.getAmount() + receiver.getBalance();
                        Integer reductionBalance = sender.getBalance() - transfer.getAmount();

                        sender.setBalance(reductionBalance);
                        receiver.setBalance(addBalance);
                        accountService.save(sender);

                        httpstatus = HttpStatus.OK;
                        response.setStatus_code(httpstatus.value());
                        response.setData(transferService.save(transfer));

                    } else {
                        response.setMessages("Balance is not enough");
                    }
                } else {
                    response.setMessages("Receiver id not exists");
                }
            } else {
                response.setMessages("Sender id not exists");
            }

        }

        return ResponseEntity.status(httpstatus).body(response);
    }

}
