package model;

public abstract class Equipamento {
    private int patrimonio;
    private String descricao;
    private String laboratorio;
    private String situacao;

    public Equipamento(int patrimonio, String descricao, String laboratorio, String situacao) {
        this.patrimonio = patrimonio;
        this.descricao = descricao;
        this.laboratorio = laboratorio;
        this.situacao = situacao;
    }

    public int getPatrimonio() { return patrimonio; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public String getLaboratorio() { return laboratorio; }
    public void setLaboratorio(String laboratorio) { this.laboratorio = laboratorio; }
    public String getSituacao() { return situacao; }
    public void setSituacao(String situacao) { this.situacao = situacao; }

    public abstract String getTipo();
    public abstract String getAtributoEspecifico();

    @Override
    public String toString() {
        return "Patrimônio: " + patrimonio + " | Tipo: " + getTipo()
                + " | Descrição: " + descricao + " | Laboratório: " + laboratorio
                + " | Situação: " + situacao + " | " + getAtributoEspecifico();
    }
}