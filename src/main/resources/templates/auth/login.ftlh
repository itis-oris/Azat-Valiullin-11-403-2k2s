<#ftl output_format="HTML" auto_esc=true>
<#assign title="Login">
<#import "../layouts/base.ftlh" as layout>

<@layout.base>
    <div class="auth-container">
        <div class="form-container">
            <h2 class="text-center">Login to SoundGate</h2>

            <#if error??>
                <div class="alert alert-error">
                    ${error}
                    <button type="button" class="alert-close" onclick="this.parentElement.remove()">×</button>
                </div>
            </#if>

            <form action="/auth/login" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" required
                           value="${email!}" placeholder="Enter your email">
                </div>

                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" required>
                </div>

                <button type="submit" class="btn btn-primary btn-block">Login</button>
            </form>

            <div class="auth-links">
                <p>Don't have an account? <a href="/auth/register">Register here</a></p>
            </div>
        </div>
    </div>
</@layout.base>