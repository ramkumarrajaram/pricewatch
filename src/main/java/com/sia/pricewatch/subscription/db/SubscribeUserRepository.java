package com.sia.pricewatch.subscription.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SubscribeUserRepository extends JpaRepository<SubscribeUserEntity, String> {

    List<SubscribeUserEntity> findByDepartureDateAfter(LocalDate date);

    List<SubscribeUserEntity> findByDeviceUid(String uid);
}
