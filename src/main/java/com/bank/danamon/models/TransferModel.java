package com.bank.danamon.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "transfers", schema = "bank")

public class TransferModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "pg-uuid")
    @GenericGenerator(name = "pg-uuid", strategy = "uuid2")
    @Column(name = "transfer_id", length = 50)
    private String transfer_id;

    @NotEmpty(message = "sender_account_is_required")
    @Column(name = "sender_account_id", length = 100)
    private String sender_account_id;

    @NotEmpty(message = "receiver_account_is_required")
    @Column(name = "receiver_account_id", length = 100)
    private String receiver_account_id;

    @NotNull(message = "amount_is_required")
    @Column(name = "amount")
    private Integer amount;

    @Column(name = "currency", length = 3)
    private String currency = "IDR";

    @Column(name = "transfer_date", updatable = false)
    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+07")
    private Date transfer_date;

    @Column(name = "status", length = 10)
    private String status = "COMPLETE";

    @JsonIgnore
    @Column(name = "is_deleted")
    private Boolean is_deleted = false;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+07")
    private Date created_at;

    public TransferModel(String transfer_id, @NotEmpty(message = "sender_account_is_required") String sender_account_id,
            @NotEmpty(message = "receiver_account_is_required") String receiver_account_id,
            @NotNull(message = "amount_is_required") Integer amount, String currency, Date transfer_date, String status,
            Boolean is_deleted, Date created_at) {
        this.transfer_id = transfer_id;
        this.sender_account_id = sender_account_id;
        this.receiver_account_id = receiver_account_id;
        this.amount = amount;
        this.currency = currency;
        this.transfer_date = transfer_date;
        this.status = status;
        this.is_deleted = is_deleted;
        this.created_at = created_at;
    }

}
