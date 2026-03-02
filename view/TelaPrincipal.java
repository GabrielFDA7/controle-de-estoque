package view;

import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        setTitle("Sistema de Controle de Estoque (SCE)");
        setSize(900, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(EstiloApp.FUNDO_PRINCIPAL);

        JTabbedPane abas = new JTabbedPane();
        abas.setFont(EstiloApp.FONTE_ABA);
        abas.setBackground(EstiloApp.FUNDO_PRINCIPAL);
        abas.setForeground(EstiloApp.TEXTO_PRINCIPAL);
        abas.putClientProperty("JTabbedPane.tabHeight", 40);

        abas.addTab("  📦 Cadastro  ", new PainelCadastro());
        abas.addTab("  🔍 Consulta  ", new PainelConsulta());
        abas.addTab("  📥 Entrada  ", new PainelEntrada());
        abas.addTab("  📤 Saída  ", new PainelSaida());

        add(abas);
    }
}
