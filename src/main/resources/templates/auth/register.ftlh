<#ftl output_format="HTML" auto_esc=true>
<#assign title="Register">
<#import "../layouts/base.ftlh" as layout>

<@layout.base>
    <div class="auth-container">
        <div class="form-container">
            <h2 class="text-center">Join SoundGate</h2>

            <#if error??>
                <div class="alert alert-error">
                    ${error}
                    <button type="button" class="alert-close" onclick="this.parentElement.remove()">×</button>
                </div>
            </#if>

            <form action="/auth/register" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

                <div class="form-group">
                    <label for="username">Username</label>
                    <input type="text" id="username" name="username" required
                           value="${username!}" pattern="^[a-zA-Z0-9_]{3,50}$">
                    <small>3-50 characters, letters, numbers and underscores only</small>
                </div>

                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" required
                           value="${email!}">
                </div>

                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" required
                           pattern="^.{6,100}$" data-strength="password-strength">
                    <div class="password-strength-container">
                        <div class="password-strength-bar">
                            <div id="password-strength" class="password-strength bg-danger" style="width: 0%"></div>
                        </div>
                        <span class="strength-text">Very Weak</span>
                    </div>
                </div>

                <div class="form-group">
                    <label for="confirmPassword">Confirm Password</label>
                    <input type="password" id="confirmPassword" name="confirmPassword" required>
                </div>

                <div class="form-group">
                    <label>I am a:</label>
                    <div class="role-selection">
                        <label class="radio-label">
                            <input type="radio" name="role" value="ARTIST" checked>
                            Artist/Band
                        </label>
                        <label class="radio-label">
                            <input type="radio" name="role" value="LABEL">
                            Music Label
                        </label>
                    </div>
                </div>

                <button type="submit" class="btn btn-primary btn-block">Create Account</button>
            </form>

            <div class="auth-links">
                <p>Already have an account? <a href="/auth/login">Login here</a></p>
            </div>
        </div>
    </div>
</@layout.base>