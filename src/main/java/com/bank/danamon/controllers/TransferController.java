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

            TransferModel sender = transferService.findBySenderId(payload.getSender_account_id());
            TransferModel receive = transferService.findByReceiverId(payload.getReceiver_account_id());

            AccountModel accountByUser = accountService.findOne(payload.getAccountPayload().getAccount_id());

            if (accountByUser == null) {
                AccountModel account = new AccountModel();

                TransferModel transfer = new TransferModel();
                transfer.setSender_account_id(payload.getSender_account_id());
                transfer.setReceiver_account_id(payload.getReceiver_account_id());
                transfer.setAmount(payload.getAmount());

                Integer addBalance = transfer.getAmount() + account.getBalance();
                // Integer reductionBalance = Integer.valueOf(account.getAccount_id()) - transfer.getAmount();
                Integer reductionBalance = account.getBalance() - transfer.getAmount();

                account.setBalance(addBalance);
                // account.setAccount_id(reductionBalance);

                httpstatus = HttpStatus.OK;
                response.setStatus_code(httpstatus.value());
                response.setData(transferService.save(transfer));
            } else {
                response.setMessages("account_sender_not_exist");

            }

        }

        return ResponseEntity.status(httpstatus).body(response);
    }

}
