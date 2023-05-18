package uz.pdp.shippingservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uz.pdp.shippingservice.entity.api.ApiResponse;
import uz.pdp.shippingservice.model.request.CarRegisterRequestDto;
import uz.pdp.shippingservice.service.CarService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/car")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('CLIENT','ADMIN')")
    public ApiResponse addCar(@ModelAttribute @Validated CarRegisterRequestDto carRegisterRequestDto) {
        return carService.addCar(carRegisterRequestDto);
    }
    @GetMapping("/getCar")
    @PreAuthorize("hasAnyRole('DRIVER','ADMIN')")
    public ApiResponse getCar() {
        return carService.getCar();
    }
    @GetMapping("/getCarById/{id}")
    @PreAuthorize("hasAnyRole('DRIVER','ADMIN')")
    public ApiResponse getCatById(@PathVariable UUID id) {
        return carService.getCarById(id);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('DRIVER','ADMIN')")
    public ApiResponse deleteCarByID(@PathVariable UUID id) {
        return carService.deleteCarByID(id);
    }
}
