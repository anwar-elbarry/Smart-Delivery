package org.example.smart_delivery.service.user;

import org.example.smart_delivery.dto.request.UserDTO;
import org.example.smart_delivery.dto.response.UserRespDTO;
import org.example.smart_delivery.entity.User;
import org.example.smart_delivery.exception.ResourceNotFoundException;
import org.example.smart_delivery.mapper.request.UserMapper;
import org.example.smart_delivery.mapper.response.UserRespMapper;
import org.example.smart_delivery.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserRespMapper userRespMapper;

    @Override
    public UserRespDTO create(UserDTO dto) {
        User entity = userMapper.toEntity(dto);
        User saved = userRepository.save(entity);
        return userRespMapper.toRespDto(saved);
    }

    @Override
    public UserRespDTO update(String id, UserDTO dto) {
        User entity = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + id));
        userMapper.updateEntityFromDto(dto, entity);
        User saved = userRepository.save(entity);
        return userRespMapper.toRespDto(saved);
    }

    @Override
    public UserRespDTO getById(String id) {
        User entity = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: ", id));
        return userRespMapper.toRespDto(entity);
    }

    @Override
    public UserRespDTO getByEmail(String email) {
        User entity = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email:"+email));
        return userRespMapper.toRespDto(entity);
    }

    @Override
    public List<UserRespDTO> getAll() {
        return userRepository.findAll()
                .stream()
                .map(userRespMapper::toRespDto)
                .toList();
    }

    @Override
    public void delete(String id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User" + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public Page<UserRespDTO> search(String q, Pageable pageable) {
        return userRepository.search(q, pageable).map(userRespMapper::toRespDto);
    }
}
