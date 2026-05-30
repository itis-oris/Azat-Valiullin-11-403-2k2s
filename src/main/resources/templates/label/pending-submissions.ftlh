<#ftl output_format="HTML" auto_esc=true>
<#assign title="Pending Submissions">
<#import "../layouts/base.ftlh" as layout>

<@layout.base>
    <div class="submissions-container">
        <div class="submissions-header">
            <h1>Pending Submissions</h1>
            <p>Review and manage track submissions from artists</p>
            <div class="submission-stats">
                <span class="stat">${submissions?size} submissions awaiting review</span>
            </div>
        </div>

        <#if submissions?? && submissions?size gt 0>
            <div class="submissions-list">
                <#list submissions as submission>
                    <div class="submission-review-card">
                        <div class="submission-main">
                            <div class="track-info">
                                <h3>${submission.trackTitle}</h3>
                                <div class="artist-info">
                                    <strong>Artist:</strong> ${submission.artistName!"Unknown Artist"}
                                </div>
                                <div class="service-info">
                                    <strong>Service:</strong> ${submission.serviceName!"Unknown Service"}
                                </div>
                                <div class="submission-date">
                                    Submitted: ${submission.formattedSubmissionDate}
                                </div>
                            </div>

                            <div class="track-actions">
                                <#if submission.trackFileUrl??>
                                    <audio controls class="audio-player">
                                        <source src="/uploads/${submission.trackFileUrl}" type="audio/mpeg">
                                        Your browser does not support the audio element.
                                    </audio>
                                </#if>
                                <a href="/song/review/${submission.id}" class="btn btn-primary">
                                    Review Track
                                </a>
                            </div>
                        </div>
                    </div>
                </#list>
            </div>
        <#else>
            <div class="empty-state">
                <div class="empty-content">
                    <h3>No Pending Submissions</h3>
                    <p>All submissions have been reviewed. Check back later for new submissions.</p>
                    <div class="success-icon">🎉</div>
                    <a href="/label/dashboard" class="btn btn-primary">
                        Return to Dashboard
                    </a>
                </div>
            </div>
        </#if>
    </div>

    <script>
    document.addEventListener('DOMContentLoaded', function() {
        const audioPlayers = document.querySelectorAll('.audio-player');
        audioPlayers.forEach(player => {
            player.addEventListener('play', function() {
                audioPlayers.forEach(otherPlayer => {
                    if (otherPlayer !== player && !otherPlayer.paused) {
                        otherPlayer.pause();
                    }
                });
            });
        });
    });
    </script>
</@layout.base>