package com.alex.asset.logs;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepo extends JpaRepository<Log, Long> {
}
