package com.github.karina_denisevich.app.dao.repository;

import com.github.karina_denisevich.app.datamodel.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    User findByUsername(String userName);
}
