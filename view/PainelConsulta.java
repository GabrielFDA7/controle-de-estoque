package view;

import model.Produto;
import repository.ProdutoRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PainelConsulta extends JPanel {

    private final JTextField campoBusca;
    private final JComboBox<String> comboTipoBusca;
    private final DefaultTableModel modeloTabela;
    private final ProdutoRepository repositorio;

    public PainelConsulta() {
        repositorio = new ProdutoRepository();
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Font fontePadrao = new Font("Segoe UI", Font.PLAIN, 14);

        // --- Painel de busca (topo) ---
        JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));

        JLabel lblBuscar = new JLabel("Buscar:");
        lblBuscar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        painelBusca.add(lblBuscar);

        comboTipoBusca = new JComboBox<>(new String[] { "Por Nome", "Por ID" });
        comboTipoBusca.setFont(fontePadrao);
        painelBusca.add(comboTipoBusca);

        campoBusca = new JTextField(20);
        campoBusca.setFont(fontePadrao);
        painelBusca.add(campoBusca);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnBuscar.addActionListener(e -> buscar());
        painelBusca.add(btnBuscar);

        JButton btnListarTodos = new JButton("Listar Todos");
        btnListarTodos.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnListarTodos.addActionListener(e -> listarTodos());
        painelBusca.add(btnListarTodos);

        add(painelBusca, BorderLayout.NORTH);

        // --- Tabela (centro) ---
        String[] colunas = { "ID", "Nome", "Quantidade", "Preço (R$)" };
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tabela = new JTable(modeloTabela);
        tabela.setFont(fontePadrao);
        tabela.setRowHeight(25);
        tabela.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabela.getTableHeader().setReorderingAllowed(false);

        // Largura das colunas
        tabela.getColumnModel().getColumn(0).setPreferredWidth(50); // ID
        tabela.getColumnModel().getColumn(1).setPreferredWidth(250); // Nome
        tabela.getColumnModel().getColumn(2).setPreferredWidth(100); // Quantidade
        tabela.getColumnModel().getColumn(3).setPreferredWidth(100); // Preço

        JScrollPane scrollPane = new JScrollPane(tabela);
        add(scrollPane, BorderLayout.CENTER);

        // --- Rodapé com contagem ---
        JPanel painelRodape = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelRodape.add(new JLabel("Dica: use 'Listar Todos' para ver o estoque completo."));
        add(painelRodape, BorderLayout.SOUTH);

        // Carregar todos ao abrir
        listarTodos();
    }

    private void buscar() {
        String termo = campoBusca.getText().trim();

        if (termo.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Digite um termo para buscar.",
                    "Campo vazio", JOptionPane.WARNING_MESSAGE);
            campoBusca.requestFocus();
            return;
        }

        String tipoBusca = (String) comboTipoBusca.getSelectedItem();

        try {
            if ("Por ID".equals(tipoBusca)) {
                long id;
                try {
                    id = Long.parseLong(termo);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this,
                            "O ID deve ser um número inteiro.",
                            "Valor inválido", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Produto produto = repositorio.buscarPorId(id);

                modeloTabela.setRowCount(0);
                if (produto == null) {
                    JOptionPane.showMessageDialog(this,
                            "Produto não encontrado com o ID: " + id,
                            "Não encontrado", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    adicionarLinhaTabela(produto);
                }

            } else {
                List<Produto> resultados = repositorio.buscarPorNome(termo);

                modeloTabela.setRowCount(0);
                if (resultados.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Nenhum produto encontrado com o nome: \"" + termo + "\"",
                            "Não encontrado", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    for (Produto p : resultados) {
                        adicionarLinhaTabela(p);
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao buscar: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listarTodos() {
        try {
            List<Produto> produtos = repositorio.listarTodos();
            modeloTabela.setRowCount(0);

            if (produtos.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Nenhum produto cadastrado no estoque.",
                        "Estoque vazio", JOptionPane.INFORMATION_MESSAGE);
            } else {
                for (Produto p : produtos) {
                    adicionarLinhaTabela(p);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao listar produtos: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void adicionarLinhaTabela(Produto p) {
        modeloTabela.addRow(new Object[] {
                p.getId(),
                p.getNome(),
                p.getQuantidade(),
                String.format("R$ %.2f", p.getPreco())
        });
    }
}
