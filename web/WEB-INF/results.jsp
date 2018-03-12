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
<p>The number you gave is already prime. You will not be billed for any compute time.</p>
<%
} else {
%>
<h4>Input Number</h4>
<p>
    <%= request.getAttribute("inputNumber") %>
</p>
<%
    if (request.getAttribute("failed") == null) {
%>
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
<%
} else {
%>
<p>The computation did not complete in a timely manner, indicating the number you provided cannot be factorized on our hardware. You will
    still be billed for used compute time.</p>
<%
    }
%>
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
            $<%= String.format("%.2f", (Double) request.getAttribute("salesTax")) %>
        </td>
    </tr>
    <tr>
        <td>
            <b>Total</b>
        </td>
        <td>
            <b>
                $<%= String.format("%.2f", (Double) request.getAttribute("total")) %>
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
