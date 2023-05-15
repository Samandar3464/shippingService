package uz.pdp.shippingservice.service;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import uz.pdp.shippingservice.entity.Attachment;
import uz.pdp.shippingservice.entity.Car;
import uz.pdp.shippingservice.entity.User;
import uz.pdp.shippingservice.entity.api.ApiResponse;
import uz.pdp.shippingservice.exception.CarNotFound;
import uz.pdp.shippingservice.model.request.CarRegisterRequestDto;
import uz.pdp.shippingservice.model.response.CarResponseDto;
import uz.pdp.shippingservice.repository.CarRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static uz.pdp.shippingservice.entity.constants.Constants.*;


@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    private final AttachmentService attachmentService;

    private final UserService userService;

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse addCar(CarRegisterRequestDto carRegisterRequestDto) throws FileUploadException {
        User user = userService.checkUserExistByContext();
        from(carRegisterRequestDto, user);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse disActiveCarList() {
        List<Car> allByActive = carRepository.findAllByActiveFalse();
        List<CarResponseDto> carResponseDtoList = new ArrayList<>();
        allByActive.forEach(this::fromCarToResponse);
        return new ApiResponse(carResponseDtoList, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getCarById(UUID carId) {
        Car car = carRepository.findById(carId).orElseThrow(() -> new CarNotFound(CAR_NOT_FOUND));
        return new ApiResponse(fromCarToResponse(car), true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getCar() {
        User user = userService.checkUserExistByContext();
        Car car = getCarByUserId(user.getId());
        return new ApiResponse(fromCarToResponse(car), true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse activateCar(UUID carId) {
        Car car = carRepository.findById(carId).orElseThrow(() -> new CarNotFound(CAR_NOT_FOUND));
        car.setActive(true);
        carRepository.save(car);
        userService.addRoleDriver(List.of(car));
        return new ApiResponse(CAR_ACTIVATED, true);
    }

    private Car from(CarRegisterRequestDto carRegisterRequestDto, User user) throws FileUploadException {

        Car car = Car.from(carRegisterRequestDto);
        car.setPhotoDriverLicense(attachmentService.saveToSystem(carRegisterRequestDto.getPhotoDriverLicense()));
        car.setTexPassportPhoto(attachmentService.saveToSystem(carRegisterRequestDto.getTexPassportPhoto()));
        car.setAutoPhotos(attachmentService.saveToSystemListFile(carRegisterRequestDto.getAutoPhotos()));
        car.setUser(user);
        Car save = carRepository.save(car);
        return save;
    }

    private CarResponseDto fromCarToResponse(Car car) {
        Attachment texPassportPhoto1 = car.getTexPassportPhoto();
        String texPasswordPhotoLink = attachmentService.attachUploadFolder + texPassportPhoto1.getPath() + "/" + texPassportPhoto1.getNewName() + "." + texPassportPhoto1.getType();
        Attachment photoDriverLicense1 = car.getPhotoDriverLicense();
        String photoDriverLicense2 = attachmentService.attachUploadFolder + photoDriverLicense1.getPath() + "/" + photoDriverLicense1.getNewName() + "." + photoDriverLicense1.getType();
        List<Attachment> autoPhotos1 = car.getAutoPhotos();
        List<String> carPhotoList = new ArrayList<>();
        for (Attachment attachment : autoPhotos1) {
            carPhotoList.add(attachmentService.attachUploadFolder + attachment.getPath() + "/" + attachment.getNewName() + "." + attachment.getType());
        }
        CarResponseDto carResponseDto = CarResponseDto.from(car);
        carResponseDto.setTexPassportPhotoPath(texPasswordPhotoLink);
        carResponseDto.setPhotoDriverLicense(photoDriverLicense2);
        carResponseDto.setAutoPhotosPath(carPhotoList);
        return carResponseDto;
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse deleteCarByID(UUID id) {
        Car byId = carRepository.findById(id).orElseThrow(() -> new CarNotFound(CAR_NOT_FOUND));
        byId.setActive(false);
        carRepository.save(byId);
        return new ApiResponse(DELETED, true);
    }

    public Car getCarByUserId(UUID user_id) {
        return carRepository.findByUserIdAndActiveTrue(user_id).orElseThrow(() ->
                new CarNotFound(CAR_NOT_FOUND));
    }

}
