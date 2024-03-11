package com.alex.asset.logs;

import com.alex.asset.logs.domain.Log;
import com.alex.asset.security.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogRepo extends JpaRepository<Log, Long> {


    List<Log> findAllByOrderByCreatedDesc();
    List<Log> findAllByUserOrderByCreatedDesc(User user);
}
