package com.e6.infrastructure.repository;

import com.e6.application.dto.source.FilterResponseDTO;
import com.e6.application.dto.source.SourceItemResponseDTO;
import com.e6.domain.model.source.Source;
import com.e6.domain.repository.SourceRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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
}
