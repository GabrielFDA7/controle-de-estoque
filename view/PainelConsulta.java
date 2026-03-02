package view;

import model.Produto;
import repository.ProdutoRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
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
        EstiloApp.estilizarPainel(this);

        // --- Painel de busca ---
        JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        EstiloApp.estilizarPainel(painelBusca);

        JLabel lblBuscar = EstiloApp.criarLabel("Buscar:");
        painelBusca.add(lblBuscar);

        comboTipoBusca = EstiloApp.criarComboBox(new String[] { "Por Nome", "Por ID" });
        painelBusca.add(comboTipoBusca);

        campoBusca = EstiloApp.criarCampoTexto(20);
        painelBusca.add(campoBusca);

        JButton btnBuscar = EstiloApp.criarBotaoPrimario("Buscar");
        btnBuscar.setPreferredSize(new Dimension(120, 40));
        btnBuscar.addActionListener(e -> buscar());
        painelBusca.add(btnBuscar);

        JButton btnListarTodos = EstiloApp.criarBotaoSecundario("Listar Todos");
        btnListarTodos.setPreferredSize(new Dimension(140, 40));
        btnListarTodos.addActionListener(e -> listarTodos());
        painelBusca.add(btnListarTodos);

        add(painelBusca, BorderLayout.NORTH);

        // --- Tabela ---
        String[] colunas = { "ID", "Nome", "Quantidade", "Preço (R$)" };
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tabela = new JTable(modeloTabela);
        tabela.setFont(EstiloApp.FONTE_CAMPO);
        tabela.setRowHeight(32);
        tabela.setBackground(EstiloApp.FUNDO_SECUNDARIO);
        tabela.setForeground(EstiloApp.TEXTO_PRINCIPAL);
        tabela.setSelectionBackground(EstiloApp.DESTAQUE);
        tabela.setSelectionForeground(Color.WHITE);
        tabela.setGridColor(EstiloApp.BORDA);
        tabela.setShowGrid(true);
        tabela.setIntercellSpacing(new Dimension(0, 1));

        // Header da tabela
        JTableHeader header = tabela.getTableHeader();
        header.setFont(EstiloApp.FONTE_LABEL);
        header.setBackground(EstiloApp.FUNDO_CAMPO);
        header.setForeground(EstiloApp.TEXTO_PRINCIPAL);
        header.setReorderingAllowed(false);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, EstiloApp.DESTAQUE));

        // Renderer para linhas alternadas
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? EstiloApp.FUNDO_SECUNDARIO : EstiloApp.LINHA_TABELA_ALT);
                    c.setForeground(EstiloApp.TEXTO_PRINCIPAL);
                }
                setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
                return c;
            }
        };
        for (int i = 0; i < tabela.getColumnCount(); i++) {
            tabela.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        tabela.getColumnModel().getColumn(0).setPreferredWidth(60);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(300);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(120);
        tabela.getColumnModel().getColumn(3).setPreferredWidth(120);

        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.getViewport().setBackground(EstiloApp.FUNDO_SECUNDARIO);
        scrollPane.setBorder(BorderFactory.createLineBorder(EstiloApp.BORDA, 1));
        add(scrollPane, BorderLayout.CENTER);

        // --- Rodapé ---
        JPanel painelRodape = new JPanel(new FlowLayout(FlowLayout.LEFT));
        EstiloApp.estilizarPainel(painelRodape);
        JLabel dica = new JLabel("💡 Use 'Listar Todos' para ver o estoque completo.");
        dica.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        dica.setForeground(EstiloApp.TEXTO_SECUNDARIO);
        painelRodape.add(dica);
        add(painelRodape, BorderLayout.SOUTH);

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
