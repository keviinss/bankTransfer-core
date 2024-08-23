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

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

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
@Table(name = "accounts", schema = "bank")

public class AccountModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "pg-uuid")
    @GenericGenerator(name = "pg-uuid", strategy = "uuid2")
    @Column(name = "account_id", length = 50)
    private String account_id;

    @Column(name = "user_id", length = 50)
    private String user_id;

    @Column(name = "account_number")
    private Integer account_number;

    @NotEmpty(message = "account_type_is_required")
    @Column(name = "account_type", length = 10)
    private String account_type;

    @Column(name = "balance")
    private Integer balance;

    @Column(name = "currency")
    private String currency = "IDR";

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+07")
    private Date created_at;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+07")
    private Date updated_at;

    @JsonIgnore
    @Column(name = "is_deleted")
    private Boolean is_deleted = false;

    public AccountModel(String account_id, String user_id,
            Integer account_number, @NotEmpty(message = "account_type_is_required") String account_type, Integer balance, String currency,
            Date created_at, Date updated_at, Boolean is_deleted) {
        this.account_id = account_id;
        this.user_id = user_id;
        this.account_number = account_number;
        this.account_type = account_type;
        this.balance = balance;
        this.currency = currency;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.is_deleted = is_deleted;
    }

}
