package org.example.smart_delivery.service.user;

import org.example.smart_delivery.dto.request.UserDTO;
import org.example.smart_delivery.dto.response.UserRespDTO;

import java.util.List;

public interface UserService {
    UserRespDTO create(UserDTO dto);
    UserRespDTO update(String id, UserDTO dto);
    UserRespDTO getById(String id);
    List<UserRespDTO> getAll();
    void delete(String id);
}
