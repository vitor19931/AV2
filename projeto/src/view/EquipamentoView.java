package view;

import controller.EquipamentoController;
import model.Computador;
import model.Equipamento;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Dimension;
import java.sql.SQLException;
import java.util.List;

public class EquipamentoView {
    private final EquipamentoController controller = new EquipamentoController();

    public void iniciar() {
        String menu = "SISTEMA DE CONTROLE DE EQUIPAMENTOS\n\n"
                + "1 - Cadastrar computador\n2 - Cadastrar projetor\n3 - Listar todos\n"
                + "4 - Pesquisar por patrimônio\n5 - Alterar equipamento\n6 - Excluir equipamento\n"
                + "7 - Listar por laboratório\n0 - Sair";
        while (true) {
            String op = JOptionPane.showInputDialog(null, menu, "Equipamentos", JOptionPane.PLAIN_MESSAGE);
            if (op == null || op.trim().equals("0")) break;
            try {
                switch (op.trim()) {
                    case "1": cadastrarComputador(); break;
                    case "2": cadastrarProjetor(); break;
                    case "3": mostrarLista(controller.listarTodos(), "Nenhum equipamento cadastrado."); break;
                    case "4": pesquisar(); break;
                    case "5": alterar(); break;
                    case "6": excluir(); break;
                    case "7": listarPorLaboratorio(); break;
                    default: erro("Opção inválida.");
                }
            } catch (NumberFormatException ex) {
                erro("Digite apenas números inteiros nos campos numéricos.");
            } catch (IllegalArgumentException ex) {
                erro(ex.getMessage());
            } catch (SQLException ex) {
                erro("Erro de banco de dados: " + ex.getMessage());
            } catch (CancelException ex) {
                // Operação cancelada pelo usuário
            }
        }
    }

    private static class CancelException extends RuntimeException {}

    // ---------- Operações ----------
    private void cadastrarComputador() throws SQLException {
        int pat = lerInt("Número de patrimônio:");
        String desc = lerTexto("Descrição:");
        String lab = lerTexto("Laboratório:");
        String sit = lerSituacao();
        int ram = lerInt("Memória RAM (GB):");
        controller.cadastrarComputador(pat, desc, lab, sit, ram);
        info("Computador cadastrado com sucesso!");
    }

    private void cadastrarProjetor() throws SQLException {
        int pat = lerInt("Número de patrimônio:");
        String desc = lerTexto("Descrição:");
        String lab = lerTexto("Laboratório:");
        String sit = lerSituacao();
        int lumens = lerInt("Luminosidade (lúmens):");
        controller.cadastrarProjetor(pat, desc, lab, sit, lumens);
        info("Projetor cadastrado com sucesso!");
    }

    private void pesquisar() throws SQLException {
        int pat = lerInt("Número de patrimônio a pesquisar:");
        info(controller.pesquisar(pat).toString());
    }

    private void alterar() throws SQLException {
        int pat = lerInt("Patrimônio do equipamento a alterar:");
        Equipamento atual = controller.pesquisar(pat);
        info("Registro atual:\n" + atual);
        String desc = lerTexto("Nova descrição:");
        String lab = lerTexto("Novo laboratório:");
        String sit = lerSituacao();
        String rotulo = atual instanceof Computador ? "Nova memória RAM (GB):" : "Nova luminosidade (lúmens):";
        int esp = lerInt(rotulo);
        controller.alterar(pat, desc, lab, sit, esp);
        info("Equipamento alterado com sucesso!");
    }

    private void excluir() throws SQLException {
        int pat = lerInt("Patrimônio do equipamento a excluir:");
        Equipamento e = controller.pesquisar(pat);
        int r = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir?\n\n" + e,
                "Confirmar exclusão", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (r == JOptionPane.YES_OPTION) {
            controller.excluir(pat);
            info("Equipamento excluído com sucesso!");
        } else {
            info("Exclusão cancelada.");
        }
    }

    private void listarPorLaboratorio() throws SQLException {
        String lab = lerTexto("Nome do laboratório:");
        mostrarLista(controller.listarPorLaboratorio(lab), "Nenhum equipamento encontrado nesse laboratório.");
    }

    // ---------- Auxiliares ----------
    private void mostrarLista(List<Equipamento> lista, String vazio) {
        if (lista.isEmpty()) { info(vazio); return; }
        StringBuilder sb = new StringBuilder();
        for (Equipamento e : lista) sb.append(e).append("\n");

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(680, 260));

        JOptionPane.showMessageDialog(null, scrollPane, "Equipamentos", JOptionPane.INFORMATION_MESSAGE);
    }

    private String lerTexto(String msg) {
        String s = JOptionPane.showInputDialog(null, msg);
        if (s == null) throw new CancelException();
        return s;
    }

    private int lerInt(String msg) {
        return Integer.parseInt(lerTexto(msg).trim());
    }

    private String lerSituacao() {
        Object s = JOptionPane.showInputDialog(null, "Situação:", "Situação", JOptionPane.QUESTION_MESSAGE,
                null, EquipamentoController.SITUACOES, EquipamentoController.SITUACOES[0]);
        if (s == null) throw new CancelException();
        return s.toString();
    }

    private void info(String msg) { JOptionPane.showMessageDialog(null, msg, "Informação", JOptionPane.INFORMATION_MESSAGE); }
    private void erro(String msg) { JOptionPane.showMessageDialog(null, msg, "Erro", JOptionPane.ERROR_MESSAGE); }
}