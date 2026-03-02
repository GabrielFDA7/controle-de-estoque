package view;

import model.Produto;
import repository.ProdutoRepository;

import javax.swing.*;
import java.awt.*;

public class PainelSaida extends JPanel {

    private final JTextField campoIdProduto;
    private final JTextField campoQuantidade;
    private final ProdutoRepository repositorio;

    public PainelSaida() {
        repositorio = new ProdutoRepository();
        setLayout(new GridBagLayout());
        setBorder(EstiloApp.criarBordaPainel());
        EstiloApp.estilizarPainel(this);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel titulo = EstiloApp.criarTitulo("📤 Registro de Saída de Mercadorias");
        titulo.setForeground(EstiloApp.VERMELHO);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 30, 0);
        add(titulo, gbc);

        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 10, 10);

        // ID do Produto
        JLabel lblId = EstiloApp.criarLabel("ID do Produto:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        add(lblId, gbc);

        campoIdProduto = EstiloApp.criarCampoTexto(20);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        add(campoIdProduto, gbc);

        // Quantidade
        JLabel lblQtd = EstiloApp.criarLabel("Quantidade Saída:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        add(lblQtd, gbc);

        campoQuantidade = EstiloApp.criarCampoTexto(20);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        add(campoQuantidade, gbc);

        // Botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        EstiloApp.estilizarPainel(painelBotoes);

        JButton btnSalvar = EstiloApp.criarBotaoVermelho("Registrar Saída");
        btnSalvar.addActionListener(e -> registrarSaida());

        JButton btnLimpar = EstiloApp.criarBotaoSecundario("Limpar");
        btnLimpar.addActionListener(e -> limparCampos());

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnLimpar);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(30, 10, 10, 10);
        add(painelBotoes, gbc);
    }

    private void registrarSaida() {
        String idTexto = campoIdProduto.getText().trim();
        String qtdTexto = campoQuantidade.getText().trim();

        if (idTexto.isEmpty() || qtdTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Os campos de ID e Quantidade são obrigatórios.",
                    "Campo obrigatório", JOptionPane.WARNING_MESSAGE);
            return;
        }

        long id;
        try {
            id = Long.parseLong(idTexto);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "O ID deve ser numérico.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int quantidade;
        try {
            quantidade = Integer.parseInt(qtdTexto);
            if (quantidade <= 0)
                throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Quantidade deve ser um número inteiro positivo.",
                    "Valor inválido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            repositorio.removerEstoque(id, quantidade);
            Produto p = repositorio.buscarPorId(id);
            JOptionPane.showMessageDialog(this,
                    "Saída registrada com sucesso!\nProduto: " + p.getNome() + "\nNovo saldo: " + p.getQuantidade(),
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCampos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro Validando Saldo", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        campoIdProduto.setText("");
        campoQuantidade.setText("");
        campoIdProduto.requestFocus();
    }
}
