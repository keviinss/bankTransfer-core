package com.bank.danamon.repository;

import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bank.danamon.models.UserModel;

public interface UserRepository extends JpaRepository<UserModel, String> {

    @Override
    @Query("SELECT a FROM UserModel a WHERE a.is_deleted = false")
    List<UserModel> findAll();

    @Query("SELECT u FROM UserModel u WHERE u.is_deleted = false AND u.user_id = :user_id ")
    Optional<UserModel> findByUid(@PathParam("user_id") String user_id);

    @Query("SELECT u FROM UserModel u WHERE u.is_deleted = false AND u.email = :email ")
    Optional<UserModel> findByEmail(@PathParam("email") String email);

    @Query("SELECT u FROM UserModel u WHERE u.is_deleted = false AND u.phone_number = :phone_number ")
    Optional<UserModel> findByPhone(@PathParam("phone_number") String phone_number);

    @Query("SELECT s FROM UserModel s WHERE s.is_deleted = false")
    Page<UserModel> search(String text_search, Pageable pageable);
}
