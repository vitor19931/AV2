package model;

public class Computador extends Equipamento {
    private int memoriaRAM; // em GB

    public Computador(int patrimonio, String descricao, String laboratorio, String situacao, int memoriaRAM) {
        super(patrimonio, descricao, laboratorio, situacao);
        this.memoriaRAM = memoriaRAM;
    }

    public int getMemoriaRAM() { return memoriaRAM; }
    public void setMemoriaRAM(int memoriaRAM) { this.memoriaRAM = memoriaRAM; }

    @Override
    public String getTipo() { return "COMPUTADOR"; }

    @Override
    public String getAtributoEspecifico() { return "Memória RAM: " + memoriaRAM + " GB"; }
}