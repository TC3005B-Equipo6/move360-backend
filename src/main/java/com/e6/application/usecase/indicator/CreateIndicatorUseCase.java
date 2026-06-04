package com.e6.application.usecase.indicator;

import com.e6.application.dto.indicator.CreateIndicatorDTO;
import com.e6.application.dto.indicator.CreateIndicatorResponseDTO;
import com.e6.domain.model.Indicator.Indicator;
import com.e6.domain.model.source.Metadata;
import com.e6.domain.repository.IndicatorRepository;
import com.e6.domain.repository.SourceRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@ApplicationScoped
public class CreateIndicatorUseCase {

    private final IndicatorRepository indicatorRepository;
    private final SourceRepository sourceRepository;

    public CreateIndicatorUseCase(IndicatorRepository indicatorRepository, SourceRepository sourceRepository) {
        this.indicatorRepository = indicatorRepository;
        this.sourceRepository = sourceRepository;
    }

    public CreateIndicatorResponseDTO execute(CreateIndicatorDTO createIndicatorDTO){
        try {
            ObjectMapper mapper = new ObjectMapper();

            Metadata metadata = sourceRepository.getMetadata(
                    createIndicatorDTO.sourceId(),
                    createIndicatorDTO.tableId(),
                    createIndicatorDTO.columnId(),
                    createIndicatorDTO.filters());

            Double data = indicatorRepository.aggregate(
                    createIndicatorDTO.sourceId() == 1,
                    metadata.tableName(),
                    metadata.columnName(),
                    createIndicatorDTO.operation(),
                    createIndicatorDTO.startDate(),
                    createIndicatorDTO.endDate(),
                    metadata.filters()
            );

            long days = ChronoUnit.DAYS.between(createIndicatorDTO.startDate(), createIndicatorDTO.endDate());
            LocalDate previousStart = createIndicatorDTO.startDate().minusDays(days);

            Double deltaData = data - indicatorRepository.aggregate(
                    createIndicatorDTO.sourceId() == 1,
                    metadata.tableName(),
                    metadata.columnName(),
                    createIndicatorDTO.operation(),
                    previousStart,
                    createIndicatorDTO.startDate(),
                    metadata.filters()
            );

            Indicator indicator = Indicator.builder()
                    .title(createIndicatorDTO.title())
                    .subtitle(createIndicatorDTO.subtitle())
                    .type(createIndicatorDTO.type())
                    .data(data)
                    .relationship(createIndicatorDTO.relationship())
                    .deltaData(deltaData)
                    .operation(createIndicatorDTO.operation())
                    .startDate(createIndicatorDTO.startDate())
                    .endDate(createIndicatorDTO.endDate())
                    .query(mapper.writeValueAsString(metadata))
                    .dashboard(createIndicatorDTO.dashboardId())
                    .coordinate(createIndicatorDTO.coordinate())
                    .source(createIndicatorDTO.sourceId())
                    .build();

            indicator = indicatorRepository.createIndicator(indicator);

            System.out.println("indicator terminado");

            return new CreateIndicatorResponseDTO(
                    indicator.getId(),
                    indicator.getTitle(),
                    indicator.getSubtitle(),
                    indicator.getType(),
                    indicator.getRelationship(),
                    indicator.getDeltaData(),
                    indicator.getData()
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
