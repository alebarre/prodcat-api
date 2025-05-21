package com.prodcat.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailTemplate {

    public String emailTemplateBuilder(String email, String token){

        return """
            <html lang="pt-BR">
                    <head>
                    <meta charset="UTF-8">
                        <title>PASSWORD RECOVERING</title>
                    <style>
                      body {
                        font-family: Arial, sans-serif;
                        line-height: 1.6;
                        color: #333;
                        font-size: 16px;        /* ← larger base text */
                      }
                      h3 {
                        font-size: 20px;        /* ↑ slightly bigger heading */
                      }
                      p {
                        font-size: 16px;        /* ↑ match body */
                      }
                      h1 {
                        font-size: 32px;        /* ↑ token emphasis */
                      }
                    </style>
                    </head>
                    <body>
                    <h3>Hi %s,</h3>
                        <p>We received an "forgot my password" message from you.</p>
                        <p>Here is the token needed to perform this action:</p>
                    <h1><b>%s</b></h1>
                        <p>Copy and paste this code into your application's password change window.</p>
                        <p>If you did not request this action, please ignore this email.</p>
                    <br/>
                    <p>Regards,<br/>Helpdesk Team.</p>
                    </body>
                    </html>
        """.formatted(email, token);

    }

}
