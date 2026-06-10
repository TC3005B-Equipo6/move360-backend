package com.e6.application.service;

import com.e6.domain.model.Dashboard;
import com.e6.domain.model.User;
import com.e6.infrastructure.security.AuthContext;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ForbiddenException;

@ApplicationScoped
public class DashboardAccessService {

    private final AuthContext authContext;

    public DashboardAccessService(AuthContext authContext) {
        this.authContext = authContext;
    }

    public void requireOwner(Dashboard dashboard) {
        if (!isOwner(dashboard)) {
            throw new ForbiddenException("Dashboard owner required");
        }
    }

    public void requireReadable(Dashboard dashboard) {
        if (!dashboard.isPublic() && !isOwner(dashboard)) {
            throw new ForbiddenException("Dashboard is private");
        }
    }

    private boolean isOwner(Dashboard dashboard) {
        User user = authContext.getUser();
        return user != null
                && dashboard.getOwner() != null
                && dashboard.getOwner().getId() != null
                && dashboard.getOwner().getId().equals(user.getId());
    }
}
