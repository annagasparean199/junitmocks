package org.example.interfaces;

import org.example.entity.Credit;

import java.util.Optional;

public interface CreditInterface {

    Optional<Credit> findById(Long id);

}

