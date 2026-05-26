package com.e6.domain.repository;

import com.e6.application.dto.dashboard.AddTagResponseDTO;
import com.e6.application.dto.dashboard.CreateDashboardResponseDTO;
import com.e6.application.dto.dashboard.GetDashboardsResponseDTO;
import com.e6.application.dto.dashboard.GetUserDashboardsResponseDTO;
import com.e6.domain.model.Dashboard;
import com.e6.domain.model.User;

import java.util.List;
import java.util.UUID;

public interface DashboardRepository {
    CreateDashboardResponseDTO createDashboard(Dashboard dashboard);

    List<GetDashboardsResponseDTO> getPublicDashboards();

    List<GetUserDashboardsResponseDTO> getUserDashboards(User user);

    Dashboard findDashboardById(UUID uuid);

    void deleteDashboardById(UUID id);

    AddTagResponseDTO addTag(UUID id, int tagId);

    void removeTag(UUID id, int tagId);
}
