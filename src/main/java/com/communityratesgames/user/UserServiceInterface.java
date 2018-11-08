package com.communityratesgames.user;

import java.util.List;

public interface UserServiceInterface {

    List<UserEntity> findAllUsers();

    UserEntity findUserById(Long id);

    UserEntity findUserByUserName(String username);

    UserEntity findUserByEmail(String email);

    UserEntity createNewUser(UserEntity user);

}
