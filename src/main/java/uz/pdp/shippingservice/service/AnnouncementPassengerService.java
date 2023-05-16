package uz.pdp.shippingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import uz.pdp.shippingservice.entity.AnnouncementPassenger;
import uz.pdp.shippingservice.entity.User;
import uz.pdp.shippingservice.entity.api.ApiResponse;
import uz.pdp.shippingservice.exception.AnnouncementAlreadyExistException;
import uz.pdp.shippingservice.exception.AnnouncementNotFoundException;
import uz.pdp.shippingservice.model.request.AnnouncementPassengerDto;
import uz.pdp.shippingservice.model.request.GetByFilter;
import uz.pdp.shippingservice.model.response.AnnouncementPassengerResponse;
import uz.pdp.shippingservice.model.response.AnnouncementPassengerResponseList;
import uz.pdp.shippingservice.model.response.UserResponseDto;
import uz.pdp.shippingservice.repository.AnnouncementDriverRepository;
import uz.pdp.shippingservice.repository.AnnouncementPassengerRepository;
import uz.pdp.shippingservice.repository.CityRepository;
import uz.pdp.shippingservice.repository.RegionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static uz.pdp.shippingservice.constants.Constants.*;


@Service
@RequiredArgsConstructor
public class AnnouncementPassengerService {

    private final RegionRepository regionRepository;
    private final CityRepository cityRepository;
    private final UserService userService;
    private final AnnouncementPassengerRepository announcementPassengerRepository;
    private final AnnouncementDriverRepository announcementDriverRepository;

    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse add(AnnouncementPassengerDto announcementPassengerDto) {
        User user = userService.checkUserExistByContext();
//        if (announcementDriverRepository.existsByUserIdAndActiveTrueAndDeletedFalse(user.getId())) {
//            throw new AnnouncementAvailable(ANNOUNCEMENT_DRIVER_ALREADY_EXIST);
//        }
//        if (existByUserIdAndActiveTrueAndDeletedFalse(user.getId())) {
//            throw new AnnouncementAlreadyExistException(ANNOUNCEMENT_PASSENGER_ALREADY_EXIST);
//        }
        List<AnnouncementPassenger> announcementPassenger1 = getAnnouncementPassenger(user, true);
        if (announcementPassenger1.size()>5){
            throw new AnnouncementAlreadyExistException(YOU_CAN_ONLY_SET_5_ANNOUNCEMENT);
        }
        AnnouncementPassenger announcementPassenger = fromRequest(announcementPassengerDto, user);
        announcementPassengerRepository.save(announcementPassenger);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getAnnouncementPassengerByFilter(GetByFilter getByFilter) {
        List<AnnouncementPassenger> byFilter = announcementPassengerRepository
                .findAllByFromRegionIdAndFromCityIdOrderByCreatedTimeDesc(
                        getByFilter.getCurrentRegionId(),
                        getByFilter.getCurrentCityId());
        List<AnnouncementPassengerResponseList> passengerResponses = new ArrayList<>();
        byFilter.forEach(obj -> passengerResponses.add(AnnouncementPassengerResponseList.from(obj)));
        return new ApiResponse(passengerResponses, true);
    }


    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getDriverAnnouncementByIdAndActiveTrue(UUID id) {
        AnnouncementPassenger active = getByIdAndActiveAndDeletedFalse(id, true);
        User user = userService.checkUserExistById(active.getUser().getId());
        UserResponseDto userResponseDto = userService.fromUserToResponse(user);
        AnnouncementPassengerResponse passengerResponse = AnnouncementPassengerResponse.from(active, userResponseDto);
        return new ApiResponse(passengerResponse, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getByIdAndDeleteFalse(UUID id) {
        AnnouncementPassenger active = announcementPassengerRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new AnnouncementNotFoundException(PASSENGER_ANNOUNCEMENT_NOT_FOUND));
        User user = userService.checkUserExistById(active.getUser().getId());
        UserResponseDto userResponseDto = userService.fromUserToResponse(user);
        AnnouncementPassengerResponse passengerResponse =
                AnnouncementPassengerResponse.from(active, userResponseDto);
        return new ApiResponse(passengerResponse, true);
    }


    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getOwnPassengerAnnouncements() {
        User user = userService.checkUserExistByContext();
        List<AnnouncementPassenger> announcementPassengers = announcementPassengerRepository.findAllByUserIdAndDeletedFalseOrderByCreatedTimeDesc(user.getId());
        List<AnnouncementPassengerResponseList> anonymousList = new ArrayList<>();
        announcementPassengers.forEach(obj -> anonymousList.add(AnnouncementPassengerResponseList.from(obj)));
        return new ApiResponse(anonymousList, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse deletePassengerAnnouncement(UUID id) {
        AnnouncementPassenger announcementPassenger = announcementPassengerRepository.findById(id).orElseThrow(() -> new AnnouncementNotFoundException(PASSENGER_ANNOUNCEMENT_NOT_FOUND));
        announcementPassenger.setDeleted(false);
        announcementPassengerRepository.save(announcementPassenger);
        return new ApiResponse(DELETED, true);
    }
    public AnnouncementPassenger getByIdAndActiveAndDeletedFalse(UUID announcement_id, boolean active) {
        return announcementPassengerRepository.findByIdAndActiveAndDeletedFalse(announcement_id, active)
                .orElseThrow(() -> new AnnouncementNotFoundException(PASSENGER_ANNOUNCEMENT_NOT_FOUND));
    }
    public List<AnnouncementPassenger> getAnnouncementPassenger(User passenger, boolean active) {
        return announcementPassengerRepository.findAllByUserIdAndActiveAndDeletedFalseOrderByCreatedTimeDesc(passenger.getId(), active);
    }

    //    public AnnouncementPassenger getByIdAndActive(UUID announcement_id, boolean active) {
//        return announcementPassengerRepository.findByIdAndActive(announcement_id, active)
//                .orElseThrow(() -> new AnnouncementNotFoundException(PASSENGER_ANNOUNCEMENT_NOT_FOUND));
//    }
//
//    public AnnouncementPassenger getByUserId(UUID user_id) {
//        return announcementPassengerRepository.findByUserId(user_id)
//                .orElseThrow(() -> new AnnouncementNotFoundException(PASSENGER_ANNOUNCEMENT_NOT_FOUND));
//    }
//
//    public boolean existByUserIdAndActiveTrueAndDeletedFalse(UUID user_id) {
//        return announcementPassengerRepository.existsByUserIdAndActiveTrueAndDeletedFalse(user_id);
//    }
//

    private AnnouncementPassenger fromRequest(AnnouncementPassengerDto announcementPassengerDto, User user) {
        AnnouncementPassenger announcementPassenger = AnnouncementPassenger.from(announcementPassengerDto);
        announcementPassenger.setUser(user);
        announcementPassenger.setFromRegion(regionRepository.getById(announcementPassengerDto.getFromRegionId()));
        announcementPassenger.setToRegion(regionRepository.getById(announcementPassengerDto.getToRegionId()));
        announcementPassenger.setFromCity(cityRepository.getById(announcementPassengerDto.getFromCityId()));
        announcementPassenger.setToCity(cityRepository.getById(announcementPassengerDto.getToCityId()));
        return announcementPassenger;
    }
}
