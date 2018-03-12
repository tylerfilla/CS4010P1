/*
 * Tyler Filla
 * CS 4010
 * Project 1
 */

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@WebServlet(name = "GoServlet", urlPatterns = {"/go"})
public class GoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().print("Invalid form submission.");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Get number and city form parameters
        String formNumber = req.getParameter("number");
        String formCity = req.getParameter("city");

        // Get the name of the city
        String cityName = getServletContext().getInitParameter("city-" + formCity + "-name");

        // Get the tax rate string proportion representation
        String taxRateString = getServletContext().getInitParameter("city-" + formCity + "-tax");
        double taxRate = Double.valueOf(taxRateString);

        // Time at the beginning of computation
        long startNanos = System.nanoTime();

        BigInteger inputInteger = new BigInteger(formNumber);

        // Create executor for work
        ExecutorService executorService = Executors.newCachedThreadPool();

        // Submit work
        Future<List<BigInteger>> factorizeTask = executorService.submit(() -> {
            List<BigInteger> primeFactors = new ArrayList<>();

            BigInteger x = inputInteger;
            for (BigInteger i = BigInteger.valueOf(2); i.compareTo(x) <= 0; i = i.add(BigInteger.ONE)) {
                while (x.mod(i).equals(BigInteger.ZERO)) {
                    primeFactors.add(i);
                    x = x.divide(i);
                }
            }

            return primeFactors;
        });

        boolean alreadyPrime = false;

        List<BigInteger> primeFactors = null;
        try {
            primeFactors = factorizeTask.get(10, TimeUnit.SECONDS);
        } catch (ExecutionException | InterruptedException ignored) {
        } catch (TimeoutException ignored) {
            req.setAttribute("failed", true);
        } finally {
            if (primeFactors != null) {
                if (primeFactors.size() == 1) {
                    alreadyPrime = true;
                }
            }
        }

        // Record prime factors
        req.setAttribute("primeFactors", primeFactors);

        // Time at the end of computation
        long stopNanos = System.nanoTime();

        // Calculate computation time in seconds
        double computationTime = (stopNanos - startNanos) / 1000000000d;

        // The price components for the computation
        // Each second, rounded to the next integer second, costs 1 dollar
        // There is a maximum subtotal of $10, since actual execution might take a few milliseconds over to cancel
        double subtotal = Math.min(10, Math.ceil(computationTime));
        double salesTax = taxRate * subtotal;
        double total = subtotal + salesTax;

        req.setAttribute("inputNumber", formNumber);
        req.setAttribute("cityName", cityName);
        req.setAttribute("taxRatePercent", taxRate * 100);
        req.setAttribute("alreadyPrime", alreadyPrime);
        req.setAttribute("computationTime", computationTime);
        req.setAttribute("subtotal", subtotal);
        req.setAttribute("salesTax", salesTax);
        req.setAttribute("total", total);
        req.getRequestDispatcher("/results").forward(req, resp);
    }

}
