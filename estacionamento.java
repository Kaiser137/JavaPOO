package Univas.TerceiroSemestre.Trabaio;

import java.util.Scanner;

public class estacionamento {

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);


        //detalhe do estacionamento
        String veiculo;

        System.out.println("Digite o veiculo estacionado: ");
        veiculo = scanner.nextLine();
        double preco = 0;

        if (veiculo.equalsIgnoreCase("carro")) {
            System.out.println("Digite o tamanho da vaga: \n1 para pequeno; \n2 pra medio; \n3 pra grande\n");
            int vaga = scanner.nextInt();
            if (vaga == 1) {
                preco = 4.00;
            } else if (vaga == 2) {
                preco = 6.00;
            } else if(vaga == 3) {
                preco = 8.00;
            }
            else{
                System.out.println("Lá ele");
            }
        } else if (veiculo.equalsIgnoreCase("moto")) {
            preco = 2.00;
        } else{
            System.out.println("Digitou errado!");
        }
            System.out.println(preco);
    }
}
