import database.ConexaoBanco;
import view.TelaPrincipal;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        // Criar tabela no banco de dados
        ConexaoBanco.criarTabela();

        // Iniciar interface gráfica
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                // Usa o look and feel padrão se falhar
            }

            TelaPrincipal tela = new TelaPrincipal();
            tela.setVisible(true);
        });
    }
}
