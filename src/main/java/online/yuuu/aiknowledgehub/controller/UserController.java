package online.yuuu.aiknowledgehub.controller;

import online.yuuu.aiknowledgehub.common.Result;
import online.yuuu.aiknowledgehub.dto.UpdateProfileRequest;
import online.yuuu.aiknowledgehub.entity.User;
import online.yuuu.aiknowledgehub.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/me")
    public Result<User> getCurrentUser(@RequestParam Integer userId) {
        return userService.getCurrentUser(userId);
    }

    @PutMapping("/me")
    public Result<User> updateProfile(@RequestParam Integer userId, @RequestBody UpdateProfileRequest request) {
        return userService.updateProfile(userId, request);
    }

    @PostMapping("/change-password")
    public Result<Void> changePassword(@RequestParam Integer userId, 
                                      @RequestParam String oldPassword, 
                                      @RequestParam String newPassword) {
        return userService.changePassword(userId, oldPassword, newPassword);
    }
}