package com.crmcoches.services.auth.admin;

import com.crmcoches.dto.CarDto;
import com.crmcoches.entity.Car;
import com.crmcoches.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

    private final CarRepository carRepository;

    @Override
    public boolean postCar(CarDto carDto) throws IOException {
        try {
            Car car = new Car();
            car.setName(carDto.getName());
            car.setBrand(carDto.getBrand());
            car.setColor(carDto.getColor());
            car.setPrice(carDto.getPrice());
            car.setYear(carDto.getYear());
            car.setType(carDto.getType());
            car.setDescription(carDto.getDescription());
            car.setTransmission(carDto.getTransmission());
            car.setImage(carDto.getImage().getBytes());

            // 1. Guardamos en la Base de Datos (RA 3)
            carRepository.save(car);

            // 2. NUEVO: Guardamos un log en un fichero de texto (Cumplimos el RA 1)
            try (FileWriter fw = new FileWriter("registro_coches.txt", true);
                 PrintWriter pw = new PrintWriter(fw)) {
                pw.println(LocalDateTime.now() + " - NUEVO COCHE CREADO: " + car.getBrand() + " " + car.getName());
            } catch (Exception ex) {
                System.err.println("Error al escribir el fichero de log: " + ex.getMessage());
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<CarDto> getAllCars() {
        return carRepository.findAll().stream().map(Car::getCarDto).collect(Collectors.toList());
    }
}
