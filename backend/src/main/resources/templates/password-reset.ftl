<#include "base.ftl">
<#macro page_head>
    <title>Password Reset</title>
</#macro>
<#macro page_body>
    <h1>Password Reset</h1>
    <p>To reset your password visit the flowing link:</p>
    <p><a href="${link}">${link}</a></p>
</#macro>
<@display_page/>
