package com.wcajisaca.accountService.entities.extras;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@MappedSuperclass
@Getter
@Setter
public class StatusDefault implements Serializable {
    @Column(name = "estado")
    private Boolean status;
    @PrePersist
    public void prePersist() {
        status = Boolean.TRUE;
    }
}
