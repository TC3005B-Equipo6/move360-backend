package com.e6.infrastructure.repository;

import com.e6.application.dto.dashboard.AddTagResponseDTO;
import com.e6.application.dto.dashboard.CreateDashboardResponseDTO;
import com.e6.application.dto.dashboard.GetDashboardsResponseDTO;
import com.e6.application.dto.dashboard.GetUserDashboardsResponseDTO;
import com.e6.domain.exception.DashboardNotFoundException;
import com.e6.domain.exception.TagNotFoundException;
import com.e6.domain.model.Dashboard;
import com.e6.domain.model.User;
import com.e6.domain.repository.DashboardRepository;
import com.e6.infrastructure.entity.DashboardEntity;
import com.e6.infrastructure.entity.TagEntity;
import com.e6.infrastructure.mapper.DashboardMapper;
import com.e6.infrastructure.mapper.TagMapper;
import com.e6.infrastructure.mapper.UserMapper;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class DashboardRepositoryImpl implements DashboardRepository, PanacheRepositoryBase<DashboardEntity, UUID> {

    @Override
    @Transactional
    public CreateDashboardResponseDTO createDashboard(Dashboard dashboard) {

        persist(DashboardMapper.toEntity(dashboard));

        return CreateDashboardResponseDTO.from(dashboard);
    }

    @Override
    public List<GetDashboardsResponseDTO> getPublicDashboards() {
        List<Dashboard> dashboards = DashboardMapper.toDomainList(list("isPublic", true));
        return dashboards.stream()
                .map(GetDashboardsResponseDTO::from)
                .collect(Collectors.toList());
    }

    @Override
    public List<GetUserDashboardsResponseDTO> getUserDashboards(User user) {
        List<Dashboard> dashboards = DashboardMapper.toDomainList(list("owner", UserMapper.toEntity(user)));
        return dashboards.stream()
                .map(GetUserDashboardsResponseDTO::from)
                .collect(Collectors.toList());
    }

    @Override
    public Dashboard findDashboardById(UUID id) {
        DashboardEntity dashboardEntity = findByIdOptional(id)
                .orElseThrow(() -> new DashboardNotFoundException(String.valueOf(id)));
        return DashboardMapper.toDomain(dashboardEntity);
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
            return AddTagResponseDTO.from(DashboardMapper.toDomain(dashboardEntity), TagMapper.toDomain(tagReference));
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
