package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleSumAmountDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(value =
            "SELECT obj FROM Sale obj JOIN FETCH obj.seller " +
            "WHERE obj.date >= :initialDate AND obj.date <= :finishDate AND UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :name, '%'))",
    countQuery = "SELECT COUNT(obj) FROM Sale obj JOIN obj.seller")
    Page<Sale> reportForDate(LocalDate initialDate, LocalDate finishDate, String name, Pageable pageable);

    @Query(value =
            "SELECT obj FROM Sale obj JOIN FETCH obj.seller ",
            countQuery = "SELECT COUNT(obj) FROM Sale obj JOIN obj.seller")
    Page<Sale> report(Pageable pageable);

    @Query("SELECT new com.devsuperior.dsmeta.dto.SaleSumAmountDTO(obj.seller.name, SUM(obj.amount)) FROM Sale obj JOIN obj.seller WHERE obj.date >= :initialDate AND obj.date <= :finishDate GROUP BY obj.seller.name")
    Page<SaleSumAmountDTO> summarySumForDate(LocalDate initialDate, LocalDate finishDate, Pageable pageable);

    @Query("SELECT new com.devsuperior.dsmeta.dto.SaleSumAmountDTO(obj.seller.name, SUM(obj.amount)) FROM Sale obj JOIN obj.seller GROUP BY obj.seller.name")
    Page<SaleSumAmountDTO> summarySum(Pageable pageable);
}
