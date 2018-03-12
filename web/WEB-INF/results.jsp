<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.math.BigInteger" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Computation Results</title>
</head>
<body>
<h2>Computation Results</h2>
<%
    if ((boolean) request.getAttribute("alreadyPrime")) {
%>
<p>The number you gave is already prime. You will not be billed for compute time.</p>
<%
} else {
%>
<h4>Input Number</h4>
<p><%= request.getAttribute("inputNumber") %></p>
<h4>Prime Factors</h4>
<ul>
    <%
        // noinspection unchecked
        for (BigInteger factor : (List<BigInteger>) request.getAttribute("primeFactors")) {
    %>
    <li>
        <%= factor %>
    </li>
    <%
        }
    %>
</ul>
<h4>Invoice</h4>
<table>
    <tr>
        <td>Compute Time</td>
        <td>
            <%= String.format("%.4f", (Double) request.getAttribute("computationTime")) %> seconds
        </td>
    </tr>
    <tr>
        <td>Subtotal</td>
        <td>
            $<%= String.format("%.2f", (Double) request.getAttribute("subtotal")) %>
        </td>
    </tr>
    <tr>
        <td>City</td>
        <td>
            <%= request.getAttribute("cityName") %>
        </td>
    </tr>
    <tr>
        <td>Sales Tax (<%= request.getAttribute("taxRatePercent") + "%" %>)</td>
        <td>
            $<%= request.getAttribute("salesTax") %>
        </td>
    </tr>
    <tr>
        <td>
            <b>Total</b>
        </td>
        <td>
            <b>
                <%= request.getAttribute("total") %>
            </b>
        </td>
    </tr>
</table>
<br/>
<%
    }
%>
<a href="/">Home</a>
</body>
</html>
