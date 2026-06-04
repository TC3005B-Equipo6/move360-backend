package com.e6.application.usecase.indicator;

import com.e6.application.dto.indicator.CreateIndicatorDTO;
import com.e6.application.dto.indicator.CreateIndicatorResponseDTO;
import com.e6.domain.model.Indicator.Indicator;
import com.e6.domain.model.source.Metadata;
import com.e6.domain.repository.IndicatorRepository;
import com.e6.domain.repository.SourceRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Set;

@ApplicationScoped
public class CreateIndicatorUseCase {

    private final IndicatorRepository indicatorRepository;
    private final SourceRepository sourceRepository;

    public CreateIndicatorUseCase(IndicatorRepository indicatorRepository, SourceRepository sourceRepository) {
        this.indicatorRepository = indicatorRepository;
        this.sourceRepository = sourceRepository;
    }

    public CreateIndicatorResponseDTO execute(CreateIndicatorDTO createIndicatorDTO){
        Metadata metadata = sourceRepository.getMetadata(
                createIndicatorDTO.sourceId(),
                createIndicatorDTO.tableId(),
                createIndicatorDTO.columnId(),
                Set.of());

        Double data = indicatorRepository.aggregate(
                metadata.tableName(),
                metadata.columnName(),
                createIndicatorDTO.operation(),
                createIndicatorDTO.startDate(),
                createIndicatorDTO.endDate()
        );

        long days = ChronoUnit.DAYS.between(createIndicatorDTO.startDate(), createIndicatorDTO.endDate());
        LocalDate previousStart = createIndicatorDTO.startDate().minusDays(days);

        Double deltaData = indicatorRepository.aggregate(
                metadata.tableName(),
                metadata.columnName(),
                createIndicatorDTO.operation(),
                previousStart,
                createIndicatorDTO.startDate()
        );

        Indicator indicator = Indicator.builder()
                .startDate(createIndicatorDTO.startDate())
                .endDate(createIndicatorDTO.endDate())
                .title(createIndicatorDTO.title())
                .coordinate(createIndicatorDTO.coordinate())
                .build();

        indicator = indicatorRepository.createIndicator(indicator);

        return new CreateIndicatorResponseDTO(
                indicator.getId(),
                createIndicatorDTO.title(),
                createIndicatorDTO.subtitle(),
                createIndicatorDTO.type(),
                createIndicatorDTO.relationship(),
                deltaData,
                data
        );
    }
}
