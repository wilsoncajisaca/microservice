package com.wcajisaca.challengeAccount.entities;

import com.wcajisaca.challengeAccount.enums.TypeAccount;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cuenta", schema = "account_movements")
public class Account {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "pk_cuenta",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "cuenta_id", updatable = false, nullable = false)
    private UUID accountId;
    @Column(name = "numero_cuenta", unique = true, nullable = false)
    private Integer accountNumber;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cuenta")
    private TypeAccount typeAccount;
    @Column(name = "saldo_inicial")
    @With
    private Double initialBalance;
    @Column(name = "estado")
    private Boolean status;

    @Column(name = "persona_id")
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