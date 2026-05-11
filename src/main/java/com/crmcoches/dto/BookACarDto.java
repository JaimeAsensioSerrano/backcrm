package com.crmcoches.dto;


import com.crmcoches.enums.BookCarsStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class BookACarDto {

    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fromDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date toDate;

    private Long days;

    private Long price;

    private BookCarsStatus bookCarsStatus;

    private Long carId;

    private Long userId;

    private String username;

    private String email;

    private String carName;

}
