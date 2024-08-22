package com.bank.danamon.repository;

import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bank.danamon.models.TransferModel;

public interface TransferRepository extends JpaRepository<TransferModel, String> {

    @Override
    @Query("SELECT a FROM TransferModel a WHERE a.is_deleted = false")
    List<TransferModel> findAll();

    @Query("SELECT u FROM TransferModel u WHERE u.is_deleted = false AND u.transfer_id = :transfer_id ")
    Optional<TransferModel> findByUid(@PathParam("transfer_id") String transfer_id);

    @Query("SELECT u FROM TransferModel u WHERE u.is_deleted = false AND u.sender_account_id = :sender_account_id ")
    Optional<TransferModel> findBySenderId(@PathParam("sender_account_id") String sender_account_id);

    @Query("SELECT u FROM TransferModel u WHERE u.is_deleted = false AND u.receiver_account_id = :receiver_account_id ")
    Optional<TransferModel> findByReceiverId(@PathParam("receiver_account_id") String receiver_account_id);

    @Query("SELECT s FROM TransferModel s WHERE s.is_deleted = false")
    Page<TransferModel> search(String text_search, Pageable pageable);
}
