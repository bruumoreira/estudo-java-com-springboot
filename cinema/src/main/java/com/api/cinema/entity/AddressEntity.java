package com.api.cinema.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "tb_address")

public class AddressEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_address")
    private UUID idAddress;

    @Column(nullable = false, unique = true, length = 350)
    private String addressStreet;

    @Column(nullable = false, unique = true, length = 350)
    private String addressCity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idUser", referencedColumnName = "id_user")
    private UserEntity user;

    public UUID getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(UUID idAddress) {
        this.idAddress = idAddress;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
