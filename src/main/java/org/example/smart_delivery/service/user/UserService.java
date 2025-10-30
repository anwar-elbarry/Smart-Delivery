package org.example.smart_delivery.service.user;

import org.example.smart_delivery.dto.UserDTO;
import java.util.List;

public interface UserService {
    UserDTO create(UserDTO dto);
    UserDTO update(String id, UserDTO dto);
    UserDTO getById(String id);
    List<UserDTO> getAll();
    void delete(String id);
}
