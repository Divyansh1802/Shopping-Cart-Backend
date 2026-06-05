package com.E_COMM.Dream_shop.Service.User;

import com.E_COMM.Dream_shop.model.User;
import com.E_COMM.Dream_shop.request.CreateUserRequest;
import com.E_COMM.Dream_shop.request.UserUpdateRequest;

public interface IUserService {
    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User userUpdate(UserUpdateRequest request, Long UserId);
    void deleteUser(Long UserId);

}
