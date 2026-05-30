<#macro service_card service showActions=true>
<div class="service-card">
    <div class="service-header">
        <h4>${service.name!}</h4>
        <#if service.basePrice?? && service.basePrice gt 0>
            <span class="price">${currencyService.getFormattedPrice(service)}</span>
        <#else>
            <span class="price free">Free</span>
        </#if>
    </div>

    <div class="service-body">
        <#if service.description??>
            <p class="description">${service.description!}</p>
        </#if>

        <div class="service-meta">
            <#if service.submissionCount??>
                <span class="meta-item">
                    <strong>${service.submissionCount!0}</strong> submissions
                </span>
            </#if>
            <#if service.avgRating??>
                <span class="meta-item">
                    <strong>${service.avgRating?string("0.0")!0}</strong> ★
                </span>
            </#if>
        </div>
    </div>

    <div class="service-actions">
        <#if showActions>
            <#if user?? && user.role == 'ARTIST'>
                <a href="/submission/create?serviceId=${service.id!}"
                   class="btn btn-sm btn-primary">
                    Select Service
                </a>
            <#elseif user?? && user.role == 'LABEL'>
                <a href="/service/edit/${service.id!}"
                   class="btn btn-sm btn-outline">
                    Edit
                </a>
                <form action="/service/delete/${service.id!}" method="post"
                      class="inline-form" onsubmit="return confirm('Delete this service?')">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                    <button type="submit" class="btn btn-sm btn-danger">Delete</button>
                </form>
            </#if>
        </#if>
    </div>
</div>
</#macro>