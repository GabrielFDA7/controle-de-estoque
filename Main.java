import com.formdev.flatlaf.FlatDarkLaf;
import database.ConexaoBanco;
import view.EstiloApp;
import view.TelaPrincipal;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        ConexaoBanco.criarTabela();

        // Configura o FlatLaf Dark como Look and Feel
        FlatDarkLaf.setup();

        // Customizações globais do UIManager
        UIManager.put("Component.arc", 10);
        UIManager.put("Button.arc", 10);
        UIManager.put("TextComponent.arc", 8);
        UIManager.put("Component.focusWidth", 1);
        UIManager.put("Component.focusColor", EstiloApp.DESTAQUE);
        UIManager.put("Component.borderColor", EstiloApp.BORDA);
        UIManager.put("Panel.background", EstiloApp.FUNDO_PRINCIPAL);
        UIManager.put("TabbedPane.selectedBackground", EstiloApp.FUNDO_PRINCIPAL);
        UIManager.put("TabbedPane.underlineColor", EstiloApp.DESTAQUE);
        UIManager.put("TabbedPane.hoverColor", EstiloApp.FUNDO_SECUNDARIO);
        UIManager.put("Table.alternateRowColor", EstiloApp.LINHA_TABELA_ALT);
        UIManager.put("ScrollBar.thumbArc", 999);
        UIManager.put("ScrollBar.thumbInsets", new Insets(2, 2, 2, 2));

        SwingUtilities.invokeLater(() -> {
            TelaPrincipal tela = new TelaPrincipal();
            tela.setVisible(true);
        });
    }
}
