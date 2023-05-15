package uz.pdp.shippingservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.shippingservice.entity.Status;

import java.util.Optional;
import java.util.UUID;

public interface StatusRepository extends JpaRepository<Status, UUID> {

    Optional<Status> findByUserId(UUID user_id);
}
