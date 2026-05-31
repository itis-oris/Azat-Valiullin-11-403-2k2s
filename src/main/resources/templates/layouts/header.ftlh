<#ftl output_format="HTML" auto_esc=true>
<header>
    <nav class="navbar">
        <div class="nav-brand">
            <a href="/">🎵 SoundGate</a>
        </div>

        <div class="nav-links">
            <#if user??>
                <span class="user-info">Welcome, ${user.username} (${user.role})</span>
                <a href="/profile" class="nav-link">Profile</a>
                <#if user.role == 'ARTIST'>
                    <a href="/artist/dashboard" class="nav-link">Dashboard</a>
                    <a href="/submission/create" class="nav-link">Submit Track</a>
                <#elseif user.role == 'LABEL'>
                    <a href="/label/dashboard" class="nav-link">Dashboard</a>
                    <a href="/service/manage" class="nav-link">Services</a>
                </#if>
                <form action="/auth/logout" method="post" class="logout-form">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                    <button type="submit" class="nav-link logout-btn">Logout</button>
                </form>
            <#else>
                <a href="/auth/login" class="nav-link">Login</a>
                <a href="/auth/register" class="nav-link">Register</a>
            </#if>
        </div>
    </nav>
</header>