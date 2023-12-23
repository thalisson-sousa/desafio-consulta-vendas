package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SaleDTO;
import com.devsuperior.dsmeta.dto.SaleSumAmountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public Page<SaleDTO> findByDate(String minDate, String maxDate, String name, Pageable pageable) {

		if (maxDate.isEmpty() && minDate.isEmpty()) {
			Page<Sale> result = repository.report( pageable);
			return result.map(x -> new SaleDTO(x));
		} else {

			if (name == null) {
				name = "";
			}

			LocalDate initialDate;
			LocalDate finishDate;

			if (maxDate.isEmpty()) {
				finishDate = transformMaxdate();
			} else {
				finishDate = LocalDate.parse(maxDate);
			}

			if (minDate.isEmpty()) {
				initialDate = transformMindate(finishDate, maxDate);
			} else {
				initialDate = LocalDate.parse(minDate);
			}

			Page<Sale> result = repository.reportForDate(initialDate, finishDate, name, pageable);
			return result.map(x -> new SaleDTO(x));
		}
	}

	public Page<SaleSumAmountDTO> searchSumAmount(String minDate, String maxDate, Pageable pageable) {

		if (maxDate.isEmpty() && minDate.isEmpty()) {
			Page<SaleSumAmountDTO> result = repository.summarySum(pageable);
			return result;
		} else {
			LocalDate initialDate;
			LocalDate finishDate;

			if (maxDate.isEmpty()) {
				finishDate = transformMaxdate();
			} else {
				finishDate = LocalDate.parse(maxDate);
			}

			if (minDate.isEmpty()) {
				initialDate = transformMindate(finishDate, maxDate);
			} else {
				initialDate = LocalDate.parse(minDate);
			}

			Page<SaleSumAmountDTO> result = repository.summarySumForDate(initialDate, finishDate, pageable);
			return result;
		}
	}

	public LocalDate transformMaxdate() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		return today;
	}

	public LocalDate transformMindate(LocalDate finishDate, String maxDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		finishDate = LocalDate.parse(maxDate, formatter);
		LocalDate result = finishDate.minusYears(1L);
		return result;
	}
}
