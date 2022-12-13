package com.api.parkingcontrol.controller;

import com.api.parkingcontrol.exception.BusinessException;
import com.api.parkingcontrol.model.User;
import com.api.parkingcontrol.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Api(tags = "user")
@RestController
@CrossOrigin(origins = "*", maxAge = 3000)
@RequestMapping("/user")
public class UserController {

    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Object> saveUser(@RequestBody @NotNull User user) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
        } catch (BusinessException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getBusinessMessage());
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Object> getAllUser(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.findAll(pageable));
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "id") UUID id) {
        try {
            userService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully.");
        } catch (BusinessException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getBusinessMessage());
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
    }
}
