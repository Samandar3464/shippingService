package uz.pdp.shippingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.shippingservice.entity.AnnouncementDriver;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AnnouncementDriverRepository extends JpaRepository<AnnouncementDriver, UUID> {

    List<AnnouncementDriver> findAllByUserIdAndDeletedFalseOrderByCreatedTime(UUID user_id);
    List<AnnouncementDriver> findAllByUserIdAndActiveAndDeletedFalseOrderByCreatedTimeDesc(UUID user_id, boolean active);
    List<AnnouncementDriver> findAllByCurrentRegionIdAndCurrentCityIdAndCreatedTimeAfterOrderByCreatedTimeDesc(Integer currentRegion_id, Integer currentCity_id, LocalDateTime createdTime);
    boolean existsByUserIdAndActiveTrueAndDeletedFalse(UUID user_id);
    Optional<AnnouncementDriver> findByIdAndActiveAndDeletedFalse(UUID id, boolean active);
    Optional<AnnouncementDriver> findByIdAndDeletedFalse(UUID id);
    Optional<AnnouncementDriver> findByIdAndUserId(UUID id, UUID user_id);

    Optional<AnnouncementDriver> findByUserIdAndActiveAndDeletedFalseOrderByCreatedTimeDesc(UUID userId, boolean active);
}