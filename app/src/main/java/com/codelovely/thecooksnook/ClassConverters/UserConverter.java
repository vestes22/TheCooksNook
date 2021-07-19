package com.codelovely.thecooksnook.ClassConverters;

import com.codelovely.thecooksnook.data.entities.User;
import com.codelovely.thecooksnook.models.UserModel;

public class UserConverter {

    public static User convertToUser(UserModel userModel) {
        User user = new User();
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setUserId(userModel.getUserId());

        return user;
    }

    public static UserModel convertToUserModel(User user) {
        UserModel userModel = new UserModel();
        userModel.setFirstName(user.getFirstName());
        userModel.setLastName(user.getLastName());
        userModel.setUserId(user.getUserId());

        return userModel;
    }
}
