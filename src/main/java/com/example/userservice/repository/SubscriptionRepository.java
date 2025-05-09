package com.example.userservice.repository;

import com.example.userservice.model.Subscription;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    @Query("""
  SELECT s.serviceName AS name, COUNT(s) AS count
  FROM Subscription s
  GROUP BY s.serviceName
  ORDER BY COUNT(s) DESC
""")
    List<TopSubscription> findTopSubscriptions(Pageable pageable);

}
