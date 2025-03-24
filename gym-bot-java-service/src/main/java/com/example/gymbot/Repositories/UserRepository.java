package com.example.gymbot.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.gymbot.Entities.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
