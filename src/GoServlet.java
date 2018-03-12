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

        // Time at the beginning of computation
        long startNanos = System.nanoTime();

        BigInteger big = new BigInteger(formNumber);
        boolean alreadyPrime = false;

        if (isBigPrime(big)) {
            alreadyPrime = true;
        } else {
            // A list of prime factors
            List<BigInteger> primeFactors = new ArrayList<>();

            // Find prime factors
            BigInteger x = big;
            for (BigInteger i = BigInteger.valueOf(2); i.compareTo(x) <= 0; i = i.add(BigInteger.ONE)) {
                while (x.mod(i).equals(BigInteger.ZERO)) {
                    primeFactors.add(i);
                    x = x.divide(i);
                }
            }

            // Record prime factors
            req.setAttribute("primeFactors", primeFactors);
        }

        // Time at the end of computation
        long stopNanos = System.nanoTime();

        // Calculate computation time in seconds
        double computationTime = (stopNanos - startNanos) / 1000000000d;

        req.setAttribute("alreadyPrime", alreadyPrime);
        req.setAttribute("computationTime", computationTime);
        req.getRequestDispatcher("/results").forward(req, resp);
    }

    private boolean isBigPrime(BigInteger big) {
        // Use BigInteger's own prime check with 50% certainty
        if (!big.isProbablePrime(1))
            return false;

        // If integer ends with a zero bit (is even) and is greater than two
        if (!big.testBit(0) && big.compareTo(BigInteger.valueOf(2)) > 0)
            return false;

        // Then test remaining odd factors up to sqrt(big)
        for (BigInteger i = BigInteger.valueOf(3); i.pow(2).compareTo(big) <= 0; i = i.add(BigInteger.valueOf(2))) {
            // If input integer is divisible by the factor
            if (big.mod(i).compareTo(BigInteger.ZERO) == 0)
                return false;
        }

        return true;
    }

}
