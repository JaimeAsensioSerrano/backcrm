package com.crmcoches.services.auth.customer;

import com.crmcoches.dto.BookACarDto;
import com.crmcoches.dto.CarDto;
import com.crmcoches.dto.CarDtoListDto;
import com.crmcoches.dto.SearchCarDto;

import java.util.List;

public interface CustomerService {

    List<CarDto> getAllCars();

    boolean bookACar(BookACarDto bookACarDto);

    CarDto getCarById(long carId);

    List<BookACarDto> getBookingsByUserId(Long userId);

    CarDtoListDto searchCar(SearchCarDto searchCarDto);

}
