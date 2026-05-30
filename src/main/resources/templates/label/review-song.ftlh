<#ftl output_format="HTML" auto_esc=true>
<#assign title="Review Submission">
<#import "../layouts/base.ftlh" as layout>

<@layout.base>
    <div class="review-container">
        <div class="review-header">
            <h1>Review Submission</h1>
            <p>Listen to the track and provide your feedback</p>
        </div>

        <#if submission??>
            <div class="review-card">
                <div class="track-details">
                    <h2>${submission.trackTitle}</h2>
                    <p><strong>Artist:</strong> ${submission.artistName!"Unknown Artist"}</p>
                    <p><strong>Service:</strong> ${submission.serviceName!"Unknown Service"}</p>
                    <p><strong>Submitted:</strong> ${submission.formattedSubmissionDate}</p>
                    <p><strong>Status:</strong> ${submission.status}</p>
                </div>

                <#if submission.trackFileUrl??>
                    <div class="audio-section">
                        <h3>Track Preview</h3>
                        <audio controls class="audio-player">
                            <source src="/uploads/${submission.trackFileUrl}" type="audio/mpeg">
                            Your browser does not support the audio element.
                        </audio>

                        <button type="button" onclick="searchTrackInfo()"
                                class="btn btn-outline btn-sm" style="margin-top:1rem;">
                            🌍 Search Track Info
                        </button>
                        <div id="trackInfoResult" style="margin-top:1rem;"></div>
                    </div>
                </#if>

                <div class="review-form-section">
                    <h3>Your Decision</h3>
                    <form action="/song/review/${submission.id}" method="post">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

                        <div class="form-group">
                            <label for="comment">Feedback to Artist</label>
                            <textarea id="comment" name="comment" rows="4"
                                      placeholder="Provide constructive feedback..."></textarea>
                        </div>

                        <div class="decision-buttons">
                            <button type="submit" name="decision" value="approve"
                                    class="btn btn-success"
                                    onclick="return confirm('Approve this submission?')">
                                ✅ Approve
                            </button>
                            <button type="submit" name="decision" value="reject"
                                    class="btn btn-danger"
                                    onclick="return confirm('Reject this submission?')">
                                ❌ Reject
                            </button>
                            <a href="/submission/pending" class="btn btn-secondary">
                                Cancel
                            </a>
                        </div>
                    </form>
                </div>
            </div>
        <#else>
            <div class="empty-state">
                <p>Submission not found.</p>
                <a href="/submission/pending" class="btn btn-primary">Back to Pending</a>
            </div>
        </#if>
    </div>
</@layout.base>

<script>
function searchTrackInfo() {
    const resultDiv = document.getElementById('trackInfoResult');
    const trackTitle = '${submission.trackTitle!"Unknown"}';

    resultDiv.innerHTML = '<p style="color:#ffd700;">🔍 Searching iTunes for "' + trackTitle + '"...</p>';

    fetch('/api/track/info?title=' + encodeURIComponent(trackTitle))
        .then(function(r) { return r.json(); })
        .then(function(data) {
            if (data.found) {
                var thumbnail = data.thumbnail !== 'N/A'
                    ? '<img src="' + data.thumbnail + '" style="width:100px; border-radius:8px; float:left; margin-right:1rem;">'
                    : '';

                resultDiv.innerHTML =
                    '<div style="background:rgba(0,0,0,0.3); padding:1rem; border-radius:8px; color:white; overflow:hidden;">' +
                    thumbnail +
                    '<h4 style="color:#ffd700;">🎵 ' + data.artist + ' - ' + data.title + '</h4>' +
                    '<p><strong>Album:</strong> ' + data.album + '</p>' +
                    '<p><strong>Genre:</strong> ' + data.genre + '</p>' +
                    '<p><strong>Year:</strong> ' + data.year + '</p>' +
                    '<p><strong>Duration:</strong> ' + data.duration + '</p>' +
                    '<p><strong>Price:</strong> ' + data.price + '</p>' +
                    '<p><strong>Country:</strong> ' + data.country + '</p>' +
                    (data.previewUrl !== 'N/A' ?
                        '<audio controls style="width:100%;margin-top:0.5rem;"><source src="' + data.previewUrl + '" type="audio/m4a"></audio>'
                        : '') +
                    '</div>';
            } else {
                resultDiv.innerHTML = '<p style="color:orange;">❌ ' + data.message + '</p>';
            }
        })
        .catch(function() {
            resultDiv.innerHTML = '<p style="color:red;">❌ Search failed</p>';
        });
}
</script>