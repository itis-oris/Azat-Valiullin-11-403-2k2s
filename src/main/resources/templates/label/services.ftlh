<#ftl output_format="HTML" auto_esc=true>
<#assign title="Manage Services">
<#import "../layouts/base.ftlh" as layout>
<#import "../components/service-card.ftlh" as components>

<@layout.base>
    <div class="services-container">
        <div class="services-header">
            <h1>Manage Services</h1>
            <p>Create and manage services you offer to artists</p>
            <a href="/service/create" class="btn btn-primary">
                + Add New Service
            </a>
        </div>

        <!-- Форма поиска -->
        <div class="search-bar">
            <form action="/service/search" method="get" class="search-form">
                <div class="search-row">
                    <div class="form-group search-input">
                        <label for="name">Service Name</label>
                        <input type="text" id="name" name="name"
                               value="${name!}" placeholder="Search by name...">
                    </div>
                    <div class="form-group search-input">
                        <label for="sortBy">Sort By</label>
                        <select id="sortBy" name="sortBy">
                            <option value="name" <#if sortBy?? && sortBy == "name">selected</#if>>Name</option>
                            <option value="price" <#if sortBy?? && sortBy == "price">selected</#if>>Price</option>
                        </select>
                    </div>
                    <div class="search-actions">
                        <button type="submit" class="btn btn-primary">🔍 Search</button>
                        <a href="/service/manage" class="btn btn-secondary">Reset</a>
                    </div>
                </div>
            </form>
        </div>

        <#if services?? && services?size gt 0>
            <div class="services-grid">
                <#list services as service>
                    <@components.service_card service=service />
                </#list>
            </div>
        <#else>
            <div class="empty-state">
                <div class="empty-content">
                    <h3>No Services Yet</h3>
                    <p>Start by creating your first service that artists can subscribe to.</p>
                    <p class="hint">Common services include: Track Release, Music Production, Marketing, Distribution</p>
                    <a href="/service/create" class="btn btn-primary btn-large">
                        Create Your First Service
                    </a>
                </div>
            </div>
        </#if>

        <div class="services-info">
            <h3>Service Management Tips</h3>
            <div class="tips-grid">
                <div class="tip">
                    <h4>🎵 Track Release</h4>
                    <p>Essential service for artists to submit tracks for consideration. Usually free.</p>
                </div>
                <div class="tip">
                    <h4>💰 Premium Services</h4>
                    <p>Consider adding paid services like production, mixing, or marketing packages.</p>
                </div>
                <div class="tip">
                    <h4>📝 Clear Descriptions</h4>
                    <p>Clearly describe what artists get with each service to manage expectations.</p>
                </div>
            </div>
        </div>
    </div>
</@layout.base>