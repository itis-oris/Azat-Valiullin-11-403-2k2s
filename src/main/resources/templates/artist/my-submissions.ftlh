<#ftl output_format="HTML" auto_esc=true>
<#assign title="My Submissions">
<#import "../layouts/base.ftlh" as layout>

<@layout.base>
    <div class="submissions-container">
        <div class="submissions-header">
            <h1>My Submissions</h1>
            <p>Track your submitted tracks and their status</p>
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
                            <p><strong>Label:</strong> ${submission.labelName!"Unknown Label"}</p>
                            <p><strong>Service:</strong> ${submission.serviceName!"Unknown Service"}</p>
                            <p><strong>Submitted:</strong> ${submission.formattedSubmissionDate}</p>
                            <p><strong>Reviewed:</strong> ${submission.formattedAdministeredDate}</p>

                            <#if submission.labelComment??>
                                <div class="label-comment">
                                    <strong>Label Feedback:</strong>
                                    <p>${submission.labelComment}</p>
                                </div>
                            </#if>
                        </div>

                        <div class="submission-actions">
                            <#if submission.status == "PENDING">
                                <span class="pending-note">Under review</span>
                            <#elseif submission.status == "APPROVED">
                                <span class="approved-note">🎉 Track approved!</span>
                            <#elseif submission.status == "REJECTED">
                                <span class="rejected-note">Track not accepted</span>
                            </#if>
                        </div>
                    </div>
                </#list>
            </div>
        <#else>
            <div class="no-submissions">
                <div class="empty-state">
                    <h3>No Submissions Yet</h3>
                    <p>You haven't submitted any tracks to labels yet.</p>
                    <a href="/submission/create" class="btn btn-primary">
                        Submit Your First Track
                    </a>
                </div>
            </div>
        </#if>
    </div>
</@layout.base>