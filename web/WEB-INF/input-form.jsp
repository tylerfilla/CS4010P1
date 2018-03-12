<form action="go" method="post">
    <label for="number">Please enter a number to factorize:</label><br/>
    <input type="text" name="number" id="number"/><br/>
    <label for="city">Please select your city for billing:</label><br/>
    <select name="city" id="city">
        <%
            for (String city : application.getInitParameter("cities").split(",")) {
        %>
        <option value="<%=city%>">
            <%=application.getInitParameter("city-" + city + "-name")%>
        </option>
        <%
            }
        %>
    </select><br/>
    <input type="submit" value="Go"/>
</form>
