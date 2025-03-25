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

public class Estacionamento {
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        int[] vaga = new int[31];
        double[] precoVaga = new double[31];
        
        // HashMaps reintroduzidos
        HashMap<Integer, Veiculo> veiculosEstacionados = new HashMap<>();
        HashMap<Integer, LocalTime> tempoEntrada = new HashMap<>();
        
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
                    if (vaga[i] == 0) {
                        vaga[i] = 1;
                        precoVaga[i] = veiculo.getPrecoPorHora();
                        tempoEntrada.put(i, horarioEntrada);
                        veiculosEstacionados.put(i, veiculo);
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
                if (vaga[numVaga] == 1) {
                    System.out.println("Digite o horário de saída (HH:MM): ");
                    scanner.nextLine();
                    String horarioSaidaStr = scanner.nextLine();
                    LocalTime horarioSaida = LocalTime.parse(horarioSaidaStr);
                    
                    LocalTime entrada = tempoEntrada.get(numVaga);
                    Veiculo veiculo = veiculosEstacionados.get(numVaga);
                    long minutos = Duration.between(entrada, horarioSaida).toMinutes();
                    double totalPagar = veiculo.getPrecoPorHora() * (minutos / 60.0);
                    
                    System.out.println("O veículo " + veiculo.getTipo() + " com placa " + veiculo.getPlaca() + 
                                      " na vaga " + numVaga + " ficou estacionado por " + minutos + " minutos.");
                    System.out.printf("Total a pagar: R$ %.2f\n", totalPagar);
                    
                    vaga[numVaga] = 0;
                    precoVaga[numVaga] = 0;
                    tempoEntrada.remove(numVaga);
                    veiculosEstacionados.remove(numVaga);
                } else {
                    System.out.println("A vaga está vazia ou inválida!");
                }
            } 
            else if (escolha == 3) { // Mostrar veículos estacionados
                System.out.println("Veículos estacionados:");
                for (int i = 1; i <= 30; i++) {
                    if (vaga[i] == 1) {
                        Veiculo v = veiculosEstacionados.get(i);
                        System.out.println("Vaga " + i + ": " + v.getTipo() + 
                                         " (Placa: " + v.getPlaca() + 
                                         ", Desde: " + tempoEntrada.get(i) + ")");
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
