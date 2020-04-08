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
    <body style="background-color: whitesmoke">
    <div style="background-color: white;margin: auto;width: fit-content;border: 1px solid darkgray; padding: 10px;">
        <@page_body/>
        <br>
        <div style="color: grey;font-size: 0.8em">
            <p>If you did not request this email, you can safely ignore this message.</p>
            <p>Another user may have entered their email address incorrectly.</p>
            <p>Without this code they cannot do anything.</p>
        </div>
    </div>
    </body>
    </html>
</#macro>
