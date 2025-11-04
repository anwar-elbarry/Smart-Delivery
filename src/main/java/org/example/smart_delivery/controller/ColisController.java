package org.example.smart_delivery.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.smart_delivery.dto.ColisDTO;
import org.example.smart_delivery.service.colis.ColisService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Colis",description = "Coli management APIs")
@RestController
@RequestMapping("/api/colis")
public class ColisController {
    private final ColisService colisService;

    public ColisController(ColisService colisService) {
        this.colisService = colisService;
    }


    @Operation(summary = "Create a new Coli")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Coli created"),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    @PostMapping
    public ResponseEntity<ColisDTO> creatColi(@Valid @RequestBody ColisDTO colis){
        ColisDTO colisDTO = colisService.create(colis);
        return ResponseEntity.status(HttpStatus.CREATED).body(colisDTO);
    }

    @Operation(summary = "Update Coli")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Coli Updated"),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    @PutMapping("{coliId}")
    public ResponseEntity<ColisDTO> updateColi(@Valid @RequestBody ColisDTO coli ,@PathVariable String coliId){
        ColisDTO updated = colisService.update(coliId,coli);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(updated);
    }

    @Operation(summary = "assign coli to livreur")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Coli assigned"),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    @PutMapping("/assign")
    public ResponseEntity<Void> assignColi(@RequestParam String colisId ,@RequestParam String livreurId){
        colisService.Assign_col(colisId,livreurId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<ColisDTO>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir){

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(colisService.getAll(pageable));
    }

}
