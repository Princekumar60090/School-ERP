package com.schoolerp.SchoolERP.repository;

import com.schoolerp.SchoolERP.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmailIgnoreCase(String email);

    boolean existsByUsername(String username);
    boolean existsByEmailIgnoreCase(String email);
}
