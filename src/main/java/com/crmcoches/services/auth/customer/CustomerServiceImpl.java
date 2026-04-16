package com.crmcoches.services.auth.customer;


import com.crmcoches.dto.BookACarDto;
import com.crmcoches.dto.CarDto;
import com.crmcoches.entity.BookACar;
import com.crmcoches.entity.Car;
import com.crmcoches.entity.User;
import com.crmcoches.enums.BookCarsStatus;
import com.crmcoches.repository.BookACarRepository;
import com.crmcoches.repository.CarRepository;
import com.crmcoches.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CarRepository carRepository;

    private final UserRepository userRepository;

    private final BookACarRepository bookACarRepository;

    @Override
    public List<CarDto> getAllCars() {
        return carRepository.findAll().stream().map(Car::getCarDto).collect(Collectors.toList());
    }

    @Override
    public boolean bookACar(BookACarDto bookACarDto) {

        Optional<Car> optionalCar = carRepository.findById(bookACarDto.getCarId());
        Optional<User> optionalUser = userRepository.findById(bookACarDto.getUserId());
        if (optionalCar.isPresent() && optionalUser.isPresent()){
            Car existingcar = optionalCar.get();
            BookACar bookACar = new BookACar();
            bookACar.setUser(optionalUser.get());
            bookACar.setCar(existingcar);
            bookACar.setBookCarsStatus(BookCarsStatus.PENDING);
            long diffInMilliSeconds = bookACarDto.getToDate().getTime() - bookACarDto.getFromDate().getTime();
            long days = TimeUnit.MICROSECONDS.toDays(diffInMilliSeconds);
            bookACar.setDays(days);
            bookACar.setPrice(existingcar.getPrice() * days);
            bookACarRepository.save(bookACar);
            return true;
        }
        return false;
    }
}
