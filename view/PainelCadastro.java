package view;

import model.Produto;
import repository.ProdutoRepository;

import javax.swing.*;
import java.awt.*;

public class PainelCadastro extends JPanel {

    private final JTextField campoNome;
    private final JTextField campoQuantidade;
    private final JTextField campoPreco;
    private final ProdutoRepository repositorio;

    public PainelCadastro() {
        repositorio = new ProdutoRepository();
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);

        // Título
        JLabel titulo = new JLabel("Cadastro de Novo Produto", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 25, 0);
        add(titulo, gbc);

        gbc.gridwidth = 1;
        gbc.insets = new Insets(8, 8, 8, 8);

        // Nome
        JLabel lblNome = new JLabel("Nome:");
        lblNome.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        add(lblNome, gbc);

        campoNome = new JTextField(20);
        campoNome.setFont(fieldFont);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        add(campoNome, gbc);

        // Quantidade
        JLabel lblQtd = new JLabel("Quantidade:");
        lblQtd.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        add(lblQtd, gbc);

        campoQuantidade = new JTextField(20);
        campoQuantidade.setFont(fieldFont);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        add(campoQuantidade, gbc);

        // Preço
        JLabel lblPreco = new JLabel("Preço (R$):");
        lblPreco.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        add(lblPreco, gbc);

        campoPreco = new JTextField(20);
        campoPreco.setFont(fieldFont);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        add(campoPreco, gbc);

        // Botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSalvar.setPreferredSize(new Dimension(120, 35));
        btnSalvar.addActionListener(e -> salvarProduto());

        JButton btnLimpar = new JButton("Limpar");
        btnLimpar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnLimpar.setPreferredSize(new Dimension(120, 35));
        btnLimpar.addActionListener(e -> limparCampos());

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnLimpar);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(25, 8, 8, 8);
        add(painelBotoes, gbc);
    }

    private void salvarProduto() {
        String nome = campoNome.getText().trim();
        String qtdTexto = campoQuantidade.getText().trim();
        String precoTexto = campoPreco.getText().trim().replace(",", ".");

        // Validações
        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "O nome do produto é obrigatório.",
                    "Campo obrigatório", JOptionPane.WARNING_MESSAGE);
            campoNome.requestFocus();
            return;
        }

        int quantidade;
        try {
            quantidade = Integer.parseInt(qtdTexto);
            if (quantidade < 0)
                throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Quantidade deve ser um número inteiro positivo.",
                    "Valor inválido", JOptionPane.WARNING_MESSAGE);
            campoQuantidade.requestFocus();
            return;
        }

        double preco;
        try {
            preco = Double.parseDouble(precoTexto);
            if (preco < 0)
                throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Preço deve ser um número válido (ex: 19.90).",
                    "Valor inválido", JOptionPane.WARNING_MESSAGE);
            campoPreco.requestFocus();
            return;
        }

        // Salvar
        try {
            Produto produto = new Produto(nome, quantidade, preco);
            repositorio.salvar(produto);

            JOptionPane.showMessageDialog(this,
                    "Produto cadastrado com sucesso!\nID: " + produto.getId(),
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            limparCampos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao salvar produto: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        campoNome.setText("");
        campoQuantidade.setText("");
        campoPreco.setText("");
        campoNome.requestFocus();
    }
}
