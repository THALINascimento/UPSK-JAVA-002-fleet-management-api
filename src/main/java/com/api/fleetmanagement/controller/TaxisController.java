package com.api.fleetmanagement.controller;

import com.api.fleetmanagement.models.TaxisModel;
import com.api.fleetmanagement.models.TrajectoriesModel;
import com.api.fleetmanagement.repositories.TaxisRepository;
import com.api.fleetmanagement.repositories.TrajectoriesRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
public class TaxisController {
    @Autowired
    TaxisRepository taxisRepository;
    @Autowired
    TrajectoriesRepository trajectoriesRepository;

    @Operation(summary = "Get all taxis")
    @GetMapping("/taxis")
    public ResponseEntity<Page<TaxisModel>> getAllTaxis(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(taxisRepository.findAll(pageable));
    }

    @Operation(summary = "Get a taxis by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the taxi",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaxisModel.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Taxi not found",
                    content = @Content)})
    @GetMapping("/taxis/{id}")
    public ResponseEntity<List<TrajectoriesModel>> getTaxiById(@PathVariable Integer id, Pageable pageable) {
        Optional<TaxisModel> taxiOptional = taxisRepository.findById(id);

        if (taxiOptional.isPresent()) {
            TaxisModel taxiModel = taxiOptional.get();
            Page<TrajectoriesModel> trajectoryInfoList = trajectoriesRepository.findTrajectoriesByTaxiId(id, pageable);

            if (trajectoryInfoList != null) {
                List<TrajectoriesModel> trajectories = trajectoryInfoList.getContent();
                return ResponseEntity.status(HttpStatus.OK).body(trajectories);
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(Collections.emptyList());
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

        @Operation(summary = "Get a taxis last trajectory")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the taxi",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaxisModel.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Taxi not found",
                    content = @Content)})

    @GetMapping("/taxis/last-locations")
    public ResponseEntity<Page<TrajectoriesModel>> getTaxiLastLocations(Pageable pageable) {
        Page<TrajectoriesModel> trajectoryLastLocations = trajectoriesRepository.findLastLocations(pageable);
        return ResponseEntity.ok(trajectoryLastLocations);
    }
}