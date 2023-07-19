<!DOCTYPE html>
<html lang="hu">
<jsp:include page="./partials/head.jsp"/>
<body>
<% if (request.getSession().getAttribute("error") != null) { %>
<p>An error occurred while logging in</p>
<% } %>
<div class="header">
    <h2>Log into your account</h2>
</div>
    <section>
        <form name="user_sign_up" method="POST" action="/leim2041-web/login">
            <p class="formText">Username: <input class="formText inputText" type="text" name="username" placeholder="Username"
                                                 pattern="^[A-z ]*$" title="You can only insert letters in this field" required></p>
            <p class="formText">Password: <input class="formText inputPassword" type="password" name="password" placeholder="Password" required></p>
            <p><input class="inputSubmit" type="submit" value="Submit"></p>
        </form>
    </section>
</body>

</html>