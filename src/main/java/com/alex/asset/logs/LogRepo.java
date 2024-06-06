package com.alex.asset.logs;

import com.alex.asset.logs.domain.Log;
import com.alex.asset.security.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface LogRepo extends JpaRepository<Log, Long> {


    List<Log> findAllByOrderByCreatedDesc();

    List<Log> findAllByUserOrderByCreatedDesc(User user);


    @Query("SELECT l " +
            "FROM Log l " +
            "WHERE l.created BETWEEN :startDate AND :endDate " +
            "AND l.user = :user" +
            " ORDER BY l.id DESC")
    List<Log> getLogsByDataRangeAndUser(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("user") User user);



    @Query("SELECT l " +
            "FROM Log l " +
            "WHERE l.created BETWEEN :startDate AND :endDate " +
            " ORDER BY l.id DESC")
    List<Log> getLogsByDataRange(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);


}
