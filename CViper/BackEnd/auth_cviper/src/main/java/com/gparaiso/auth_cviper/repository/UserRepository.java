package com.gparaiso.auth_cviper.repository;

import com.gparaiso.auth_cviper.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID> {

}
