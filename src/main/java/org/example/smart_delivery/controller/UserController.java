package org.example.smart_delivery.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.smart_delivery.dto.request.UserDTO;
import org.example.smart_delivery.dto.response.UserRespDTO;
import org.example.smart_delivery.service.user.UserService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Users",description = "User management APIs")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;


    @Operation(summary = "Create a new User")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User created"),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    @PreAuthorize("hasRole('GESTIONNAIRE')")
    @PostMapping
    public ResponseEntity<UserRespDTO> create(@Valid @RequestBody UserDTO dto){
        UserRespDTO created = userService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }


    @Operation(summary = "Update an existing user by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User updated"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PreAuthorize("hasRole('GESTIONNAIRE')")
    @PutMapping("/{id}")
    public ResponseEntity<UserRespDTO> update(@PathVariable String id, @Valid @RequestBody UserDTO dto) {
        UserRespDTO updated = userService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Get a user by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User returned"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PreAuthorize("hasRole('GESTIONNAIRE')")
    @GetMapping("/{id}")
    public ResponseEntity<UserRespDTO> getById(@PathVariable String id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @Operation(summary = "List users")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Users returned")
    })
//    @PreAuthorize("hasRole('GESTIONNAIRE')")
    @GetMapping
    public ResponseEntity<List<UserRespDTO>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @Operation(summary = "Delete a user by id")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "User deleted"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PreAuthorize("hasRole('GESTIONNAIRE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('GESTIONNAIRE')")
    @GetMapping("/search")
    public ResponseEntity<Page<UserRespDTO>> search(@RequestParam String q, @ParameterObject Pageable pageable) {
        return ResponseEntity.ok(userService.search(q, pageable));
    }
}
