package com.e6.infrastructure.repository;

import com.e6.domain.exception.DashboardNotFoundException;
import com.e6.domain.model.Dashboard;
import com.e6.domain.model.User;
import com.e6.domain.repository.DashboardRepository;
import com.e6.infrastructure.entity.DashboardEntity;
import com.e6.infrastructure.mapper.DashboardMapper;
import com.e6.infrastructure.mapper.UserMapper;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class DashboardRepositoryImpl implements DashboardRepository, PanacheRepositoryBase<DashboardEntity, UUID> {

    @Override
    @Transactional
    public Dashboard createDashboard(Dashboard dashboard){
        dashboard.setId(UUID.randomUUID());
        dashboard.setCreatedAt(LocalDateTime.now());

        persist(DashboardMapper.toEntity(dashboard));

        return dashboard;
    }

    @Override
    public List<Dashboard> getUserDashboards(User user) {
        List<DashboardEntity> dashboardEntities = list("owner", UserMapper.toEntity(user));
        return DashboardMapper.toDomainList(dashboardEntities);
    }

    @Override
    public Dashboard findDashboardById(UUID id) {
        DashboardEntity dashboardEntity = findById(id);
        if (dashboardEntity == null)
            throw new DashboardNotFoundException();
        return DashboardMapper.toDomain(dashboardEntity);
    }

    @Override
    @Transactional
    public void deleteDashboardById(UUID id) {
        boolean deleted = deleteById(id);
        if (!deleted)
            throw new DashboardNotFoundException();
    }
}
