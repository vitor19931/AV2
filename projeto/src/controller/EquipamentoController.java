package controller;

import dao.EquipamentoDAO;
import model.Computador;
import model.Equipamento;
import model.Projetor;

import java.sql.SQLException;
import java.util.List;

public class EquipamentoController {
    public static final String[] SITUACOES = {"Disponível", "Em uso", "Em manutenção"};

    private final EquipamentoDAO dao = new EquipamentoDAO();

    // ---------- Regras de negócio ----------
    private void validarTexto(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty())
            throw new IllegalArgumentException(campo + " não pode estar vazio.");
    }

    private void validarSituacao(String situacao) {
        for (String s : SITUACOES) if (s.equals(situacao)) return;
        throw new IllegalArgumentException("Situação inválida. Use: Disponível, Em uso ou Em manutenção.");
    }

    private void validarPositivo(int valor, String campo) {
        if (valor <= 0) throw new IllegalArgumentException(campo + " deve ser um número inteiro maior que zero.");
    }

    // ---------- RF01 ----------
    public void cadastrarComputador(int patrimonio, String descricao, String lab, String situacao, int ram)
            throws SQLException {
        validarPositivo(patrimonio, "Patrimônio");
        validarTexto(descricao, "Descrição");
        validarTexto(lab, "Laboratório");
        validarSituacao(situacao);
        validarPositivo(ram, "Memória RAM");
        if (dao.existe(patrimonio)) throw new IllegalArgumentException("Já existe equipamento com o patrimônio " + patrimonio + ".");
        dao.inserir(new Computador(patrimonio, descricao.trim(), lab.trim(), situacao, ram));
    }

    public void cadastrarProjetor(int patrimonio, String descricao, String lab, String situacao, int lumens)
            throws SQLException {
        validarPositivo(patrimonio, "Patrimônio");
        validarTexto(descricao, "Descrição");
        validarTexto(lab, "Laboratório");
        validarSituacao(situacao);
        validarPositivo(lumens, "Luminosidade");
        if (dao.existe(patrimonio)) throw new IllegalArgumentException("Já existe equipamento com o patrimônio " + patrimonio + ".");
        dao.inserir(new Projetor(patrimonio, descricao.trim(), lab.trim(), situacao, lumens));
    }

    // ---------- RF02 ----------
    public List<Equipamento> listarTodos() throws SQLException { return dao.listarTodos(); }

    // ---------- RF03 ----------
    public Equipamento pesquisar(int patrimonio) throws SQLException {
        validarPositivo(patrimonio, "Patrimônio");
        Equipamento e = dao.buscarPorPatrimonio(patrimonio);
        if (e == null) throw new IllegalArgumentException("Nenhum equipamento encontrado com o patrimônio " + patrimonio + ".");
        return e;
    }

    // ---------- RF04 ----------
    public void alterar(int patrimonio, String descricao, String lab, String situacao, int atributoEspecifico)
            throws SQLException {
        Equipamento atual = pesquisar(patrimonio);
        validarTexto(descricao, "Descrição");
        validarTexto(lab, "Laboratório");
        validarSituacao(situacao);
        validarPositivo(atributoEspecifico, atual instanceof Computador ? "Memória RAM" : "Luminosidade");
        if (atual instanceof Computador) {
            dao.atualizar(new Computador(patrimonio, descricao.trim(), lab.trim(), situacao, atributoEspecifico));
        } else {
            dao.atualizar(new Projetor(patrimonio, descricao.trim(), lab.trim(), situacao, atributoEspecifico));
        }
    }

    // ---------- RF05 ----------
    public void excluir(int patrimonio) throws SQLException {
        pesquisar(patrimonio);
        dao.excluir(patrimonio);
    }

    // ---------- RF06 ----------
    public List<Equipamento> listarPorLaboratorio(String lab) throws SQLException {
        validarTexto(lab, "Laboratório");
        return dao.listarPorLaboratorio(lab.trim());
    }
}