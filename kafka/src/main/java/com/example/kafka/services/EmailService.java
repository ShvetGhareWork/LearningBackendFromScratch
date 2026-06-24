package com.example.kafka.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    public void sendWelcomeEmail(String toEmail) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            // Enable HTML and UTF-8 encoding
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            
            helper.setTo(toEmail);
            helper.setSubject("Welcome to Our Platform! 🚀");
            helper.setText(getHtmlContent(toEmail), true); // 'true' flags that this content is HTML

            javaMailSender.send(mimeMessage);
            System.out.println("HTML Email sent successfully to " + toEmail);
        } catch (MessagingException e) {
            System.err.println("Failed to send HTML email: " + e.getMessage());
            throw new RuntimeException("Error sending welcome email", e);
        }
    }

    private String getHtmlContent(String emailAddress) {
        // Extract the username/handle from email to personalize it
        String username = emailAddress.split("@")[0];
        
        return "<!DOCTYPE html>" +
               "<html>" +
               "<head>" +
               "  <meta charset='utf-8'>" +
               "  <meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
               "  <title>Welcome to Our Platform</title>" +
               "</head>" +
               "<body style='margin: 0; padding: 0; background-color: #f8fafc; font-family: -apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, Helvetica, Arial, sans-serif;'>" +
               "  <table align='center' border='0' cellpadding='0' cellspacing='0' width='100%' style='max-width: 600px; margin: 40px auto; background-color: #ffffff; border-radius: 12px; box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.05), 0 2px 4px -1px rgba(0, 0, 0, 0.03); border: 1px solid #e2e8f0; overflow: hidden;'>" +
               "    <!-- Header with modern gradient -->" +
               "    <tr>" +
               "      <td style='padding: 40px 30px; text-align: center; background: linear-gradient(135deg, #4f46e5 0%, #7c3aed 100%);'>" +
               "        <h1 style='margin: 0; color: #ffffff; font-size: 28px; font-weight: 700; letter-spacing: -0.5px;'>Welcome aboard!</h1>" +
               "        <p style='margin: 8px 0 0 0; color: #e0e7ff; font-size: 16px;'>Your journey starts here.</p>" +
               "      </td>" +
               "    </tr>" +
               "    " +
               "    <!-- Main Body Content -->" +
               "    <tr>" +
               "      <td style='padding: 40px 30px; color: #334155;'>" +
               "        <p style='margin: 0 0 20px 0; font-size: 18px; line-height: 1.6; font-weight: 600;'>Hello " + username + ",</p>" +
               "        <p style='margin: 0 0 24px 0; font-size: 16px; line-height: 1.6; color: #475569;'>" +
               "          We are absolutely thrilled to have you join our community! Your account is officially set up and ready to go. Here at our platform, we strive to build high-performance systems and provide the best-in-class backend solutions." +
               "        </p>" +
               "        " +
               "        <!-- Call to Action Button -->" +
               "        <table align='center' border='0' cellpadding='0' cellspacing='0' style='margin: 30px auto;'>" +
               "          <tr>" +
               "            <td align='center' style='border-radius: 8px; background-color: #4f46e5;'>" +
               "              <a href='#' target='_blank' style='display: inline-block; padding: 14px 30px; font-size: 16px; font-weight: 600; color: #ffffff; text-decoration: none; border-radius: 8px;'>Get Started Now &rarr;</a>" +
               "            </td>" +
               "          </tr>" +
               "        </table>" +
               "        " +
               "        <hr style='border: 0; border-top: 1px solid #f1f5f9; margin: 30px 0;'>" +
               "        " +
               "        <p style='margin: 0 0 8px 0; font-size: 14px; color: #64748b;'>Need help? Have questions?</p>" +
               "        <p style='margin: 0; font-size: 14px; line-height: 1.6; color: #64748b;'>" +
               "          Reply directly to this email or visit our <a href='#' style='color: #4f46e5; text-decoration: none;'>Help Center</a>. Our support team is available 24/7." +
               "        </p>" +
               "      </td>" +
               "    </tr>" +
               "    " +
               "    <!-- Footer -->" +
               "    <tr>" +
               "      <td style='padding: 30px; background-color: #f8fafc; border-top: 1px solid #f1f5f9; text-align: center; color: #94a3b8; font-size: 12px; line-height: 1.5;'>" +
               "        <p style='margin: 0 0 8px 0;'>&copy; 2026 YourCompany Inc. All rights reserved.</p>" +
               "        <p style='margin: 0;'>You received this email because you registered on our platform. If you wish to unsubscribe, you can <a href='#' style='color: #64748b; text-decoration: underline;'>update your email preferences</a>.</p>" +
               "      </td>" +
               "    </tr>" +
               "  </table>" +
               "</body>" +
               "</html>";
    }
}