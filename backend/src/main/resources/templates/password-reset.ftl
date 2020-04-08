<#include "base.ftl">
<#macro page_head>
    <title>Password Reset</title>
</#macro>
<#macro page_body>
    <b>Password Reset</b>
    <br>
    <p>To reset your password visit the flowing link:</p>
    <p><a href="${link}">${link}</a></p>
    <br>
    <p>Please note that this links is only valid for one day and can be used only once.</p>
</#macro>
<@display_page/>
