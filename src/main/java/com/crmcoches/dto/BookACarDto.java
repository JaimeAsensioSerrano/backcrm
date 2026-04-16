package com.crmcoches.dto;


import com.crmcoches.enums.BookCarsStatus;
import lombok.Data;

import java.util.Date;

@Data
public class BookACarDto {

    private Long id;

    private Date fromDate;

    private Date toDate;

    private Long days;

    private Long price;

    private BookCarsStatus bookCarsStatus;

    private Long carId;

    private Long userId;
}
