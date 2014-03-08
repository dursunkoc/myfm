package com.aric.myfm.persistance;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.aric.myfm.domain.User;


public interface UserDao extends Dao<User, Long>, UserDetailsService {

	User findByName(String name);

}
