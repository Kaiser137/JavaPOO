//Aqui aplicamos alguns imports necessários para a lógica do programa
import java.util.Scanner;
import java.util.HashMap;
import java.time.LocalTime;
import java.time.Duration;
//---------------------------------------------------------------------------------------------------------------------------------------
public class estacionamento {
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        int[] vaga = new int[31];
        double[] precoVaga = new double[31];
//Aqui usamos o "HashMap" como ponto identificador para a placa, o tempo de entrada e a localização do veiculo estacionado---------------
        HashMap<Integer, String> placas = new HashMap<>();
        HashMap<Integer, LocalTime> tempoEntrada = new HashMap<>();
        HashMap<Integer, String> veiculosEstacionados = new HashMap<>();
//Lógica para o estacionamento do veiculo------------------------------------------------------------------------------------------------        
        while (true) {
            System.out.println("Digite 1 para estacionar, 2 para retirar um veículo, 3 para ver veículos estacionados ou 0 para sair: ");
            int escolha = scanner.nextInt();
            
            if (escolha == 1) { // Estacionar veículo
                scanner.nextLine(); 
                System.out.println("Digite a placa do veículo: ");
                String placa = scanner.nextLine();
                
                System.out.println("Digite o tipo de veículo (carro/moto): ");
                String veiculo = scanner.nextLine();
                
                System.out.println("Digite o horário de entrada (HH:MM): ");
                String horarioEntradaStr = scanner.nextLine();
                LocalTime horarioEntrada = LocalTime.parse(horarioEntradaStr);
                
                double preco = 0;
                int vagaInicio = (veiculo.equalsIgnoreCase("moto")) ? 19 : 1;
                int vagaFim = (veiculo.equalsIgnoreCase("moto")) ? 30 : 18;
                
                if (veiculo.equalsIgnoreCase("carro")) {
                    System.out.println("Escolha o tamanho da vaga: 1 (pequeno), 2 (médio), 3 (grande)");
                    int tamanhoVaga = scanner.nextInt();
                    preco = (tamanhoVaga == 1) ? 4.00 : (tamanhoVaga == 2) ? 6.00 : 8.00;
                } else if (veiculo.equalsIgnoreCase("moto")) {
                    preco = 2.50;
                } else {
                    System.out.println("Tipo de veículo inválido!");
                    continue;
                }
                
                boolean estacionado = false;
                for (int i = vagaInicio; i <= vagaFim; i++) {
                    if (vaga[i] == 0) {
                        vaga[i] = 1;
                        precoVaga[i] = preco;
                        tempoEntrada.put(i, horarioEntrada);
                        veiculosEstacionados.put(i, veiculo);
                        placas.put(i, placa);
                        System.out.println("O " + veiculo + " com placa " + placa + " estacionou na vaga: " + i + " às " + horarioEntrada);
                        estacionado = true;
                        break;
                    }
                }
                if (!estacionado) {
                    System.out.println("Não há vagas disponíveis para " + veiculo + ".");
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
                    long minutos = Duration.between(entrada, horarioSaida).toMinutes();
                    double totalPagar = precoVaga[numVaga] * (minutos / 60.0);
                    System.out.println("O veículo com placa " + placas.get(numVaga) + " na vaga " + numVaga + " ficou estacionado por " + minutos + " minutos.");
                    System.out.printf("Total a pagar: R$ %.2f\n", totalPagar);
                    
                    vaga[numVaga] = 0;
                    precoVaga[numVaga] = 0;
                    tempoEntrada.remove(numVaga);
                    veiculosEstacionados.remove(numVaga);
                    placas.remove(numVaga);
                } else {
                    System.out.println("A vaga está vazia ou inválida!");
                }
            } 
            else if (escolha == 3) { // Mostrar veículos estacionados
                System.out.println("Veículos estacionados:");
                for (int i = 1; i <= 30; i++) {
                    if (vaga[i] == 1) {
                        System.out.println("Vaga " + i + ": " + veiculosEstacionados.get(i) + " (Placa: " + placas.get(i) + ", Desde: " + tempoEntrada.get(i) + ")");
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
