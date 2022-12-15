package com.utm.cslabs.hash;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<UserHash, Long> {
}
