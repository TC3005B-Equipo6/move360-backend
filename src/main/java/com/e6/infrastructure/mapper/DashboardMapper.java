package com.e6.infrastructure.mapper;

import com.e6.domain.model.Dashboard;
import com.e6.infrastructure.entity.DashboardEntity;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class DashboardMapper {

    public static Dashboard toDomainSimple(DashboardEntity dashboardEntity) {
        return Dashboard.builder()
                .id(dashboardEntity.getId())
                .owner(UserMapper.toDomain(dashboardEntity.getOwner()))
                .title(dashboardEntity.getTitle())
                .description(dashboardEntity.getDescription())
                .createdAt(dashboardEntity.getCreatedAt())
                .isPublic(dashboardEntity.isPublic())
                .tags(TagMapper.toDomainSetWithoutDashboards(dashboardEntity.getTags()))
                .build();
    }

    public static Dashboard toDomainFull(DashboardEntity dashboardEntity) {
        return Dashboard.builder()
                .id(dashboardEntity.getId())
                .owner(UserMapper.toDomain(dashboardEntity.getOwner()))
                .title(dashboardEntity.getTitle())
                .description(dashboardEntity.getDescription())
                .createdAt(dashboardEntity.getCreatedAt())
                .isPublic(dashboardEntity.isPublic())
                .tags(TagMapper.toDomainSetWithoutDashboards(dashboardEntity.getTags()))
                .graphs(GraphMapper.toDomainSetWithoutDashboard(dashboardEntity.getGraphs()))
                .indicators(IndicatorMapper.toDomainSetWithoutDashboard(dashboardEntity.getIndicators()))
                .build();
    }

    public static Dashboard toDomainWithoutTags(DashboardEntity dashboardEntity) {
        return Dashboard.builder()
                .id(dashboardEntity.getId())
                .owner(UserMapper.toDomain(dashboardEntity.getOwner()))
                .title(dashboardEntity.getTitle())
                .description(dashboardEntity.getDescription())
                .createdAt(dashboardEntity.getCreatedAt())
                .isPublic(dashboardEntity.isPublic())
                .build();
    }

    public static List<Dashboard> toDomainList(List<DashboardEntity> entities) {
        return entities.stream()
                .map(DashboardMapper::toDomainSimple)
                .collect(Collectors.toList());
    }

    public static Set<Dashboard> toDomainSet(Set<DashboardEntity> entities) {
        return entities.stream()
                .map(DashboardMapper::toDomainSimple)
                .collect(Collectors.toSet());
    }

    public static Set<Dashboard> toDomainSetWithoutTags(Set<DashboardEntity> entities) {
        return entities.stream()
                .map(DashboardMapper::toDomainWithoutTags)
                .collect(Collectors.toSet());
    }

    public static DashboardEntity toEntity(Dashboard dashboard) {
        DashboardEntity dashboardEntity = new DashboardEntity();
        dashboardEntity.setId(dashboard.getId());
        dashboardEntity.setOwner(UserMapper.toEntity(dashboard.getOwner()));
        dashboardEntity.setTitle(dashboard.getTitle());
        dashboardEntity.setDescription(dashboard.getDescription());
        dashboardEntity.setCreatedAt(dashboard.getCreatedAt());
        dashboardEntity.setPublic(dashboard.isPublic());
        dashboardEntity.setTags(TagMapper.toEntitySet(dashboard.getTags()));
        dashboardEntity.setGraphs(GraphMapper.toEntitySet(dashboard.getGraphs()));
        dashboardEntity.setIndicators(IndicatorMapper.toEntitySet(dashboard.getIndicators()));
        return dashboardEntity;
    }

    public static List<DashboardEntity> toEntityList(List<Dashboard> dashboards) {
        return dashboards.stream()
                .map(DashboardMapper::toEntity)
                .collect(Collectors.toList());
    }

    public static Set<DashboardEntity> toEntitySet(Set<Dashboard> dashboards) {
        return dashboards.stream()
                .map(DashboardMapper::toEntity)
                .collect(Collectors.toSet());
    }
}
