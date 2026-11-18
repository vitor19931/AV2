package controller;
import dao.EquipamentoDAO;
import model.Computador;
import model.Equipamento;
import model.Projetor;
import java.sql.SQLException;
import java.util.List;

public class EquipamentoController {
    public static final String[] SITUACOES = {"Disponível", "Em uso", "Em manutenção"};
    public static final int MAX_DESCRICAO = 100;
    public static final int MAX_LABORATORIO = 50;
    private final EquipamentoDAO dao;
    public EquipamentoController() { this(new EquipamentoDAO()); }
    public EquipamentoController(EquipamentoDAO dao) { this.dao = dao; }

    private void validarTexto(String valor, String campo, int max) {
        if (valor == null || valor.trim().isEmpty())
            throw new IllegalArgumentException(campo + " não pode estar vazio.");
        if (valor.trim().length() > max)
            throw new IllegalArgumentException(campo + " deve ter no máximo " + max + " caracteres.");
    }

    private void validarSituacao(String situacao) {
        for (String s : SITUACOES) if (s.equals(situacao)) return;
        throw new IllegalArgumentException("Situação inválida. Use: Disponível, Em uso ou Em manutenção.");
    }

    private void validarPositivo(int valor, String campo) {
        if (valor <= 0) throw new IllegalArgumentException(campo + " deve ser um número inteiro maior que zero.");
    }

    private void validarComuns(String descricao, String lab, String situacao) {
        validarTexto(descricao, "Descrição", MAX_DESCRICAO);
        validarTexto(lab, "Laboratório", MAX_LABORATORIO);
        validarSituacao(situacao);
    }

    private void garantirPatrimonioLivre(int patrimonio) throws SQLException {
        if (dao.existe(patrimonio))
            throw new IllegalArgumentException("Já existe equipamento com o patrimônio " + patrimonio + ".");
    }
    public void cadastrarComputador(int patrimonio, String descricao, String lab, String situacao, int ram)
            throws SQLException {
        validarPositivo(patrimonio, "Patrimônio");
        validarComuns(descricao, lab, situacao);
        validarPositivo(ram, "Memória RAM");
        garantirPatrimonioLivre(patrimonio);
        dao.inserir(new Computador(patrimonio, descricao.trim(), lab.trim(), situacao, ram));
    }
    public void cadastrarProjetor(int patrimonio, String descricao, String lab, String situacao, int lumens)
            throws SQLException {
        validarPositivo(patrimonio, "Patrimônio");
        validarComuns(descricao, lab, situacao);
        validarPositivo(lumens, "Luminosidade");
        garantirPatrimonioLivre(patrimonio);
        dao.inserir(new Projetor(patrimonio, descricao.trim(), lab.trim(), situacao, lumens));
    }
    public List<Equipamento> listarTodos() throws SQLException { return dao.listarTodos(); }

    public Equipamento pesquisar(int patrimonio) throws SQLException {
        Equipamento e = dao.buscarPorPatrimonio(patrimonio);
        if (e == null)
            throw new IllegalArgumentException("Nenhum equipamento encontrado com o patrimônio " + patrimonio + ".");
        return e;
    }

    public void alterar(int patrimonio, String descricao, String lab, String situacao, int atributoEspecifico)
            throws SQLException {
        Equipamento atual = pesquisar(patrimonio); // confirma que o registro existe
        validarComuns(descricao, lab, situacao);
        validarPositivo(atributoEspecifico, atual instanceof Computador ? "Memória RAM" : "Luminosidade");
        if (atual instanceof Computador) {
            dao.atualizar(new Computador(patrimonio, descricao.trim(), lab.trim(), situacao, atributoEspecifico));
        } else {
            dao.atualizar(new Projetor(patrimonio, descricao.trim(), lab.trim(), situacao, atributoEspecifico));
        }
    }
    public void excluir(int patrimonio) throws SQLException {
        pesquisar(patrimonio); // confirma que existe
        dao.excluir(patrimonio);
    }
    public List<Equipamento> listarPorLaboratorio(String lab) throws SQLException {
        validarTexto(lab, "Laboratório", MAX_LABORATORIO);
        return dao.listarPorLaboratorio(lab.trim());
    }
}
