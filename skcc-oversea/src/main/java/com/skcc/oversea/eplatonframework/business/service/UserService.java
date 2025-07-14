package com.skcc.oversea.eplatonframework.business.service;

import com.skcc.oversea.user.entity.User;
import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(String userId);

    User save(User user);

    void deleteById(String userId);

    boolean existsById(String userId);
}