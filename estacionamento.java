
import java.util.Scanner;

public class estacionamento {

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);

        //detalhe do estacionamento(estacionar ou retirar o veiculo)------------------------------------------

        String veiculo;

        int repete = 0;
        int escolhaEntreSai = 0;
        int[] vaga = new int[31];
        double[] precoVaga = new double[31];

        for(int i = 0; i < 31; i++){
            vaga[i] = 0;
        }

        while(escolhaEntreSai == 0){
            System.out.println("Digite 1 se deseja estacionar um veiculo ou 2 caso deseje retira-lo: ");
            int escolhaEstacionar = scanner.nextInt();
            if (escolhaEstacionar == 1){
                repete = 0;
                while (repete == 0){

                    System.out.println("Digite o veiculo a estacionar: ");
                    scanner.nextLine(); // Adicionado para consumir o \n deixado pelo nextInt()
                    veiculo = scanner.nextLine();

                    double preco = 0;

                    if (veiculo.equalsIgnoreCase("carro")) {
                        System.out.println("Digite o tamanho da vaga: \n1 para pequeno; \n2 pra medio; \n3 pra grande\n");
                        int tamanhoVaga = scanner.nextInt();
                        int whilePreco = 0;
                        while(whilePreco < 1){
                            if (tamanhoVaga == 1) {
                                preco = 4.00;
                                whilePreco = 1;
                            } else if (tamanhoVaga == 2) {
                                preco = 6.00;
                                whilePreco = 1;
                            } else if(tamanhoVaga == 3) {
                                preco = 8.00;
                                whilePreco = 1;
                            }
                            else{
                                System.out.println("Numero invalido!");
                            }
                        }

                        for(int i = 1; i < 19; i++){
                            if(vaga[i] == 0){
                                vaga[i] += 1;
                                precoVaga[i] = preco;
                                System.out.println("O carro estacionou na vaga: " + i);
                                break;
                            }
                        }

                        repete = 1;

                    } else if (veiculo.equalsIgnoreCase("moto")) {
                        preco = 2.50;
                        for (int i = 19; i < 31; i++) { // Considere as vagas de 19 a 30 para motos
                            if (vaga[i] == 0) {
                                vaga[i] = 1;
                                precoVaga[i] = preco;
                                System.out.println("A moto estacionou na vaga: " + i);
                                break;
                            }
                        }
                        repete = 1;
                    } else{
                        System.out.println("Digitou errado!");
                    }
                }
            }
            else if(escolhaEstacionar == 2){
                System.out.println("Incompleto");
            } else if(escolhaEstacionar == 0){
                escolhaEntreSai = 1;
            }
        }
    }
}
