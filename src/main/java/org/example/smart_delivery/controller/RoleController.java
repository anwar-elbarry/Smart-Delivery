package org.example.smart_delivery.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.smart_delivery.dto.request.RoleReqDTO;
import org.example.smart_delivery.dto.response.RoleResDTO;
import org.example.smart_delivery.service.role.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Roles", description = "Role management APIs")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    @Operation(summary = "Create a new Role")
    @ApiResponse(responseCode = "201", description = "Role created")
    @ApiResponse(responseCode = "400", description = "Validation error")

    @PostMapping
    public ResponseEntity<RoleResDTO> create(@Valid @RequestBody RoleReqDTO dto) {
        RoleResDTO created = roleService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Update an existing role by id")
    @ApiResponse(responseCode = "200", description = "Role updated")
    @ApiResponse(responseCode = "404", description = "Role not found")
    @PreAuthorize("hasRole('GESTIONNAIRE')")
    @PutMapping("/{id}")
    public ResponseEntity<RoleResDTO> update(@PathVariable String id, @Valid @RequestBody RoleReqDTO dto) {
        RoleResDTO updated = roleService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Get role by id")
    @ApiResponse(responseCode = "200", description = "Role found")
    @ApiResponse(responseCode = "404", description = "Role not found")
    @PreAuthorize("hasRole('GESTIONNAIRE')")
    @GetMapping("/{id}")
    public ResponseEntity<RoleResDTO> getById(@PathVariable String id) {
        return ResponseEntity.ok(roleService.getById(id));
    }

    @Operation(summary = "Get all roles")
    @ApiResponse(responseCode = "200", description = "List of roles")

    @GetMapping
    public ResponseEntity<List<RoleResDTO>> getAll() {
            return ResponseEntity.ok(roleService.getAll());
    }

    @Operation(summary = "Assign permission to role")
    @ApiResponse(responseCode = "200", description = "Permission assigned")
    @ApiResponse(responseCode = "404", description = "Role or Permission not found")
    @PreAuthorize("hasRole('GESTIONNAIRE')")
    @PostMapping("assign/{roleId}/permissions/{permissionId}")
    public ResponseEntity<RoleResDTO> assignPermission(@PathVariable String roleId, @PathVariable String permissionId) {
        return ResponseEntity.ok(roleService.assignPermissions(roleId, permissionId));
    }

    @Operation(summary = "Delete role by id")
    @ApiResponse(responseCode = "204", description = "Role deleted")
    @ApiResponse(responseCode = "404", description = "Role not found")
    @PreAuthorize("hasRole('GESTIONNAIRE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        roleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
