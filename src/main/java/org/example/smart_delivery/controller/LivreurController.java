package org.example.smart_delivery.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.smart_delivery.dto.LivreurDTO;
import org.example.smart_delivery.service.livreur.LivreurService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Livreur",description = "Livreur management APIs")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/Livreurs")
public class LivreurController {
    private final LivreurService livreurService;

    @Operation(summary = "Create a new Livreur")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Livreur created"),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    @PostMapping
    public ResponseEntity<LivreurDTO> create(@Valid @RequestBody LivreurDTO dto){
        LivreurDTO created = livreurService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Update an existing Livreur by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Livreur updated"),
            @ApiResponse(responseCode = "404", description = "Livreur not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<LivreurDTO> update(@PathVariable String id, @Valid @RequestBody LivreurDTO dto) {
        LivreurDTO updated = livreurService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Get a Livreur by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Livreur returned"),
            @ApiResponse(responseCode = "404", description = "Livreur not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<LivreurDTO> getById(@PathVariable String id) {
        return ResponseEntity.ok(livreurService.getById(id));
    }

    @Operation(summary = "List Livreurs")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Livreurs returned")
    })
    @GetMapping
    public ResponseEntity<List<LivreurDTO>> getAll() {
        return ResponseEntity.ok(livreurService.getAll());
    }

    @Operation(summary = "Delete a Livreur by id")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Livreur deleted"),
            @ApiResponse(responseCode = "404", description = "Livreur not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        livreurService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
