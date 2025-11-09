package org.example.smart_delivery.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.smart_delivery.dto.request.ProduitDTO;
import org.example.smart_delivery.dto.response.ProduitRespDTO;
import org.example.smart_delivery.service.produit.ProduitService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Produits" , description = "Produits managment APIs")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/produits")
public class ProduitController {
    private final ProduitService produitService;

    @Operation(summary = "Create a new produit")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "produit created"),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    @PostMapping
    public ResponseEntity<ProduitRespDTO> create(@Validated @RequestBody ProduitDTO produitDTO){
        ProduitRespDTO created =  produitService.create(produitDTO);
        return  ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Get all produits")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "produits getted"),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    @GetMapping
    public ResponseEntity<Page<ProduitRespDTO>> getAll(@ParameterObject Pageable pageable){

        Page<ProduitRespDTO> page = produitService.getAll(pageable);
        return ResponseEntity.ok(page);
    }

    @Operation(summary = "Update produit")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "produit updated"),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    @PutMapping({"{produitId}"})
    public ResponseEntity<ProduitRespDTO> update(@Validated @PathVariable String produitId , @RequestBody ProduitDTO produitDTO){
        ProduitRespDTO updated =  produitService.update(produitId,produitDTO);
        return  ResponseEntity.status(HttpStatus.ACCEPTED).body(updated);
    }

    @Operation(summary = "get produit by id")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "produit getted"),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    @GetMapping("{produitId}")
    public ResponseEntity<ProduitRespDTO> getProduit(@PathVariable String produitId){
        ProduitRespDTO produitDTO = produitService.getById(produitId);
        return ResponseEntity.ok(produitDTO);
    }

    @Operation(summary = "delete produit")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "produit deleted"),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    @DeleteMapping("{produitId}")
    public ResponseEntity<ProduitRespDTO> delete(@PathVariable String produitId){
        produitService.delete(produitId);
        return ResponseEntity.noContent().build();
    }

}


