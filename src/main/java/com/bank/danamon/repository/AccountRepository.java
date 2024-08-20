package com.bank.danamon.repository;

import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bank.danamon.models.AccountModel;

public interface AccountRepository extends JpaRepository<AccountModel, String> {

    @Override
    @Query("SELECT a FROM AccountModel a WHERE a.is_deleted = false")
    List<AccountModel> findAll();

    @Query("SELECT u FROM AccountModel u WHERE u.is_deleted = false AND u.account_id = :account_id ")
    Optional<AccountModel> findByUid(@PathParam("account_id") String account_id);

    @Query("SELECT u FROM AccountModel u WHERE u.is_deleted = false AND u.user_id = :user_id ")
    Optional<AccountModel> findByUserId(@PathParam("user_id") String user_id);

    @Query("SELECT u FROM AccountModel u WHERE u.is_deleted = false AND u.account_number = :account_number ")
    Optional<AccountModel> findByAccountNumber(@PathParam("account_number") Integer account_number);

    @Query("SELECT s FROM AccountModel s WHERE s.is_deleted = false")
    Page<AccountModel> search(String text_search, Pageable pageable);
}
