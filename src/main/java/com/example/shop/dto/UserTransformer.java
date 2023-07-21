package com.example.shop.dto;

import com.example.shop.entity.User;

public class UserTransformer {

    public static UserDto convertToDto(User user){
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getAddress(),
                user.getPhone()
        );
    }

}
