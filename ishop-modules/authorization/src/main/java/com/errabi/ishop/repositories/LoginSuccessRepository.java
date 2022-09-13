package com.errabi.ishop.repositories;

import com.errabi.ishop.entities.LoginSuccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginSuccessRepository extends JpaRepository<LoginSuccess,Integer> {
}
