package org.example.smart_delivery.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.smart_delivery.dto.request.LivreurDTO;
import org.example.smart_delivery.dto.response.LivreurRespDTO;
import org.example.smart_delivery.service.livreur.LivreurService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('LIVREUR_CREATE')")
    @PostMapping
    public ResponseEntity<LivreurRespDTO> create(@Valid @RequestBody LivreurDTO dto){
        LivreurRespDTO created = livreurService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Update an existing Livreur by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Livreur updated"),
            @ApiResponse(responseCode = "404", description = "Livreur not found")
    })
    @PreAuthorize("hasAuthority('LIVREUR_UPDATE')")
    @PutMapping("/{id}")
    public ResponseEntity<LivreurRespDTO> update(@PathVariable String id, @Valid @RequestBody LivreurDTO dto) {
        LivreurRespDTO updated = livreurService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Get a Livreur by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Livreur returned"),
            @ApiResponse(responseCode = "404", description = "Livreur not found")
    })
    @PreAuthorize("hasAuthority('LIVREUR_READ')")
    @GetMapping("/{id}")
    public ResponseEntity<LivreurRespDTO> getById(@PathVariable String id) {
        return ResponseEntity.ok(livreurService.getById(id));
    }

    @Operation(summary = "List Livreurs")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Livreurs returned")
    })
    @PreAuthorize("hasAuthority('LIVREUR_READ')")
    @GetMapping
    public ResponseEntity<List<LivreurRespDTO>> getAll() {
        return ResponseEntity.ok(livreurService.getAll());
    }

    @Operation(summary = "Delete a Livreur by id")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Livreur deleted"),
            @ApiResponse(responseCode = "404", description = "Livreur not found")
    })
    @PreAuthorize("hasAuthority('LIVREUR_DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        livreurService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
