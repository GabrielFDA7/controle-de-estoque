package view;

import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        setTitle("Sistema de Controle de Estoque (SCE)");
        setSize(750, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JTabbedPane abas = new JTabbedPane();
        abas.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        abas.addTab("📦 Cadastro de Produto", new PainelCadastro());
        abas.addTab("🔍 Consulta de Produto", new PainelConsulta());

        add(abas);
    }
}
