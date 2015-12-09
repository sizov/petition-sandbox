package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Subscriptions;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Subscriptions entity.
 */
public interface SubscriptionsRepository extends JpaRepository<Subscriptions,Long> {

    @Query("select subscriptions from Subscriptions subscriptions where subscriptions.user.login = ?#{principal.username}")
    List<Subscriptions> findByUserIsCurrentUser();

}
