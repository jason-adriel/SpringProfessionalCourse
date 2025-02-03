package org.jasonadriel.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jasonadriel.context.BankApplicationConfiguration;
import org.jasonadriel.model.Transaction;
import org.jasonadriel.service.TransactionService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class TransactionServlet extends HttpServlet {

    private TransactionService transactionService;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                BankApplicationConfiguration.class
        );
        context.registerShutdownHook();
        this.transactionService = context.getBean(TransactionService.class);
        this.objectMapper = context.getBean(ObjectMapper.class);
    }

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
                Transaction transaction = transactionService.findById(id);
                if (transaction == null) {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                } else {
                    String json = objectMapper.writeValueAsString(transaction);
                    resp.getWriter().print(json);
                }
            } else {
                List<Transaction> transactions = transactionService.getAll();
                String json = objectMapper.writeValueAsString(transactions);
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
            Transaction transaction = transactionService.create(reference, amount);
            String json = objectMapper.writeValueAsString(transaction);
            resp.setContentType("application/json; charset=utf-8");
            resp.getWriter().print(json);
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
