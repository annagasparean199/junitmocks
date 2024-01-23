package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "salary")
    private Double salary;

    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "legal_entity")
    private Boolean legalEntity;

    @Column(name = "company")
    private String company;

    @Column(name = "name")
    private String name;

//    @OneToMany(mappedBy = "user_id", fetch = FetchType.LAZY)
//    private List<Sales> sales;
}