package com.e6.infrastructure.mapper;

import com.e6.domain.model.Dashboard;
import com.e6.infrastructure.entity.DashboardEntity;

import java.util.List;
import java.util.stream.Collectors;

public final class DashboardMapper {

    public static Dashboard toDomain(DashboardEntity dashboardEntity){
        Dashboard dashboard = new Dashboard();
        dashboard.setId(dashboardEntity.getId());
        dashboard.setOwner(UserMapper.toDomain(dashboardEntity.getOwner()));
        dashboard.setTitle(dashboardEntity.getTitle());
        dashboard.setDescription(dashboardEntity.getDescription());
        dashboard.setCreatedAt(dashboardEntity.getCreatedAt());
        dashboard.setPublic(dashboardEntity.isPublic());
        //TODO: agregar lógica de tags
        return dashboard;
    }

    public static List<Dashboard> toDomainList(List<DashboardEntity> entities) {
        return entities.stream()
                .map(DashboardMapper::toDomain)
                .collect(Collectors.toList());
    }

    public static DashboardEntity toEntity(Dashboard dashboard){
        DashboardEntity dashboardEntity = new DashboardEntity();
        dashboardEntity.setId(dashboard.getId());
        dashboardEntity.setOwner(UserMapper.toEntity(dashboard.getOwner()));
        dashboardEntity.setTitle(dashboard.getTitle());
        dashboardEntity.setDescription(dashboard.getDescription());
        dashboardEntity.setCreatedAt(dashboard.getCreatedAt());
        dashboardEntity.setPublic(dashboard.isPublic());
        //TODO: agregar lógica de tags
        return dashboardEntity;
    }

    public static List<DashboardEntity> toEntityList(List<Dashboard> dashboards) {
        return dashboards.stream()
                .map(DashboardMapper::toEntity)
                .collect(Collectors.toList());
    }
}
