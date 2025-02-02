package org.jasonadriel.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jasonadriel.context.Application;
import org.jasonadriel.model.Transaction;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class TransactionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getRequestURI().equalsIgnoreCase("/")) {
            String htmlResponse = """
                   <html lang='en'>
                       <body>
                           <h1>Bank Servlet</h1>
                           <p>Welcome to MyBank!</p>
                       </body>
                   </html>
                    """;
            resp.setContentType("text/html; charset=utf-8");
            resp.getWriter().print(htmlResponse);
        } else if (req.getRequestURI().equalsIgnoreCase("/transactions")) {
            String id = req.getParameter("id");
            resp.setContentType("application/json; charset=utf-8");
            if (id != null) {
                Transaction transaction = Application.transactionService.findById(id);
                if (transaction == null) {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                } else {
                    String json = Application.objectMapper.writeValueAsString(transaction);
                    resp.getWriter().print(json);
                }
            } else {
                List<Transaction> transactions = Application.transactionService.getAll();
                String json = Application.objectMapper.writeValueAsString(transactions);
                resp.getWriter().print(json);
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getRequestURI().equalsIgnoreCase("/transactions")) {
            String reference = req.getParameter("reference");
            BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(req.getParameter("amount")));
            Transaction transaction = Application.transactionService.create(reference, amount);
            String json = Application.objectMapper.writeValueAsString(transaction);
            resp.setContentType("application/json; charset=utf-8");
            resp.getWriter().print(json);
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
