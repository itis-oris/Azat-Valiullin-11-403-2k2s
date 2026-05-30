<#ftl output_format="HTML" auto_esc=true>
<#assign title=(isEdit)?then('Edit Service', 'Create Service')>
<#import "../layouts/base.ftlh" as layout>

<@layout.base>
    <div class="service-form-container">
        <div class="form-header">
            <h1>${(isEdit)?then('Edit Service', 'Create New Service')}</h1>
            <p>Define a service that artists can request from your label</p>
        </div>

        <div class="form-content">
            <form action="/service/${(isEdit)?then('edit/' + service.id?c, 'create')}" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

                <div class="form-group">
                    <label for="name">Service Name *</label>
                    <input type="text" id="name" name="name" required
                           value="${(service.name)!}" maxlength="100"
                           placeholder="e.g., Track Release, Music Production">
                </div>

                <div class="form-group">
                    <label for="description">Service Description *</label>
                    <textarea id="description" name="description" required rows="4"
                              placeholder="Describe what artists will receive...">${(service.description)!}</textarea>
                    <small>Be clear about what artists can expect</small>
                </div>

                <div class="form-group">
                    <label for="basePrice">Price ($ USD)</label>
                    <input type="number" id="basePrice" name="basePrice"
                           value="${(service.basePrice)!0}" min="0" max="10000" step="0.01"
                           placeholder="0.00 for free services">
                    <small>Set to 0 for free services</small>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">
                        ${(isEdit)?then('Update Service', 'Create Service')}
                    </button>
                    <a href="/service/manage" class="btn btn-secondary">Cancel</a>
                    <#if isEdit>
                        <a href="/service/delete/${service.id}" class="btn btn-danger"
                           onclick="return confirm('Delete this service?')">
                            Delete Service
                        </a>
                    </#if>
                </div>
            </form>
        </div>

        <div class="form-guidance">
            <h3>Service Creation Guidelines</h3>
            <ul>
                <li><strong>Track Release Service:</strong> Should always be free</li>
                <li><strong>Clear Pricing:</strong> Be transparent about costs</li>
                <li><strong>Realistic Timelines:</strong> Set achievable delivery timelines</li>
                <li><strong>Requirements:</strong> Clearly state any prerequisites</li>
            </ul>
        </div>
    </div>
</@layout.base>