package com.wcajisaca.accountService.entities;

import com.wcajisaca.accountService.entities.extras.StatusDefault;
import com.wcajisaca.accountService.enums.TypeAccount;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cuenta", schema = "account_movements")
public class Account extends StatusDefault implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "pk_cuenta",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "cuenta_id", updatable = false, nullable = false)
    private UUID accountId;
    @Column(name = "numero_cuenta", unique = true, nullable = false)
    @With
    private Integer accountNumber;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cuenta")
    @With
    private TypeAccount typeAccount;
    @Column(name = "saldo_inicial")
    @With
    private Double initialBalance;

    @Column(name = "persona_id")
    @With
    private UUID personId;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "persona_id",
            nullable = false,
            referencedColumnName = "persona_id",
            insertable = false, updatable = false,
            foreignKey = @ForeignKey(name = "fk_cuenta_persona"))
    private Person person;

    @OneToMany(mappedBy = "account")
    private Set<Movements> movimientos;
}