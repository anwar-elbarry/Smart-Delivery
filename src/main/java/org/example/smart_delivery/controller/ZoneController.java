package org.example.smart_delivery.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.smart_delivery.dto.request.ZoneDTO;
import org.example.smart_delivery.dto.response.ZoneRespDTO;
import org.example.smart_delivery.service.zone.ZoneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Zones" , description = "Zone management APIs")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/zones")
public class ZoneController {
    private final ZoneService zoneService;

    @Operation(summary = "Create a new Zone")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Zone created"),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    @PostMapping
    public ResponseEntity<ZoneRespDTO> create(@Valid @RequestBody ZoneDTO dto){
        ZoneRespDTO created = zoneService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Update an existing Zone by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Zone updated"),
            @ApiResponse(responseCode = "404", description = "Zone not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ZoneRespDTO> update(@PathVariable String id, @Valid @RequestBody ZoneDTO dto) {
        ZoneRespDTO updated = zoneService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Get a Zone by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Zone returned"),
            @ApiResponse(responseCode = "404", description = "Zone not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ZoneRespDTO> getById(@PathVariable String id) {
        return ResponseEntity.ok(zoneService.getById(id));
    }

    @Operation(summary = "List Zones")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Zones returned")
    })
    @GetMapping
    public ResponseEntity<List<ZoneRespDTO>> getAll() {
        return ResponseEntity.ok(zoneService.getAll());
    }

    @Operation(summary = "Delete a Zone by id")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Zone deleted"),
            @ApiResponse(responseCode = "404", description = "Zone not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        zoneService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
