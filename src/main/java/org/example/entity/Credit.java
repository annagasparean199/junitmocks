package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Credit", schema = "public")
public class Credit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "price_per_month")
    private BigDecimal pricePerMonth;

    @Column(name = "months")
    private Integer months;

    @ManyToOne
    private Sales sales;

    @Column(name = "payment_date")
    private Date paymentDate;
}
