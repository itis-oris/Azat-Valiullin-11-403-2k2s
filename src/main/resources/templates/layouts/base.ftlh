<#ftl output_format="HTML" auto_esc=true>
<#macro base>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${title!"Music Label Platform"} - SoundGate</title>

    <link rel="stylesheet" href="/css/style.css">

    <#if csrfToken??>
        <meta name="_csrf" content="${csrfToken}">
    </#if>
</head>
<body>
    <#include "header.ftlh">      <!-- ✅ без layouts/ -->

    <main class="container">
        <#if error??>
            <div class="alert alert-error">
                ${error}
                <button type="button" class="alert-close" onclick="this.parentElement.remove()">×</button>
            </div>
        </#if>

        <#if message??>
            <div class="alert alert-success">
                ${message}
                <button type="button" class="alert-close" onclick="this.parentElement.remove()">×</button>
            </div>
        </#if>

        <#if info??>
            <div class="alert alert-info">
                ${info}
                <button type="button" class="alert-close" onclick="this.parentElement.remove()">×</button>
            </div>
        </#if>

        <#nested>
    </main>

    <#include "footer.ftlh">      <!-- ✅ без layouts/ -->

    <script src="/js/main.js"></script>
</body>
</html>
</#macro>