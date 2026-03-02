package view;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EstiloApp {

    // Paleta de cores
    public static final Color FUNDO_PRINCIPAL = new Color(30, 30, 46); // #1E1E2E
    public static final Color FUNDO_SECUNDARIO = new Color(42, 42, 61); // #2A2A3D
    public static final Color FUNDO_CAMPO = new Color(55, 55, 78); // #37374E
    public static final Color DESTAQUE = new Color(124, 58, 237); // #7C3AED (roxo)
    public static final Color DESTAQUE_HOVER = new Color(139, 92, 246); // #8B5CF6
    public static final Color VERDE = new Color(34, 197, 94); // #22C55E
    public static final Color VERDE_HOVER = new Color(74, 222, 128); // #4ADE80
    public static final Color VERMELHO = new Color(239, 68, 68); // #EF4444
    public static final Color VERMELHO_HOVER = new Color(248, 113, 113); // #F87171
    public static final Color TEXTO_PRINCIPAL = new Color(228, 228, 231); // #E4E4E7
    public static final Color TEXTO_SECUNDARIO = new Color(161, 161, 170); // #A1A1AA
    public static final Color BORDA = new Color(63, 63, 85); // #3F3F55
    public static final Color LINHA_TABELA_ALT = new Color(35, 35, 52); // #232334

    public static final Font FONTE_TITULO = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font FONTE_LABEL = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FONTE_CAMPO = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONTE_BOTAO = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FONTE_ABA = new Font("Segoe UI", Font.PLAIN, 14);

    public static JButton criarBotao(String texto, Color corFundo, Color corHover) {
        JButton btn = new JButton(texto);
        btn.setFont(FONTE_BOTAO);
        btn.setForeground(Color.WHITE);
        btn.setBackground(corFundo);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(160, 40));
        btn.putClientProperty("JButton.buttonType", "roundRect");

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(corHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(corFundo);
            }
        });

        return btn;
    }

    public static JButton criarBotaoPrimario(String texto) {
        return criarBotao(texto, DESTAQUE, DESTAQUE_HOVER);
    }

    public static JButton criarBotaoSecundario(String texto) {
        JButton btn = criarBotao(texto, FUNDO_CAMPO, BORDA);
        btn.setForeground(TEXTO_SECUNDARIO);
        return btn;
    }

    public static JButton criarBotaoVerde(String texto) {
        return criarBotao(texto, VERDE, VERDE_HOVER);
    }

    public static JButton criarBotaoVermelho(String texto) {
        return criarBotao(texto, VERMELHO, VERMELHO_HOVER);
    }

    public static JTextField criarCampoTexto(int colunas) {
        JTextField campo = new JTextField(colunas);
        campo.setFont(FONTE_CAMPO);
        campo.setBackground(FUNDO_CAMPO);
        campo.setForeground(TEXTO_PRINCIPAL);
        campo.setCaretColor(TEXTO_PRINCIPAL);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDA, 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        return campo;
    }

    public static JLabel criarLabel(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(FONTE_LABEL);
        lbl.setForeground(TEXTO_PRINCIPAL);
        return lbl;
    }

    public static JLabel criarTitulo(String texto) {
        JLabel lbl = new JLabel(texto, SwingConstants.CENTER);
        lbl.setFont(FONTE_TITULO);
        lbl.setForeground(DESTAQUE);
        return lbl;
    }

    public static JComboBox<String> criarComboBox(String[] itens) {
        JComboBox<String> combo = new JComboBox<>(itens);
        combo.setFont(FONTE_CAMPO);
        combo.setBackground(FUNDO_CAMPO);
        combo.setForeground(TEXTO_PRINCIPAL);
        return combo;
    }

    public static void estilizarPainel(JPanel painel) {
        painel.setBackground(FUNDO_PRINCIPAL);
    }

    public static Border criarBordaPainel() {
        return BorderFactory.createEmptyBorder(30, 50, 30, 50);
    }
}
