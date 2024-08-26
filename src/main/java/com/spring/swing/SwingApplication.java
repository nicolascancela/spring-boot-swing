package com.spring.swing;
import com.spring.swing.view.VistaPrincipal;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import javax.swing.SwingUtilities;

@SpringBootApplication
public class SwingApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(SwingApplication.class)
                .headless(false)
                .run(args);

        SwingUtilities.invokeLater(() -> {
            VistaPrincipal vistaPrincipal = context.getBean(VistaPrincipal.class);
            vistaPrincipal.setVisible(true);
        });
    }
}