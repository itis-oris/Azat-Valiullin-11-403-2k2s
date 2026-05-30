<#ftl output_format="HTML" auto_esc=true>
<#assign title="Label Dashboard">
<#import "../layouts/base.ftlh" as layout>
<#import "../components/service-card.ftlh" as components>

<@layout.base>
    <div class="dashboard-header">
        <h1>Label Dashboard</h1>
        <p>Manage your label services and track submissions</p>
    </div>

    <div class="dashboard-stats">
        <div class="stat-card">
            <h3>${pendingSubmissionsCount!0}</h3>
            <p>Pending Submissions</p>
        </div>
        <div class="stat-card">
            <h3>${totalSubmissionsCount!0}</h3>
            <p>Total Submissions</p>
        </div>
        <div class="stat-card">
            <h3>${servicesCount!0}</h3>
            <p>Active Services</p>
        </div>
        <div class="stat-card">
            <h3>${approvalRate!0}%</h3>
            <p>Approval Rate</p>
        </div>
    </div>

    <div class="dashboard-actions">
        <a href="/submission/pending" class="btn btn-primary">
            Review Submissions (${pendingSubmissionsCount!0})
        </a>
        <a href="/service/manage" class="btn btn-secondary">
            Manage Services
        </a>
        <a href="/submission/history" class="btn btn-outline">
            View All Submissions
        </a>
        <a href="/label/profile" class="btn btn-outline">
            Label Profile
        </a>
    </div>

    <div class="dashboard-sections">
        <div class="section">
            <h2>Pending Submissions</h2>
            <#if recentSubmissions?? && recentSubmissions?size gt 0>
                <div class="submissions-list compact">
                    <#list recentSubmissions as submission>
                        <div class="submission-item compact ${submission.status?lower_case}">
                            <div class="submission-info">
                                <h4>${submission.trackTitle}</h4>
                                <p class="artist">By: ${submission.artistName!"Unknown Artist"}</p>
                                <p class="date">${submission.formattedSubmissionDate}</p>
                            </div>
                            <div class="submission-actions">
                                <a href="/submission/pending" class="btn btn-sm btn-primary">
                                    Review
                                </a>
                            </div>
                        </div>
                    </#list>
                </div>
            <#else>
                <div class="empty-state small">
                    <p>No recent submissions</p>
                </div>
            </#if>
        </div>

        <div class="section">
            <h2>Your Services</h2>
            <#if labelServices?? && labelServices?size gt 0>
                <div class="services-grid compact">
                    <#list labelServices as service>
                        <@components.service_card service=service showActions=false />
                    </#list>
                </div>
                <div class="text-center">
                    <a href="/service/manage" class="btn btn-outline">
                        View All Services
                    </a>
                </div>
            <#else>
                <div class="empty-state small">
                    <p>No services created yet</p>
                    <a href="/service/create" class="btn btn-primary">
                        Create First Service
                    </a>
                </div>
            </#if>
        </div>
    </div>
</@layout.base>