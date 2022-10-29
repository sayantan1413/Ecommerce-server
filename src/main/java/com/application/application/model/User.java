package com.application.application.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "user_info")
public class User {
    @Id
    // @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence",
    // initialValue = 100000, allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
    // "user_sequence")
    @Column(name = "user_Id")
    private Long id;
    private String companyName;
    private String password;
    private String email;
    private String phoneNo;
    private long gstNo;
    private String address;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    private Boolean locked = false;
    private Boolean enabled = false;

    public User(Long id, String companyName, String password, String email, String phoneNo, long gstNo, String address,
            UserRole userRole) {
        this.id = id;
        this.companyName = companyName;
        this.password = password;
        this.email = email;
        this.phoneNo = phoneNo;
        this.gstNo = gstNo;
        this.address = address;
        this.userRole = userRole;
    }

}
