<#include "base.ftl">
<#macro page_head>
    <title>Email Confirmation</title>
</#macro>
<#macro page_body>
    <h1>Email Confirmation</h1>
    <p>Welcome to the Speech Collection App.</p>
    <p>To activate your account please visit the following link:</p>
    <p><a href="${link}">${link}</a></p>
    <br>
    <p>Please note that this links is only valid for one day and can be used only once.</p>
</#macro>
<@display_page/>
