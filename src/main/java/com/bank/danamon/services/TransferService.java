package com.bank.danamon.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bank.danamon.models.TransferModel;
import com.bank.danamon.repository.TransferRepository;

@Service
public class TransferService {

    @Autowired
    private TransferRepository transferRepository;

    public TransferModel save(TransferModel transferModel) {
        return transferRepository.save(transferModel);
    }

    public TransferModel findOne(String transfer_id) {
        Optional<TransferModel> transferModel = transferRepository.findByUid(transfer_id);
        if (!transferModel.isPresent()) {
            return null;
        }
        return transferModel.get();
    }

    public TransferModel findBySenderId(String sender_account_id) {
        Optional<TransferModel> transferModel = transferRepository.findBySenderId(sender_account_id);
        if (!transferModel.isPresent()) {
            return null;
        }
        return transferModel.get();
    }

    public TransferModel findByReceiverId(String receiver_account_id) {
        Optional<TransferModel> transferModel = transferRepository.findByReceiverId(receiver_account_id);
        if (!transferModel.isPresent()) {
            return null;
        }
        return transferModel.get();
    }

    public TransferModel softDelete(TransferModel transferModel) {
        transferRepository.save(transferModel);
        return transferModel;
    }

    public List<TransferModel> findAll() {
        List<TransferModel> aList = transferRepository.findAll();
        if (aList.isEmpty()) {
            aList = null;
        }
        return aList;
    }

    public Page<TransferModel> search(String keyword, Pageable pageable) {
        return transferRepository.search(keyword, pageable);
    }
}
