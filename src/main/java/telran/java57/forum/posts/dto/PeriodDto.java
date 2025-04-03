package telran.java57.forum.posts.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PeriodDto {
    LocalDate dateFrom;
    LocalDate dateTo;
}
