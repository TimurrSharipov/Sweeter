<#import "parts/common.ftlh" as p>
<#import "parts/login.ftlh" as l>
<@p.page>
<div>
<@l.logout/>
</div>
<a href="/user">Users</a>
<form method="get" action="/main">
    <label>
        <input type="text" name="filter">
    </label>
    <button type="submit">Find</button>
</form>
<div>
<#list messages as message>
        <div>
            <b>${message.id}</b>
            <span>${message.text}</span>
            <i>${message.tag}</i>
            <strong>${message.authorName}</strong>
        </div>
<#else>
    No message
</#list>
</div>
</@p.page>