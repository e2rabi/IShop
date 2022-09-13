package com.errabi.ishop.repositories;

import com.errabi.ishop.entities.User;
import com.errabi.ishop.entities.LoginFailure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface LoginFailureRepository extends JpaRepository<LoginFailure,Integer> {
    List<LoginFailure> findAllByUserAndCreatedDateIsAfter(User user, Timestamp timestamp) ;


}
