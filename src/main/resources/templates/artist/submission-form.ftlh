<#ftl output_format="HTML" auto_esc=true>
<#assign title="Submit Track">
<#import "../layouts/base.ftlh" as layout>

<@layout.base>
    <div class="submission-container">
        <div class="submission-header">
            <h1>Submit Your Track</h1>
            <p>Share your music with labels</p>
        </div>

        <#if error??>
            <div class="alert alert-error">
                ${error}
                <button type="button" class="alert-close" onclick="this.parentElement.remove()">×</button>
            </div>
        </#if>

        <div class="submission-form-container">
            <form action="/submission/create" method="post" enctype="multipart/form-data">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                <input type="hidden" id="trackFileName" name="trackFileName">

                <div class="form-group">
                    <label for="trackTitle">Track Title *</label>
                    <input type="text" id="trackTitle" name="trackTitle" required
                           value="${trackTitle!}" maxlength="200"
                           placeholder="Enter the title of your track">
                </div>

                <div class="form-group">
                    <label for="labelId">Select Label *</label>
                    <select id="labelId" name="labelId" required>
                        <option value="">Choose a label...</option>
                        <#list labels as label>
                            <option value="${label.id}"
                                    <#if selectedLabelId?? && selectedLabelId == label.id>selected</#if>>
                                ${label.labelName}
                            </option>
                        </#list>
                    </select>
                </div>

                <div class="form-group">
                    <label for="serviceId">Select Service *</label>
                    <select id="serviceId" name="serviceId" required>
                        <option value="">Choose a service...</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="trackFile">Upload Track *</label>
                    <input type="file" id="trackFile" name="trackFile" required
                           accept="audio/mpeg,audio/wav,audio/mp3"
                           data-preview="file-preview">
                    <small>Supported formats: MP3, WAV. Max size: 10MB</small>
                    <div id="file-preview" class="file-preview"></div>
                </div>

                <div class="form-group">
                    <label for="notes">Additional Notes (Optional)</label>
                    <textarea id="notes" name="notes" rows="3"
                              placeholder="Any additional information about your track...">${notes!}</textarea>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">Submit Track</button>
                    <a href="/artist/dashboard" class="btn btn-secondary">Cancel</a>
                </div>
            </form>
        </div>

        <div class="submission-info">
            <h3>Submission Guidelines</h3>
            <ul>
                <li>Ensure your track is properly mixed and mastered</li>
                <li>File size should not exceed 10MB</li>
                <li>Only submit original content you have rights to</li>
                <li>Labels typically respond within 7-14 days</li>
                <li>You can track your submission status in your dashboard</li>
            </ul>
        </div>
    </div>

    <script>
        var RUB_RATE = 71;

        fetch('https://www.cbr-xml-daily.ru/daily_json.js')
            .then(function(response) {
                if (!response.ok) throw new Error('Network error');
                return response.json();
            })
            .then(function(data) {
                RUB_RATE = data.Valute.USD.Value;
                console.log('USD/RUB rate:', RUB_RATE);
            })
            .catch(function(error) {
                console.log('Using fallback rate:', RUB_RATE);
            });

        document.getElementById('labelId').addEventListener('change', function() {
            const labelId = this.value;
            const serviceSelect = document.getElementById('serviceId');

            serviceSelect.innerHTML = '<option value="">Loading services...</option>';

            if (labelId) {
                fetch('/api/services/label/' + labelId)
                    .then(function(response) {
                        if (!response.ok) throw new Error('Network error');
                        return response.json();
                    })
                    .then(function(services) {
                        serviceSelect.innerHTML = '<option value="">Choose a service...</option>';

                        if (services.length === 0) {
                            serviceSelect.innerHTML = '<option value="">No services available</option>';
                            return;
                        }

                        services.forEach(function(service) {
                            var priceText;
                            if (service.basePrice > 0) {
                                var rubPrice = (service.basePrice * RUB_RATE).toFixed(0);
                                priceText = ' ($' + service.basePrice + ' / ' + rubPrice + ' ₽)';
                            } else {
                                priceText = ' (Free)';
                            }
                            var option = document.createElement('option');
                            option.value = service.id;
                            option.textContent = service.name + priceText;
                            serviceSelect.appendChild(option);
                        });
                    })
                    .catch(function(error) {
                        console.error('Error:', error);
                        serviceSelect.innerHTML = '<option value="">Error loading services</option>';
                    });
            } else {
                serviceSelect.innerHTML = '<option value="">Choose a service...</option>';
            }
        });

        document.getElementById('trackFile').addEventListener('change', function(e) {
            const file = e.target.files[0];
            const preview = document.getElementById('file-preview');

            if (file) {
                preview.innerHTML =
                    '<div class="file-info">' +
                    '<strong>Selected file:</strong> ' + file.name + '<br>' +
                    '<strong>Size:</strong> ' + (file.size / 1024 / 1024).toFixed(2) + ' MB<br>' +
                    '<strong>Type:</strong> ' + file.type +
                    '</div>';
            } else {
                preview.innerHTML = '';
            }
        });
    </script>
</@layout.base>