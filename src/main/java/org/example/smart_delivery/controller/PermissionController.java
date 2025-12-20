package org.example.smart_delivery.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.smart_delivery.dto.request.PermissionReqDTO;
import org.example.smart_delivery.dto.response.PermissionResDTO;
import org.example.smart_delivery.entity.enums.UserRole;
import org.example.smart_delivery.service.permission.PermissionService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Permissions", description = "Permission management APIs")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/permissions")
public class PermissionController {

    private final PermissionService permissionService;

    @Operation(summary = "Create a new Permission")
    @ApiResponse(responseCode = "201", description = "Permission created")
    @ApiResponse(responseCode = "400", description = "Validation error")
    @PreAuthorize("hasRole('GESTIONNAIRE')")
    @PostMapping
    public ResponseEntity<PermissionResDTO> create(@Valid @RequestBody PermissionReqDTO dto) {
        PermissionResDTO created = permissionService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Update an existing permission by id")
    @ApiResponse(responseCode = "200", description = "Permission updated")
    @ApiResponse(responseCode = "404", description = "Permission not found")
    @PreAuthorize("hasRole('GESTIONNAIRE')")
    @PutMapping("/{id}")
    public ResponseEntity<PermissionResDTO> update(@PathVariable String id, @Valid @RequestBody PermissionReqDTO dto) {
        PermissionResDTO updated = permissionService.take(id, dto);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Get permission by id")
    @ApiResponse(responseCode = "200", description = "Permission found")
    @ApiResponse(responseCode = "404", description = "Permission not found")
    @PreAuthorize("hasRole('GESTIONNAIRE')")
    @GetMapping("/{id}")
    public ResponseEntity<PermissionResDTO> getById(@PathVariable String id) {
        return ResponseEntity.ok(permissionService.getById(id));
    }

    @Operation(summary = "Get all permissions")
    @ApiResponse(responseCode = "200", description = "List of permissions")
    @PreAuthorize("hasRole('GESTIONNAIRE')")
    @GetMapping
    public ResponseEntity<Page<PermissionResDTO>> getAll(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(permissionService.getAll(pageable));
    }

    @Operation(summary = "Delete permission by id")
    @ApiResponse(responseCode = "204", description = "Permission deleted")
    @ApiResponse(responseCode = "404", description = "Permission not found")
    @PreAuthorize("hasRole('GESTIONNAIRE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        permissionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Assign role to permission")
    @ApiResponse(responseCode = "200", description = "Role assigned")
    @ApiResponse(responseCode = "404", description = "Permission not found")
    @PreAuthorize("hasRole('GESTIONNAIRE')")
    @PostMapping("/{id}/assign")
    public ResponseEntity<Boolean> assign(@PathVariable String id, @RequestParam UserRole role) {
        return ResponseEntity.ok(permissionService.assign(role, id));
    }
}

