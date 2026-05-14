package com.e6.domain.repository;

import com.e6.domain.model.Dashboard;
import com.e6.domain.model.User;

import java.util.List;
import java.util.UUID;

public interface DashboardRepository {

    Dashboard createDashboard(Dashboard dashboard);
    List<Dashboard> getUserDashboards(User user);
    Dashboard findDashboardById(UUID uuid);
    void deleteDashboardById(UUID id);
}
