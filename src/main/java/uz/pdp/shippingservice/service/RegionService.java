package uz.pdp.shippingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import uz.pdp.shippingservice.entity.Region;
import uz.pdp.shippingservice.entity.api.ApiResponse;
import uz.pdp.shippingservice.exception.RecordAlreadyExistException;
import uz.pdp.shippingservice.exception.RecordNotFoundException;
import uz.pdp.shippingservice.model.request.RegionRegisterRequestDto;
import uz.pdp.shippingservice.repository.RegionRepository;

import static uz.pdp.shippingservice.entity.constants.Constants.*;

@Service
@RequiredArgsConstructor
public class RegionService {

    private final RegionRepository regionRepository;

    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse addRegion(RegionRegisterRequestDto regionRegisterRequestDto) {
        if (regionRepository.existsByName(regionRegisterRequestDto.getName())) {
            throw new RecordAlreadyExistException(REGION_ALREADY_EXIST);
        }
        regionRepository.save(Region.from(regionRegisterRequestDto));
        return new ApiResponse(SUCCESSFULLY , true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getRegionList(){
        return new ApiResponse(regionRepository.findAll(),true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getRegionById(Integer id){
        return new ApiResponse(regionRepository.findById(id).orElseThrow(()->new RecordNotFoundException(REGION_NOT_FOUND)),true);
    }

    public ApiResponse deleteRegionById(Integer id) {
        regionRepository.deleteById(id);
        return new ApiResponse(DELETED,true);
    }
}
