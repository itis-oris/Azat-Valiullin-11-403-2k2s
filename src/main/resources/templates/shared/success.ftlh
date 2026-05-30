<#ftl output_format="HTML" auto_esc=true>
<#assign title="Success">
<#import "../layouts/base.ftlh" as layout>

<@layout.base>
    <div class="success-container">
        <div class="success-content">
            <div class="success-icon">✅</div>
            <h1>Success!</h1>

            <#if successMessage??>
                <div class="success-message">
                    <p>${successMessage}</p>
                </div>
            <#else>
                <div class="success-message">
                    <p>Your request was completed successfully.</p>
                </div>
            </#if>

            <#if additionalInfo??>
                <div class="additional-info">
                    <p>${additionalInfo}</p>
                </div>
            </#if>

            <div class="success-actions">
                <#if continueUrl??>
                    <a href="${continueUrl}" class="btn btn-primary">
                        <#if continueText??>${continueText}<#else>Continue</#if>
                    </a>
                </#if>

                <#if secondaryUrl??>
                    <a href="${secondaryUrl}" class="btn btn-secondary">
                        <#if secondaryText??>${secondaryText}<#else>Back to Dashboard</#if>
                    </a>
                <#else>
                    <a href="/dashboard" class="btn btn-secondary">Dashboard</a>
                </#if>

                <#if homeUrl!true>
                    <a href="/" class="btn btn-outline">Home Page</a>
                </#if>
            </div>

            <#if autoRedirect?? && autoRedirect.url??>
                <div class="auto-redirect">
                    <p>Redirecting in <span id="countdown">${autoRedirect.delay!5}</span> seconds...</p>
                </div>

                <script>
                    let seconds = ${autoRedirect.delay!5};
                    const countdownElement = document.getElementById('countdown');
                    const redirectUrl = '${autoRedirect.url}';

                    const countdown = setInterval(function() {
                        seconds--;
                        countdownElement.textContent = seconds;

                        if (seconds <= 0) {
                            clearInterval(countdown);
                            window.location.href = redirectUrl;
                        }
                    }, 1000);
                </script>
            </#if>
        </div>
    </div>
</@layout.base>