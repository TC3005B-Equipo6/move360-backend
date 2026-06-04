package com.e6.infrastructure.repository;

import com.e6.application.dto.dashboard.AddTagResponseDTO;
import com.e6.application.dto.dashboard.CreateDashboardResponseDTO;
import com.e6.application.dto.dashboard.GetDashboardsResponseDTO;
import com.e6.application.dto.dashboard.GetUserDashboardsResponseDTO;
import com.e6.application.dto.indicator.CreateIndicatorResponseDTO;
import com.e6.domain.exception.DashboardNotFoundException;
import com.e6.domain.exception.IndicatorNotFoundException;
import com.e6.domain.exception.TagNotFoundException;
import com.e6.domain.model.Dashboard;
import com.e6.domain.model.User;
import com.e6.domain.repository.DashboardRepository;
import com.e6.infrastructure.entity.DashboardEntity;
import com.e6.infrastructure.entity.IndicatorEntity;
import com.e6.infrastructure.entity.TagEntity;
import com.e6.infrastructure.mapper.DashboardMapper;
import com.e6.infrastructure.mapper.TagMapper;
import com.e6.infrastructure.mapper.UserMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class DashboardRepositoryImpl implements DashboardRepository, PanacheRepositoryBase<DashboardEntity, UUID> {

    private static final String FETCH_GRAPH_HINT = "jakarta.persistence.fetchgraph";
    private static final String DASHBOARD_SIMPLE = "Dashboard.simple";
    private static final String DASHBOARD_FULL = "Dashboard.full";

    @Override
    @Transactional
    public CreateDashboardResponseDTO createDashboard(Dashboard dashboard) {

        persist(DashboardMapper.toEntity(dashboard));

        return CreateDashboardResponseDTO.from(dashboard);
    }

    @Override
    public List<GetDashboardsResponseDTO> getPublicDashboards() {
        List<DashboardEntity> entities = find("isPublic", true)
                .withHint(FETCH_GRAPH_HINT, getEntityManager().getEntityGraph(DASHBOARD_SIMPLE))
                .list();
        List<Dashboard> dashboards = DashboardMapper.toDomainList(entities);
        return dashboards.stream()
                .map(GetDashboardsResponseDTO::from)
                .collect(Collectors.toList());
    }

    @Override
    public List<GetUserDashboardsResponseDTO> getUserDashboards(User user) {
        List<DashboardEntity> entities = find("owner", UserMapper.toEntity(user))
                .withHint(FETCH_GRAPH_HINT, getEntityManager().getEntityGraph(DASHBOARD_SIMPLE))
                .list();
        List<Dashboard> dashboards = DashboardMapper.toDomainList(entities);
        return dashboards.stream()
                .map(GetUserDashboardsResponseDTO::from)
                .collect(Collectors.toList());
    }

    @Override
    public Dashboard findDashboardById(UUID id) {
        DashboardEntity dashboardEntity = getEntityManager().find(
                DashboardEntity.class,
                id,
                Map.of(FETCH_GRAPH_HINT, getEntityManager().getEntityGraph(DASHBOARD_FULL))
        );
        if (dashboardEntity == null) {
            throw new DashboardNotFoundException(String.valueOf(id));
        }
        return DashboardMapper.toDomainFull(dashboardEntity);
    }

    @Override
    @Transactional
    public CreateDashboardResponseDTO updateDashboard(Dashboard dashboard) {
        DashboardEntity dashboardEntity = findByIdOptional(dashboard.getId())
                .orElseThrow(() -> new DashboardNotFoundException(String.valueOf(dashboard.getId())));

        if (dashboard.getTitle() != null)
            dashboardEntity.setTitle(dashboard.getTitle());
        if (dashboard.getDescription() != null)
            dashboardEntity.setDescription(dashboard.getDescription());

        return new CreateDashboardResponseDTO(
                dashboardEntity.getId(),
                dashboardEntity.getTitle(),
                dashboardEntity.getDescription(),
                dashboardEntity.getOwner().getFirstName() + dashboardEntity.getOwner().getPaternalSurname());
    }

    @Override
    @Transactional
    public void deleteDashboardById(UUID id) {
        boolean deleted = deleteById(id);
        if (!deleted)
            throw new DashboardNotFoundException(id.toString());
    }

    @Override
    @Transactional
    public AddTagResponseDTO addTag(UUID id, int tagId) {
        try {
            DashboardEntity dashboardEntity = findByIdOptional(id)
                    .orElseThrow(() -> new DashboardNotFoundException(String.valueOf(id)));

            TagEntity tagReference = getEntityManager().getReference(TagEntity.class, tagId);
            dashboardEntity.getTags().add(tagReference);
            return AddTagResponseDTO.from(DashboardMapper.toDomainSimple(dashboardEntity), TagMapper.toDomain(tagReference));
        } catch (EntityNotFoundException e) {
            throw new TagNotFoundException(String.valueOf(tagId));
        }
    }

    @Override
    @Transactional
    public void removeTag(UUID id, int tagId) {
        try {
            DashboardEntity dashboardEntity = findByIdOptional(id)
                    .orElseThrow(() -> new DashboardNotFoundException(String.valueOf(id)));

            TagEntity tagReference = getEntityManager().getReference(TagEntity.class, tagId);
            dashboardEntity.getTags().remove(tagReference);
        } catch (EntityNotFoundException e) {
            throw new TagNotFoundException(String.valueOf(tagId));
        }

    }
}
