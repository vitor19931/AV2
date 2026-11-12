package model;

public class Projetor extends Equipamento {
    private int luminosidade; // em lúmens

    public Projetor(int patrimonio, String descricao, String laboratorio, String situacao, int luminosidade) {
        super(patrimonio, descricao, laboratorio, situacao);
        this.luminosidade = luminosidade;
    }

    public int getLuminosidade() { return luminosidade; }
    public void setLuminosidade(int luminosidade) { this.luminosidade = luminosidade; }

    @Override
    public String getTipo() { return "PROJETOR"; }

    @Override
    public String getAtributoEspecifico() { return "Luminosidade: " + luminosidade + " lúmens"; }
}