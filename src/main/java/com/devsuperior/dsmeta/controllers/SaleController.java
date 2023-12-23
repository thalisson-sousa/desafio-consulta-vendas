package com.devsuperior.dsmeta.controllers;

import com.devsuperior.dsmeta.dto.SaleDTO;
import com.devsuperior.dsmeta.dto.SaleSumAmountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.services.SaleService;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
		SaleMinDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}

	@GetMapping(value = "/report")
	public ResponseEntity<Page<SaleDTO>> getReport(@RequestParam(required = true, defaultValue = "") String minDate, @RequestParam(required = true, defaultValue = "") String maxDate, @RequestParam(required = true, defaultValue = "") String name, Pageable pageable) {
		Page<SaleDTO> dto = service.findByDate(minDate, maxDate, name, pageable);
		return ResponseEntity.ok(dto);
	}

	@GetMapping(value = "/summary")
	public ResponseEntity<Page<SaleSumAmountDTO>> getSummary(@RequestParam(required = true, defaultValue = "") String minDate, @RequestParam(required = true, defaultValue = "") String maxDate, Pageable pageable) {
		Page<SaleSumAmountDTO> dto = service.searchSumAmount(minDate, maxDate, pageable);
		return ResponseEntity.ok(dto);
	}
}
