import java.util.Scanner;
import java.util.HashMap;
import java.time.LocalTime;
import java.time.Duration;

// Classe base para Veículo
abstract class Veiculo {
    private String placa;
    private String tipo;
    
    public Veiculo(String placa, String tipo) {
        this.placa = placa;
        this.tipo = tipo;
    }
    
    public String getPlaca() {
        return placa;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public abstract double getPrecoPorHora();
}

// Subclasses de Veículo
class Carro extends Veiculo {
    private int tamanhoVaga;
    
    public Carro(String placa, int tamanhoVaga) {
        super(placa, "carro");
        this.tamanhoVaga = tamanhoVaga;
    }
    
    @Override
    public double getPrecoPorHora() {
        switch(tamanhoVaga) {
            case 1: return 4.00;
            case 2: return 6.00;
            case 3: return 8.00;
            default: return 6.00;
        }
    }
    
    public int getTamanhoVaga() {
        return tamanhoVaga;
    }
}

class Moto extends Veiculo {
    public Moto(String placa) {
        super(placa, "moto");
    }
    
    @Override
    public double getPrecoPorHora() {
        return 2.50;
    }
}

// Classe para representar uma Vaga
class Vaga {
    private int numero;
    private boolean ocupada;
    private Veiculo veiculo;
    private LocalTime entrada;
    
    public Vaga(int numero) {
        this.numero = numero;
        this.ocupada = false;
    }
    
    public boolean estacionar(Veiculo veiculo, LocalTime entrada) {
        if (!ocupada) {
            this.veiculo = veiculo;
            this.entrada = entrada;
            this.ocupada = true;
            return true;
        }
        return false;
    }
    
    public double liberar(LocalTime saida) {
        if (ocupada) {
            long minutos = Duration.between(entrada, saida).toMinutes();
            double total = veiculo.getPrecoPorHora() * (minutos / 60.0);
            ocupada = false;
            return total;
        }
        return 0;
    }
    
    // Getters
    public int getNumero() { return numero; }
    public boolean isOcupada() { return ocupada; }
    public Veiculo getVeiculo() { return veiculo; }
    public LocalTime getEntrada() { return entrada; }
}

public class Estacionamento {
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        Vaga[] vagas = new Vaga[31]; // Índice 0 não usado
        
        // Inicializar vagas
        for (int i = 1; i <= 30; i++) {
            vagas[i] = new Vaga(i);
        }
        
        while (true) {
            System.out.println("Digite 1 para estacionar, 2 para retirar um veículo, 3 para ver veículos estacionados ou 0 para sair: ");
            int escolha = scanner.nextInt();
            
            if (escolha == 1) { // Estacionar veículo
                scanner.nextLine(); 
                System.out.println("Digite a placa do veículo: ");
                String placa = scanner.nextLine();
                
                System.out.println("Digite o tipo de veículo (carro/moto): ");
                String tipoVeiculo = scanner.nextLine();
                
                System.out.println("Digite o horário de entrada (HH:MM): ");
                String horarioEntradaStr = scanner.nextLine();
                LocalTime horarioEntrada = LocalTime.parse(horarioEntradaStr);
                
                Veiculo veiculo;
                if (tipoVeiculo.equalsIgnoreCase("carro")) {
                    System.out.println("Escolha o tamanho da vaga: 1 (pequeno), 2 (médio), 3 (grande)");
                    int tamanhoVaga = scanner.nextInt();
                    veiculo = new Carro(placa, tamanhoVaga);
                } else if (tipoVeiculo.equalsIgnoreCase("moto")) {
                    veiculo = new Moto(placa);
                } else {
                    System.out.println("Tipo de veículo inválido!");
                    continue;
                }
                
                boolean estacionado = false;
                int vagaInicio = (tipoVeiculo.equalsIgnoreCase("moto")) ? 19 : 1;
                int vagaFim = (tipoVeiculo.equalsIgnoreCase("moto")) ? 30 : 18;
                
                for (int i = vagaInicio; i <= vagaFim; i++) {
                    if (vagas[i].estacionar(veiculo, horarioEntrada)) {
                        System.out.println("O " + veiculo.getTipo() + " com placa " + veiculo.getPlaca() + 
                                         " estacionou na vaga: " + i + " às " + horarioEntrada);
                        estacionado = true;
                        break;
                    }
                }
                if (!estacionado) {
                    System.out.println("Não há vagas disponíveis para " + tipoVeiculo + ".");
                }
            } 
            else if (escolha == 2) { // Retirar veículo
                System.out.println("Digite o número da vaga a liberar: ");
                int numVaga = scanner.nextInt();
                
                if (numVaga >= 1 && numVaga <= 30 && vagas[numVaga].isOcupada()) {
                    System.out.println("Digite o horário de saída (HH:MM): ");
                    scanner.nextLine();
                    String horarioSaidaStr = scanner.nextLine();
                    LocalTime horarioSaida = LocalTime.parse(horarioSaidaStr);
                    
                    double totalPagar = vagas[numVaga].liberar(horarioSaida);
                    Veiculo veiculo = vagas[numVaga].getVeiculo();
                    long minutos = Duration.between(vagas[numVaga].getEntrada(), horarioSaida).toMinutes();
                    
                    System.out.println("O veículo com placa " + veiculo.getPlaca() + " na vaga " + numVaga + 
                                     " ficou estacionado por " + minutos + " minutos.");
                    System.out.printf("Total a pagar: R$ %.2f\n", totalPagar);
                } else {
                    System.out.println("A vaga está vazia ou inválida!");
                }
            } 
            else if (escolha == 3) { // Mostrar veículos estacionados
                System.out.println("Veículos estacionados:");
                for (int i = 1; i <= 30; i++) {
                    if (vagas[i].isOcupada()) {
                        Veiculo v = vagas[i].getVeiculo();
                        System.out.println("Vaga " + i + ": " + v.getTipo() + 
                                         " (Placa: " + v.getPlaca() + 
                                         ", Desde: " + vagas[i].getEntrada() + ")");
                    }
                }
            } 
            else if (escolha == 0) { // Sair
                System.out.println("Encerrando sistema...");
                break;
            } else {
                System.out.println("Opção inválida!");
            }
        }
        scanner.close();
    }
}
