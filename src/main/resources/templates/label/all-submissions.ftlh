<#ftl output_format="HTML" auto_esc=true>
<#assign title="All Submissions">
<#import "../layouts/base.ftlh" as layout>

<@layout.base>
    <div class="submissions-container">
        <div class="submissions-header">
            <h1>All Submissions</h1>
            <p>Complete history of artist submissions</p>
            <div class="submission-stats">
                <span class="stat">${submissions?size} total submissions</span>
            </div>
        </div>

        <div class="dashboard-actions">
            <a href="/submission/pending" class="btn btn-primary">Pending (${submissions?filter(s -> s.status == 'PENDING')?size})</a>
            <a href="/submission/history" class="btn btn-secondary">All History</a>
            <a href="/label/dashboard" class="btn btn-outline">Dashboard</a>
        </div>

        <#if submissions?? && submissions?size gt 0>
            <div class="submissions-list">
                <#list submissions as submission>
                    <div class="submission-item ${submission.status?lower_case}">
                        <div class="submission-header">
                            <h3>${submission.trackTitle}</h3>
                            <span class="status-badge status-${submission.status?lower_case}">
                                ${submission.status}
                            </span>
                        </div>

                        <div class="submission-details">
                            <p><strong>Artist:</strong> ${submission.artistName!"Unknown Artist"}</p>
                            <p><strong>Service:</strong> ${submission.serviceName!"Unknown Service"}</p>
                            <p><strong>Submitted:</strong> ${submission.formattedSubmissionDate}</p>
                            <p><strong>Reviewed:</strong> ${submission.formattedAdministeredDate}</p>

                            <#if submission.labelComment??>
                                <div class="label-comment">
                                    <strong>Your Feedback:</strong>
                                    <p>${submission.labelComment}</p>
                                </div>
                            </#if>
                        </div>

                        <div class="submission-actions">
                            <#if submission.status == "PENDING">
                                <a href="/song/review/${submission.id}" class="btn btn-sm btn-primary">Review</a>
                            <#elseif submission.status == "APPROVED">
                                <span class="approved-note">✅ Approved</span>
                            <#elseif submission.status == "REJECTED">
                                <span class="rejected-note">❌ Rejected</span>
                            </#if>
                        </div>
                    </div>
                </#list>
            </div>
        <#else>
            <div class="empty-state">
                <h3>No Submissions Yet</h3>
                <p>No artists have submitted tracks to your services yet.</p>
                <a href="/label/dashboard" class="btn btn-primary">Back to Dashboard</a>
            </div>
        </#if>
    </div>
</@layout.base>