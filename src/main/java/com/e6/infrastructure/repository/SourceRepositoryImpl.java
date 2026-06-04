package com.e6.infrastructure.repository;

import com.e6.application.dto.source.FilterResponseDTO;
import com.e6.application.dto.source.SourceItemResponseDTO;
import com.e6.domain.model.source.FilterMetadata;
import com.e6.domain.model.source.Metadata;
import com.e6.domain.model.source.Source;
import com.e6.domain.repository.SourceRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class SourceRepositoryImpl implements SourceRepository {

    private final List<Source> sources;

    public SourceRepositoryImpl(ObjectMapper mapper) throws IOException {

        TypeReference<List<Source>> type = new TypeReference<>() {};

        InputStream is = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("sources.json");

        if (is == null) {
            throw new IllegalStateException("sources.json not found");
        }

        this.sources = mapper.readValue(is, type);
    }


    @Override
    public List<SourceItemResponseDTO> getSource() {
        return  sources.stream()
                .map(t -> SourceItemResponseDTO.of(
                        t.id(),
                        t.name()))
                .toList();
    }

    @Override
    public List<SourceItemResponseDTO> getTables(int id) {
        return sources.get(id).tables().stream()
                .map(t -> SourceItemResponseDTO.of(
                        t.id(),
                        t.displayName()))
                .toList();
    }

    @Override
    public List<SourceItemResponseDTO> getColumns(int id) {
        return sources.getFirst().tables().get(id).columns().stream()
                .map(t -> SourceItemResponseDTO.of(
                        t.id(),
                        t.displayName()))
                .toList();
    }

    @Override
    public List<FilterResponseDTO> getFilters(int id) {
        return sources.getLast().tables().get(id).filters().stream()
                .map(t -> FilterResponseDTO.of(
                        t.id(),
                        t.displayName(),
                        t.values().toArray(new String[0])))
                .toList();
    }

    @Override
    public Metadata getMetadata(int sourceId, int tableId, int columnId, Set<Integer> filterIds) {
        switch(sourceId) {
            case 0:
                return new Metadata(
                        sources.get(sourceId).tables().get(tableId).displayName(),
                        sources.get(sourceId).tables().get(tableId).columns().get(columnId).displayName(),
                        Set.of()
                );
            case 1:
                Set<Integer> ids = new HashSet<>(filterIds);
                /*
                *
                * Set<FilterMetadata> filters = sources.get().filters()
                        .stream()
                        .filter(f -> ids.contains(f.id()))
                        .map(f -> new FilterMetadata(
                                f.columnName(),
                                new HashSet<>(f.values())
                        ))
                        .collect(Collectors.toSet());

                return new Metadata(
                        sources.get(sourceId).tables().get(tableId).displayName(),
                        "afluencia",
                        sources.get(sourceId).tables().get(tableId).filters().stream()
                                .map(filter::get)
                                .filter()
                                .collect(Collectors.toSet())
                );
                * */

                return new Metadata("", "", Set.of());

            default:
                throw new IllegalArgumentException();
        }

    }
}
