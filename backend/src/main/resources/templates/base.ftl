<#macro page_head>
    <title>Page title!</title>
</#macro>

<#macro page_body></#macro>

<#macro display_page>
<#--TODO improve base email layout-->
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <@page_head/>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
    <@page_body/>
    </body>
    </html>
</#macro>
