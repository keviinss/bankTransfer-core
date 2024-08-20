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
import com.bank.danamon.request.SearchPayload;
import com.bank.danamon.request.AccountPayload;
import com.bank.danamon.services.AccountService;

@CrossOrigin()
@RestController
public class AccountController {

    @Autowired
    AccountService accountService;

    @PostMapping("/account/upgradeAccount")
    public ResponseEntity<Object> update(@Valid @RequestBody AccountPayload payload, Errors errors,
            HttpServletRequest httpRequest) {
        ResponseJson<Object> response = new ResponseJson<>();
        HttpStatus httpstatus = HttpStatus.BAD_REQUEST;
        response.setStatus_code(httpstatus.value());

        String account_id = payload.getAccount_id();
        if (errors.hasErrors()) {
            List<String> messages = new ArrayList<>();
            for (ObjectError error : errors.getAllErrors()) {
                messages.add(error.getDefaultMessage());
            }
            response.setMessages(String.join(", ", messages));
        } else {
            AccountModel account = accountService.findOne(account_id);
            AccountModel user_id = accountService.findByUserId(payload.getUser_id());
            if (account != null) {
                if (user_id != null) {

                    account.setAccount_id(account_id);
                    account.setAccount_type(payload.getAccount_type());

                    httpstatus = HttpStatus.OK;
                    response.setStatus_code(httpstatus.value());
                    response.setData(accountService.save(account));

                } else {
                    response.setMessages("user_id_not_exist");
                }
            } else {
                response.setMessages("data_not_exist");
            }
        }

        return ResponseEntity.status(httpstatus).body(response);

    }

    @PostMapping("/account/inquiry")
    public ResponseEntity<Object> getById(@RequestBody AccountPayload payload, HttpServletRequest httpRequest) {
        ResponseJson<Object> response = new ResponseJson<>();
        HttpStatus httpstatus = HttpStatus.BAD_REQUEST;
        response.setStatus_code(httpstatus.value());

        String account_id = payload.getAccount_id();
        if (account_id != null && !account_id.isEmpty()) {
            AccountModel data = accountService.findOne(account_id);
            if (data != null) {
                httpstatus = HttpStatus.OK;
                response.setStatus_code(httpstatus.value());
                response.setData(data);
            } else {
                response.setMessages("data_not_exist");
            }
        } else {
            response.setMessages("account_id_is_required");
        }
        return ResponseEntity.status(httpstatus).body(response);
    }

    @PostMapping("/account/getAll")
    public ResponseEntity<Object> getAll(@RequestBody SearchPayload payload, HttpServletRequest httpRequest) {
        List<AccountModel> data = accountService.findAll();
        ResponseJson<Object> response = new ResponseJson<>();
        response.setData(data);
        response.setStatus_code(HttpStatus.OK.value());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
