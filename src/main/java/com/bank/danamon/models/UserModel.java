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
@Table(name = "users", schema = "bank")

public class UserModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "pg-uuid")
    @GenericGenerator(name = "pg-uuid", strategy = "uuid2")
    @Column(name = "user_id", length = 200)
    private String user_id;

    @JsonIgnore
    @NotEmpty(message = "first_name_is_required")
    @Column(name = "first_name", length = 100)
    private String first_name;

    @JsonIgnore
    @NotEmpty(message = "last_name_is_required")
    @Column(name = "last_name", length = 100)
    private String last_name;

    @NotEmpty(message = "email_is_required")
    @Column(name = "email", length = 100)
    private String email;

    @NotEmpty(message = "phone_number_is_required")
    @Column(name = "phone_number", length = 50)
    private String phone_number;

    @NotEmpty(message = "address_is_required")
    @Column(name = "address")
    private String address;

    @NotNull(message = "date_of_birth_is_required")
    @Column(name = "date_of_birth")
    private String date_of_birth;

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

    public UserModel(String user_id, @NotEmpty(message = "first_name_is_required") String first_name,
            @NotEmpty(message = "last_name_is_required") String last_name,
            @NotEmpty(message = "email_is_required") String email,
            @NotEmpty(message = "phone_number_is_required") String phone_number,
            @NotEmpty(message = "address_is_required") String address,
            @NotNull(message = "date_of_birth_is_required") String date_of_birth, Date created_at, Date updated_at,
            Boolean is_deleted) {
        this.user_id = user_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.phone_number = phone_number;
        this.address = address;
        this.date_of_birth = date_of_birth;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.is_deleted = is_deleted;
    }

}
