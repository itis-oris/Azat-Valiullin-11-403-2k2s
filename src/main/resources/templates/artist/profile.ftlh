<#ftl output_format="HTML" auto_esc=true>
<#assign title="Artist Profile">
<#import "../layouts/base.ftlh" as layout>

<@layout.base>
    <div class="profile-container">
        <div class="profile-header">
            <h1>Artist Profile</h1>
            <p>Manage your artist information</p>
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
            <form action="/artist/profile" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

                <div class="form-group">
                    <label for="artistName">Artist/Band Name *</label>
                    <input type="text" id="artistName" name="artistName" required
                           value="${profile.artistName!}" maxlength="100">
                </div>

                <div class="form-group">
                    <label for="genre">Music Genre</label>
                    <select id="genre" name="genre">
                        <option value="">Select Genre</option>
                        <option value="Rock" <#if profile.genre?? && profile.genre == "Rock">selected</#if>>Rock</option>
                        <option value="Pop" <#if profile.genre?? && profile.genre == "Pop">selected</#if>>Pop</option>
                        <option value="Hip-Hop" <#if profile.genre?? && profile.genre == "Hip-Hop">selected</#if>>Hip-Hop</option>
                        <option value="Electronic" <#if profile.genre?? && profile.genre == "Electronic">selected</#if>>Electronic</option>
                        <option value="Jazz" <#if profile.genre?? && profile.genre == "Jazz">selected</#if>>Jazz</option>
                        <option value="Classical" <#if profile.genre?? && profile.genre == "Classical">selected</#if>>Classical</option>
                        <option value="Country" <#if profile.genre?? && profile.genre == "Country">selected</#if>>Country</option>
                        <option value="R&B" <#if profile.genre?? && profile.genre == "R&B">selected</#if>>R&B</option>
                        <option value="Metal" <#if profile.genre?? && profile.genre == "Metal">selected</#if>>Metal</option>
                        <option value="Other" <#if profile.genre?? && profile.genre == "Other">selected</#if>>Other</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="description">Artist Description</label>
                    <textarea id="description" name="description" rows="4"
                              placeholder="Tell us about your music, influences, and style...">${profile.description!}</textarea>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">Save Profile</button>
                    <a href="/artist/dashboard" class="btn btn-secondary">Cancel</a>
                </div>
            </form>
        </div>

        <#if profile?? && profile.artistName??>
        <div class="profile-preview">
            <h3>Profile Preview</h3>
            <div class="preview-card">
                <h4>${profile.artistName}</h4>
                <#if profile.genre??>
                    <p class="genre">Genre: ${profile.genre}</p>
                </#if>
                <#if profile.description??>
                    <p class="description">${profile.description}</p>
                </#if>
            </div>
        </div>
        </#if>
    </div>
</@layout.base>