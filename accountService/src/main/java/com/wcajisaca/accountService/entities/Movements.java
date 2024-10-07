package com.wcajisaca.accountService.entities;

import com.wcajisaca.accountService.entities.extras.StatusDefault;
import com.wcajisaca.accountService.enums.TypeMovement;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movimientos",schema = "account_movements")
public class Movements extends StatusDefault implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "pk_movimiento",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "movimiento_id", updatable = false, nullable = false)
    private UUID movementId;

    @Column(name = "fecha_movimiento")
    private LocalDate movementDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_movimiento")
    private TypeMovement typeMovement;

    @Column(name = "saldo")
    @With
    private Double balance;

    @Column(name = "valor")
    private Double value;

    @Column(name = "cuenta_id")
    private UUID accountId;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "cuenta_id",
            nullable = false,
            referencedColumnName = "cuenta_id",
            insertable = false,updatable = false,
            foreignKey = @ForeignKey(name = "fk_movement_account"))
    private Account account;
}