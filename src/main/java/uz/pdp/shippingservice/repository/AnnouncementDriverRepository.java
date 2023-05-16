package uz.pdp.shippingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.shippingservice.entity.AnnouncementDriver;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AnnouncementDriverRepository extends JpaRepository<AnnouncementDriver, UUID> {

    List<AnnouncementDriver> findAllByUserIdAndDeletedFalseOrderByCreatedTimeDesc(UUID user_id);
    List<AnnouncementDriver> findAllByUserIdAndActiveAndDeletedFalseOrderByCreatedTimeDesc(UUID user_id, boolean active);
    List<AnnouncementDriver> findAllByCurrentRegionIdAndCurrentCityIdOrderByCreatedTimeDesc(Integer currentRegion_id, Integer currentCity_id);
    boolean existsByUserIdAndActiveTrueAndDeletedFalse(UUID user_id);
    Optional<AnnouncementDriver> findByIdAndActiveAndDeletedFalse(UUID id, boolean active);
    Optional<AnnouncementDriver> findByIdAndDeletedFalse(UUID id);
}
