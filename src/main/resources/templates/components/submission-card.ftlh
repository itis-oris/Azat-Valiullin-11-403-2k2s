<#ftl output_format="HTML" auto_esc=true>
<div class="submission-card ${submission.status?lower_case}">
    <div class="card-header">
        <h4>${submission.trackTitle}</h4>
        <span class="status status-${submission.status?lower_case}">
            ${submission.status}
        </span>
    </div>

    <div class="card-body">
        <p class="label">To: ${submission.labelName!"Unknown Label"}</p>
        <p class="service">Service: ${submission.serviceName!"Unknown Service"}</p>
        <p class="date">Submitted: ${submission.formattedSubmissionDate}</p>

        <#if submission.labelComment??>
            <div class="comment">
                <strong>Feedback:</strong> ${submission.labelComment}
            </div>
        </#if>
    </div>
</div>