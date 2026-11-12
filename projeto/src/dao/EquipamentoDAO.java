package dao;

import conexao.Conexao;
import model.Computador;
import model.Equipamento;
import model.Projetor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EquipamentoDAO {

    public void inserir(Equipamento e) throws SQLException {
        String sql = "INSERT INTO equipamento (patrimonio, tipo, descricao, laboratorio, situacao, memoria_ram, luminosidade) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexao.getConexao(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, e.getPatrimonio());
            ps.setString(2, e.getTipo());
            ps.setString(3, e.getDescricao());
            ps.setString(4, e.getLaboratorio());
            ps.setString(5, e.getSituacao());
            if (e instanceof Computador) {
                ps.setInt(6, ((Computador) e).getMemoriaRAM());
                ps.setNull(7, java.sql.Types.INTEGER);
            } else {
                ps.setNull(6, java.sql.Types.INTEGER);
                ps.setInt(7, ((Projetor) e).getLuminosidade());
            }
            ps.executeUpdate();
        }
    }

    public List<Equipamento> listarTodos() throws SQLException {
        String sql = "SELECT * FROM equipamento ORDER BY patrimonio";
        List<Equipamento> lista = new ArrayList<>();
        try (Connection con = Conexao.getConexao();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(montar(rs));
        }
        return lista;
    }

    public List<Equipamento> listarPorLaboratorio(String laboratorio) throws SQLException {
        String sql = "SELECT * FROM equipamento WHERE laboratorio = ? ORDER BY patrimonio";
        List<Equipamento> lista = new ArrayList<>();
        try (Connection con = Conexao.getConexao(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, laboratorio);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(montar(rs));
            }
        }
        return lista;
    }

    public Equipamento buscarPorPatrimonio(int patrimonio) throws SQLException {
        String sql = "SELECT * FROM equipamento WHERE patrimonio = ?";
        try (Connection con = Conexao.getConexao(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, patrimonio);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? montar(rs) : null;
            }
        }
    }

    public boolean existe(int patrimonio) throws SQLException {
        return buscarPorPatrimonio(patrimonio) != null;
    }

    public void atualizar(Equipamento e) throws SQLException {
        String sql = "UPDATE equipamento SET descricao = ?, laboratorio = ?, situacao = ?, "
                + "memoria_ram = ?, luminosidade = ? WHERE patrimonio = ?";
        try (Connection con = Conexao.getConexao(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, e.getDescricao());
            ps.setString(2, e.getLaboratorio());
            ps.setString(3, e.getSituacao());
            if (e instanceof Computador) {
                ps.setInt(4, ((Computador) e).getMemoriaRAM());
                ps.setNull(5, java.sql.Types.INTEGER);
            } else {
                ps.setNull(4, java.sql.Types.INTEGER);
                ps.setInt(5, ((Projetor) e).getLuminosidade());
            }
            ps.setInt(6, e.getPatrimonio());
            ps.executeUpdate();
        }
    }

    public void excluir(int patrimonio) throws SQLException {
        String sql = "DELETE FROM equipamento WHERE patrimonio = ?";
        try (Connection con = Conexao.getConexao(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, patrimonio);
            ps.executeUpdate();
        }
    }

    private Equipamento montar(ResultSet rs) throws SQLException {
        int pat = rs.getInt("patrimonio");
        String desc = rs.getString("descricao");
        String lab = rs.getString("laboratorio");
        String sit = rs.getString("situacao");
        if ("COMPUTADOR".equals(rs.getString("tipo"))) {
            return new Computador(pat, desc, lab, sit, rs.getInt("memoria_ram"));
        }
        return new Projetor(pat, desc, lab, sit, rs.getInt("luminosidade"));
    }
}