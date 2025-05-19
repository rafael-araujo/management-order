package com.hexagonal.template.infrastructure.persistence.entity;


import com.hexagonal.template.application.utils.ConverterUtils;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "td_order")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProductEntity> ordersAndProducts;

    @Column(name = "amount")
    private BigDecimal amount;

    //@Column(name = "signature", columnDefinition = "BYTEA(32)") // Para PostgreSQL
    @Column(name = "signature", columnDefinition = "BINARY(32)")
    @Convert(converter = ConverterUtils.class)
    private String signature;

    @Column(name = "create_date")
    private Instant createDate;

    @Column(name = "update_date")
    private Instant updateDate;

    @Column(name = "remove_date")
    private Instant removeDate;

    @Column(name = "deleted")
    private Boolean deleted;

    @Column(name = "user_id")
    private Long userId;

}
