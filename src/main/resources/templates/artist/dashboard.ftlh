<#ftl output_format="HTML" auto_esc=true>
<#assign title="Artist Dashboard">
<#import "../layouts/base.ftlh" as layout>

<@layout.base>
    <div class="dashboard-header">
        <h1>Artist Dashboard</h1>
        <p>Manage your music submissions and profile</p>
    </div>

    <div class="dashboard-stats">
        <div class="stat-card">
            <h3>${submissionsCount!0}</h3>
            <p>Total Submissions</p>
        </div>
        <div class="stat-card">
            <h3>${pendingSubmissions!0}</h3>
            <p>Pending Review</p>
        </div>
        <div class="stat-card">
            <h3>${approvedSubmissions!0}</h3>
            <p>Approved Tracks</p>
        </div>
    </div>

    <div class="dashboard-actions">
        <a href="/submission/create" class="btn btn-primary">
            Submit New Track
        </a>
        <a href="/submission/my" class="btn btn-secondary">
            View My Submissions
        </a>
        <a href="/artist/profile" class="btn btn-outline">
            Edit Profile
        </a>
    </div>

    <#if recentSubmissions?? && recentSubmissions?size gt 0>
        <div class="recent-submissions">
            <h2>Recent Submissions</h2>
            <div class="submissions-grid">
                <#list recentSubmissions as submission>
                    <#include "../components/submission-card.ftlh">
                </#list>
            </div>
        </div>
    </#if>
</@layout.base>