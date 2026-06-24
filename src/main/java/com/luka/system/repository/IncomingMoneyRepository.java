package com.luka.system.repository;

import com.luka.system.model.IncomingMoney;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomingMoneyRepository extends JpaRepository<IncomingMoney, Long> {
}