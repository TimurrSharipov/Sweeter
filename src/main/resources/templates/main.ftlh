<#import "parts/common.ftlh" as p>
<#import "parts/login.ftlh" as l>
<@p.page>
<div>
<@l.logout/>
</div>
<div>
    <form method="post">
        <input type="text" name="text" placeholder="Message">
        <input type="text" name="tag" placeholder="Tag">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button type="submit">Add</button>
    </form>
</div>
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