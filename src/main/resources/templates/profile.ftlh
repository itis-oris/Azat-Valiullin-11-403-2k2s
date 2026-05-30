<#ftl output_format="HTML" auto_esc=true>
<#assign title="User Profile">
<#import "layouts/base.ftlh" as layout>

<@layout.base>
    <div class="profile-container">
        <div class="profile-header">
            <h1>User Profile</h1>
            <p>Manage your account information</p>
        </div>

        <#if message??>
            <div class="alert alert-success">
                ${message}
                <button type="button" class="alert-close" onclick="this.parentElement.remove()">×</button>
            </div>
        </#if>

        <#if error??>
            <div class="alert alert-error">
                ${error}
                <button type="button" class="alert-close" onclick="this.parentElement.remove()">×</button>
            </div>
        </#if>

        <div class="profile-content">
            <div class="profile-info">
                <div class="info-card">
                    <h3>Account Details</h3>
                    <div class="info-group">
                        <label>Username:</label>
                        <span>${user.username!}</span>
                    </div>
                    <div class="info-group">
                        <label>Email:</label>
                        <span>${user.email!}</span>
                    </div>
                    <div class="info-group">
                        <label>Role:</label>
                        <span class="role-badge ${user.role?lower_case}">${user.role!}</span>
                    </div>
                </div>

                <#if user.role == 'ARTIST' && artistProfile??>
                    <div class="profile-card">
                        <h3>Artist Profile</h3>
                        <div class="info-group">
                            <label>Artist Name:</label>
                            <span>${artistProfile.artistName!user.username}</span>
                        </div>
                        <div class="info-group">
                            <label>Genre:</label>
                            <span>${artistProfile.genre!'Not specified'}</span>
                        </div>
                    </div>
                <#elseif user.role == 'LABEL' && labelProfile??>
                    <div class="profile-card">
                        <h3>Label Profile</h3>
                        <div class="info-group">
                            <label>Label Name:</label>
                            <span>${labelProfile.labelName!user.username}</span>
                        </div>
                        <div class="info-group">
                            <label>Description:</label>
                            <span>${labelProfile.description!'Not specified'}</span>
                        </div>
                    </div>
                </#if>
            </div>

            <div class="profile-actions">
                <div class="update-form">
                    <h3>Update Account</h3>
                    <form action="/profile" method="post">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <div class="form-group">
                            <label for="username">Username:</label>
                            <input type="text" id="username" name="username" value="${user.username!}" required>
                        </div>
                        <div class="form-group">
                            <label for="email">Email:</label>
                            <input type="email" id="email" name="email" value="${user.email!}" required>
                        </div>
                        <button type="submit" class="btn btn-primary">Update Profile</button>
                    </form>
                </div>

                <div class="role-actions">
                    <#if user.role == 'ARTIST'>
                        <h3>Artist Actions</h3>
                        <div class="action-links">
                            <a href="/artist/dashboard" class="btn btn-primary">Artist Dashboard</a>
                            <a href="/submission/create" class="btn btn-secondary">Submit New Track</a>
                            <a href="/artist/profile" class="btn btn-outline">Edit Artist Profile</a>
                        </div>
                    <#elseif user.role == 'LABEL'>
                        <h3>Label Actions</h3>
                        <div class="action-links">
                            <a href="/label/dashboard" class="btn btn-primary">Label Dashboard</a>
                            <a href="/submission/pending" class="btn btn-secondary">Pending Submissions</a>
                            <a href="/label/profile" class="btn btn-outline">Edit Label Profile</a>
                        </div>
                    </#if>
                </div>
            </div>
        </div>
    </div>
</@layout.base>