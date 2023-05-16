package uz.pdp.shippingservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.shippingservice.entity.AnnouncementPassenger;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnnouncementPassengerResponseList {
    private UUID id;
    private String toRegion;
    private String fromRegion;
    private String fromCity;
    private String toCity;
    private String timeToSend;
    public static AnnouncementPassengerResponseList from(AnnouncementPassenger announcementPassenger) {
        return AnnouncementPassengerResponseList.builder()
                .id(announcementPassenger.getId())
                .fromRegion(announcementPassenger.getFromRegion().getName())
                .toRegion(announcementPassenger.getToRegion().getName())
                .fromCity(announcementPassenger.getFromCity().getName())
                .toCity(announcementPassenger.getToCity().getName())
                .timeToSend(announcementPassenger.getTimeToSend().toString())
                .build();
    }
}
