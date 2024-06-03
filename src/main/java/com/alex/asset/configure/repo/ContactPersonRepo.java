package com.alex.asset.configure.repo;

import com.alex.asset.configure.domain.ContactPerson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactPersonRepo extends JpaRepository<ContactPerson, Long> {
}
