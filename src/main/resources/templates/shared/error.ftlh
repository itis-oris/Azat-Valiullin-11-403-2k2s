<#ftl output_format="HTML" auto_esc=true>
<#assign title="Error ${statusCode!500}">
<#import "../layouts/base.ftlh" as layout>

<@layout.base>
    <div class="error-container">
        <div class="error-content">
            <div class="error-icon">⚠️</div>
            <h1>Oops! Something went wrong</h1>

            <#if errorMessage??>
                <div class="error-message">
                    <p>${errorMessage}</p>
                </div>
            <#else>
                <div class="error-message">
                    <#if statusCode??>
                        <p><strong>Error ${statusCode}</strong></p>
                    </#if>
                    <p>We encountered an unexpected error while processing your request.</p>
                </div>
            </#if>

            <div class="error-details">
                <details>
                    <summary>Technical Details</summary>
                    <div class="technical-info">
                        <p><strong>Timestamp:</strong> ${.now?string("yyyy-MM-dd HH:mm:ss")}</p>
                        <p><strong>Path:</strong> ${currentPath!"Unknown"}</p>
                        <#if exception??>
                            <p><strong>Exception:</strong> ${exception.class.simpleName}</p>
                        </#if>
                    </div>
                </details>
            </div>

            <div class="error-actions">
                <a href="javascript:history.back()" class="btn btn-secondary">Go Back</a>
                <a href="/" class="btn btn-primary">Home Page</a>
                <#if user??>
                    <a href="/dashboard" class="btn btn-outline">Dashboard</a>
                </#if>
            </div>

            <div class="error-help">
                <p>If this problem persists, please contact support.</p>
            </div>
        </div>
    </div>
</@layout.base>