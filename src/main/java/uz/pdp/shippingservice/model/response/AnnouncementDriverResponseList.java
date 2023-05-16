package uz.pdp.shippingservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.shippingservice.entity.AnnouncementDriver;
import uz.pdp.shippingservice.entity.Car;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementDriverResponseList {
    private UUID id;

    private String currentRegion;

    private String currentCity;

    private float maximumLoad;

    private float maximumLength;

    private float maximumLoadWidth;

    public static AnnouncementDriverResponseList from(AnnouncementDriver announcementDriver) {
        Car car = announcementDriver.getCar();
        return AnnouncementDriverResponseList
                .builder()
                .id(announcementDriver.getId())
                .currentRegion(announcementDriver.getCurrentRegion().getName())
                .currentCity(announcementDriver.getCurrentCity() == null ? null : announcementDriver.getCurrentCity().getName())
                .maximumLoad(car.getMaximumLoad())
                .maximumLength(car.getMaximumLength())
                .maximumLoadWidth(car.getMaximumLoadWidth())
                .build();
    }
}
