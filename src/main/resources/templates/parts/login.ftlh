<#macro login path isRegisterForm>
    <form action="${path}" method="post">
        <div class="form-group-row">
            <label class="col-sm-2 col-form-label"> User Name : </label>
            <div class="col-sm-6">
                <input type="text" class="form-control" name="username" placeholder="Username"/>
            </div>
        </div>
        <div class="form-group-row">
            <label class="col-sm-2 col-form-label"> Password: </label>
            <div class="col-sm-6">
                <input type="password" class="form-control" name="password" placeholder="Password"/>
            </div>
        </div>
        <#if isRegisterForm>
        <div class="form-group-row">
            <label class="col-sm-2 col-form-label"> Email: </label>
            <div class="col-sm-6">
                <input type="email" class="form-control" name="email" placeholder="YourEmail@gmail.com"/>
            </div>
        </div>
        </#if>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <#if !isRegisterForm><a href="/registration">Add new user</a></#if>
        <button class="btn btn-primary m-2" type="submit"><#if isRegisterForm>Create<#else >Sign In</button></#if>
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button class="btn btn-primary m-2" type="submit">Sign Out</button>
    </form>
</#macro>