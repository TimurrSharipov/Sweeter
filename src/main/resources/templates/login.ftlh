<#import "parts/common.ftlh" as p>
<#import "parts/login.ftlh" as l>
<@p.page>
    ${message!}
<@l.login "/login" false />
Login page
</@p.page>