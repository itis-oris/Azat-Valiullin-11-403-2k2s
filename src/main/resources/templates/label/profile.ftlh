<#ftl output_format="HTML" auto_esc=true>
<#assign title="Label Profile">
<#import "../layouts/base.ftlh" as layout>

<@layout.base>
    <div class="profile-container">
        <div class="profile-header">
            <h1>Label Profile</h1>
            <p>Manage your label information and branding</p>
        </div>

        <#if successMessage??>
            <div class="alert alert-success">
                ${successMessage}
                <button type="button" class="alert-close" onclick="this.parentElement.remove()">×</button>
            </div>
        </#if>

        <#if errorMessage??>
            <div class="alert alert-error">
                ${errorMessage}
                <button type="button" class="alert-close" onclick="this.parentElement.remove()">×</button>
            </div>
        </#if>

        <div class="profile-form-container">
            <form action="/label/profile" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

                <div class="form-group">
                    <label for="labelName">Label Name *</label>
                    <input type="text" id="labelName" name="labelName" required
                           value="<#if profile??>${profile.labelName!}</#if>" maxlength="100"
                           placeholder="Enter your label name">
                </div>

                <div class="form-group">
                    <label for="description">Label Description</label>
                    <textarea id="description" name="description" rows="5"
                              placeholder="Describe your label, music genre focus, philosophy..."><#if profile??>${profile.description!}</#if></textarea>
                    <small>This description will be visible to artists considering your services</small>
                </div>

                <div class="form-group">
                    <label for="contactEmail">Contact Email</label>
                    <input type="email" id="contactEmail" name="contactEmail"
                           value="<#if profile??>${profile.contactEmail!}<#else>${user.email!}</#if>"
                           placeholder="Public contact email for artists">
                </div>

                <div class="form-group">
                    <label for="website">Website</label>
                    <input type="url" id="website" name="website"
                           value="<#if profile??>${profile.website!}</#if>"
                           placeholder="https://your-label.com">
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">Save Profile</button>
                    <a href="/label/dashboard" class="btn btn-secondary">Cancel</a>
                </div>
            </form>
        </div>

        <#if profile?? && profile.labelName??>
        <div class="profile-preview">
            <h3>Profile Preview</h3>
            <div class="preview-card">
                <h4>${profile.labelName}</h4>
                <#if profile.description??>
                    <p class="description">${profile.description}</p>
                </#if>
                <div class="contact-info">
                    <#if profile.contactEmail??>
                        <p><strong>Email:</strong> ${profile.contactEmail}</p>
                    </#if>
                    <#if profile.website??>
                        <p><strong>Website:</strong> <a href="${profile.website}" target="_blank">${profile.website}</a></p>
                    </#if>
                </div>
            </div>
        </div>
        </#if>
    </div>
</@layout.base>