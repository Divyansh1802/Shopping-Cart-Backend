package com.E_COMM.Dream_shop.Controller;

import com.E_COMM.Dream_shop.Repository.RoleRepository;
import com.E_COMM.Dream_shop.Response.ApiResponse;
import com.E_COMM.Dream_shop.Service.User.UserService;
import com.E_COMM.Dream_shop.model.User;
import com.E_COMM.Dream_shop.request.CreateUserRequest;
import com.E_COMM.Dream_shop.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("${api.prefix}/User")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/user=/{userId}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId) {
        try{
            User user = userService.getUserById(userId);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Success", user));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", e.getMessage()));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request){
        try{
            User user = userService.createUser(request);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Success", user));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", e.getMessage()));
        }
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UserUpdateRequest request, @PathVariable Long userId){
        try{
            User user = userService.userUpdate(request,userId);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Success", user));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", e.getMessage()));
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId){
        try{
            userService.deleteUser(userId);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successfully deleted", null));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", e.getMessage()));
        }
    }

}
