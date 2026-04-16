package com.crmcoches.services.auth.customer;

import com.crmcoches.dto.BookACarDto;
import com.crmcoches.dto.CarDto;

import java.util.List;

public interface CustomerService {

    List<CarDto> getAllCars();

    boolean bookACar(BookACarDto bookACarDto);

}
