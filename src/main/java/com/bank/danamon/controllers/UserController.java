package com.bank.danamon.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
import com.bank.danamon.models.UserModel;
import com.bank.danamon.request.SearchPayload;
import com.bank.danamon.request.UserPayload;
import com.bank.danamon.services.AccountService;
import com.bank.danamon.services.UserService;

@CrossOrigin()
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AccountService accountService;

    @PostMapping("/user/create")
    public ResponseEntity<Object> create(@Valid @RequestBody UserPayload payload, Errors errors,
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

            UserModel email = userService.findByEmail(payload.getEmail());
            UserModel phone = userService.findByPhone(payload.getPhone_number());
            Random rand = new Random();
            int randomNum = rand.nextInt(99999999);

            if (phone == null) {
                if (email == null) {
                    switch (payload.getAccount().getAccount_type()) {
                        case "LITE" -> {
                            UserModel user = new UserModel();
                            user.setFirst_name(payload.getFirst_name());
                            user.setLast_name(payload.getLast_name());
                            user.setEmail(payload.getEmail());
                            user.setPhone_number(payload.getPhone_number());
                            user.setAddress(payload.getAddress());
                            user.setDate_of_birth(payload.getDate_of_birth());
                            user.setGender(payload.getGender());

                            httpstatus = HttpStatus.OK;
                            response.setStatus_code(httpstatus.value());
                            response.setData(userService.save(user));

                            String aacount_id = user.getUser_id();
                            AccountModel account = new AccountModel();
                            account.setUser_id(aacount_id);
                            account.setAccount_type(payload.getAccount().getAccount_type());
                            account.setAccount_number(randomNum);
                            account.setBalance(200000);
                            account.setTransfer_limit_count(5);
                            accountService.save(account);
                        }
                        case "LITEPLUS" -> {
                            UserModel user = new UserModel();
                            user.setFirst_name(payload.getFirst_name());
                            user.setLast_name(payload.getLast_name());
                            user.setEmail(payload.getEmail());
                            user.setPhone_number(payload.getPhone_number());
                            user.setAddress(payload.getAddress());
                            user.setDate_of_birth(payload.getDate_of_birth());
                            user.setGender(payload.getGender());

                            httpstatus = HttpStatus.OK;
                            response.setStatus_code(httpstatus.value());
                            response.setData(userService.save(user));

                            String aacount_id = user.getUser_id();
                            AccountModel account = new AccountModel();
                            account.setUser_id(aacount_id);
                            account.setAccount_type(payload.getAccount().getAccount_type());
                            account.setAccount_number(randomNum);
                            account.setBalance(500000);
                            account.setTransfer_limit_count(10);
                            accountService.save(account);
                        }
                        case "REGULER" -> {
                            UserModel user = new UserModel();
                            user.setFirst_name(payload.getFirst_name());
                            user.setLast_name(payload.getLast_name());
                            user.setEmail(payload.getEmail());
                            user.setPhone_number(payload.getPhone_number());
                            user.setAddress(payload.getAddress());
                            user.setDate_of_birth(payload.getDate_of_birth());
                            user.setGender(payload.getGender());

                            httpstatus = HttpStatus.OK;
                            response.setStatus_code(httpstatus.value());
                            response.setData(userService.save(user));

                            String aacount_id = user.getUser_id();
                            AccountModel account = new AccountModel();
                            account.setUser_id(aacount_id);
                            account.setAccount_type(payload.getAccount().getAccount_type());
                            account.setAccount_number(randomNum);
                            account.setBalance(1000000);
                            account.setTransfer_limit_count(0);
                            accountService.save(account);
                        }
                        default -> {
                            response.setMessages("Account type is not available");
                        }
                    }

                } else {
                    response.setMessages("Email address already exists");
                }
            } else {
                response.setMessages("Phone already exists");
            }

        }
        return ResponseEntity.status(httpstatus).body(response);
    }

    @PostMapping("/user/update")
    public ResponseEntity<Object> update(@Valid @RequestBody UserPayload payload, Errors errors,
            HttpServletRequest httpRequest) {
        ResponseJson<Object> response = new ResponseJson<>();
        HttpStatus httpstatus = HttpStatus.BAD_REQUEST;
        response.setStatus_code(httpstatus.value());

        String userId = payload.getUser_id();
        if (errors.hasErrors()) {
            List<String> messages = new ArrayList<>();
            for (ObjectError error : errors.getAllErrors()) {
                messages.add(error.getDefaultMessage());
            }
            response.setMessages(String.join(", ", messages));
        } else {
            UserModel user = userService.findOne(userId);
            UserModel email = userService.findByEmail(payload.getEmail());
            UserModel phone = userService.findByPhone(payload.getPhone_number());
            if (user != null) {
                if (phone == null) {
                    if (email == null) {
                        user.setUser_id(userId);
                        user.setFirst_name(payload.getFirst_name());
                        user.setLast_name(payload.getLast_name());
                        user.setEmail(payload.getEmail());
                        user.setPhone_number(payload.getPhone_number());
                        user.setAddress(payload.getAddress());
                        user.setDate_of_birth(payload.getDate_of_birth());
                        user.setGender(payload.getGender());

                        httpstatus = HttpStatus.OK;
                        response.setStatus_code(httpstatus.value());
                        response.setData(userService.save(user));
                    } else {
                        response.setMessages("Email address already exists");
                    }
                } else {
                    response.setMessages("Phone already exists");
                }
            } else {
                response.setMessages("Data not exist");
            }
        }

        return ResponseEntity.status(httpstatus).body(response);

    }

    @PostMapping("/user/getById")
    public ResponseEntity<Object> getById(@RequestBody UserPayload payload, HttpServletRequest httpRequest) {

        ResponseJson<Object> response = new ResponseJson<>();
        HttpStatus httpstatus = HttpStatus.BAD_REQUEST;
        response.setStatus_code(httpstatus.value());

        String user_id = payload.getUser_id();
        if (user_id != null && !user_id.isEmpty()) {

            UserModel data = userService.findOne(user_id);
            if (data != null) {
                httpstatus = HttpStatus.OK;
                response.setStatus_code(httpstatus.value());
                response.setData(data);
            } else {
                response.setMessages("Data not exist");
            }

        } else {
            response.setMessages("Uid is required");
        }

        return ResponseEntity.status(httpstatus).body(response);

    }

    @PostMapping("/user/getAll")
    public ResponseEntity<Object> getAll(@RequestBody SearchPayload payload, HttpServletRequest httpRequest) {

        List<UserModel> data = userService.findAll();
        ResponseJson<Object> response = new ResponseJson<>();
        response.setData(data);
        response.setStatus_code(HttpStatus.OK.value());

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @PostMapping("/user/delete")
    public ResponseEntity<Object> delete(@RequestBody UserPayload payload, HttpServletRequest httpRequest) {
        ResponseJson<Object> response = new ResponseJson<>();
        HttpStatus httpstatus = HttpStatus.BAD_REQUEST;
        response.setStatus_code(httpstatus.value());

        String uid = payload.getUser_id();
        if (uid != null && !uid.isEmpty()) {

            UserModel user = userService.findOne(uid);

            if (user != null) {

                user.setIs_deleted(true);

                AccountModel account = accountService.findByUserId(uid);
                account.setIs_deleted(true);
                accountService.save(account);

                httpstatus = HttpStatus.OK;
                response.setStatus_code(httpstatus.value());
                response.setData(userService.save(user));
                response.setMessages("Data has been deleted");
            } else {
                response.setMessages("Data not exist");
            }

        } else {
            response.setMessages("Uid is required");
        }

        return ResponseEntity.status(httpstatus).body(response);

    }
}
