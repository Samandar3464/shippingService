package uz.pdp.shippingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.shippingservice.entity.AnnouncementPassenger;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AnnouncementPassengerRepository extends JpaRepository<AnnouncementPassenger, UUID> {

    List<AnnouncementPassenger> findAllByFromRegionIdAndFromCityIdOrderByCreatedTimeDesc(Integer fromRegionId, Integer fromCityId);
    Optional<AnnouncementPassenger> findByIdAndActiveAndDeletedFalse(UUID id, boolean active);

    Optional<AnnouncementPassenger> findByIdAndDeletedFalse(UUID id);

    List<AnnouncementPassenger> findAllByUserIdAndDeletedFalseOrderByCreatedTimeDesc(UUID id);
    List<AnnouncementPassenger> findAllByUserIdAndActiveAndDeletedFalseOrderByCreatedTimeDesc(UUID id, boolean active);
}
