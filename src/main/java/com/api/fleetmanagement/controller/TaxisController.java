package com.api.fleetmanagement.controller;

import com.api.fleetmanagement.models.TaxisModel;
import com.api.fleetmanagement.repositories.TaxisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class TaxisController {
    @Autowired
    TaxisRepository taxisRepository;

    @GetMapping("/taxis")
    public ResponseEntity<Page<TaxisModel>> getAllTaxis(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(taxisRepository.findAll(pageable));
    }

    @GetMapping("/taxis/{id}")
    public ResponseEntity<TaxisModel> getTaxiById(@PathVariable Integer id) {
        Optional<TaxisModel> taxi = taxisRepository.findById(id);

        return taxi.map(taxisModel -> ResponseEntity.status(HttpStatus.OK).body(taxisModel)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


}