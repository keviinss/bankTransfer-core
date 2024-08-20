package com.bank.danamon.services;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bank.danamon.models.UserModel;
import com.bank.danamon.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserModel save(UserModel userModel) {
        return userRepository.save(userModel);
    }

    public UserModel findOne(String user_id) {
        Optional<UserModel> userModel = userRepository.findByUid(user_id);
        if (!userModel.isPresent()) {
            return null;
        }
        return userModel.get();
    }

    public UserModel findByPhone(String phone_number) {
        Optional<UserModel> userModel = userRepository.findByPhone(phone_number);
        if (!userModel.isPresent()) {
            return null;
        }
        return userModel.get();
    }

    public UserModel findByEmail(String email) {
        Optional<UserModel> userModel = userRepository.findByEmail(email);
        if (!userModel.isPresent()) {
            return null;
        }
        return userModel.get();
    }

    public UserModel softDelete(UserModel userModel) {
        userRepository.save(userModel);
        return userModel;
    }

    public List<UserModel> findAll() {
        List<UserModel> aList = userRepository.findAll();
        if (aList.size() == 0) {
            aList = null;
        }
        return aList;
    }

    public Page<UserModel> search(String keyword, Pageable pageable) {
        return userRepository.search(keyword, pageable);
    }
}
