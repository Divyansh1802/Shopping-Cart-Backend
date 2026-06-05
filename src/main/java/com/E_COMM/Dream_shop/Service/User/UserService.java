package com.E_COMM.Dream_shop.Service.User;

import com.E_COMM.Dream_shop.Repository.RoleRepository;
import com.E_COMM.Dream_shop.Repository.UserRepository;
import com.E_COMM.Dream_shop.exceptions.ResourceNotFoundException;
import com.E_COMM.Dream_shop.model.Role;
import com.E_COMM.Dream_shop.model.User;
import com.E_COMM.Dream_shop.request.CreateUserRequest;
import com.E_COMM.Dream_shop.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository  roleRepository;


    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("user not found")
        );
    }

    @Override
    public User createUser(CreateUserRequest request){
        if(userRepository.existsByEmail(request.getEmail())){
            throw new ResourceNotFoundException("email already exists");
        }
        Role userRole = roleRepository.findByName("ROLE_USER");
        if(userRole == null){
            throw new ResourceNotFoundException("role not found");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        user.getRoles().add(userRole);
        return userRepository.save(user);
    }

    @Override
    public User userUpdate(UserUpdateRequest request, Long UserId) {
        return userRepository.findById(UserId).map(i ->{
            i.setFirstName(request.getFirstName());
            i.setLastName(request.getLastName());
            return userRepository.save(i);
        }).orElseThrow(() -> new ResourceNotFoundException("user not found"));
    }

    @Override
    public void deleteUser(Long UserId) {
        userRepository.findById(UserId).ifPresentOrElse(userRepository :: delete,
                () -> {throw new  ResourceNotFoundException("user not found");
        });
    }
}
