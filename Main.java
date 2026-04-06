import java.util.Random; 

public class Main {

    public static void main(String[] args) {
        Random sorteador = new Random();
        
        System.out.println("===== MONITORAMENTO DO AMBIENTE =====");

        // Repetiçao para simular 10 leituras conforme o tempo passando
        for (int i = 1; i <= 10; i++) {
            
            // Criando valores aleatórios (falsos sensores)
            double tempFake = 10 + (sorteador.nextDouble() * 30); 
            int luzFake = sorteador.nextInt(1024); // Luz entre 0 e 1023
            int umidFake = sorteador.nextInt(101); // Umidade entre 0 e 100

            //String no formato que seria recebido pelo Arduino
            String linhaDoArduino = String.format("%.1f", tempFake).replace(",", ".") + ";" + luzFake + ";" + umidFake;

            System.out.println("Leitura #" + i);
            analisarEntrada(linhaDoArduino);

            // Pausa de 5 segundos entre as leituras
            try {
                Thread.sleep(5000); 
            } catch (InterruptedException e) {
                System.out.println("Erro na pausa!");
            }
        }
    }

    public static void analisarEntrada(String textoRecebido) {
        String[] partes = textoRecebido.split(";");

        double temp = Double.parseDouble(partes[0]);
        int luz = Integer.parseInt(partes[1]);
        int umid = Integer.parseInt(partes[2]);

        DadosSensor dados = new DadosSensor(temp, luz, umid);

        System.out.println("Status: T:" + dados.temperatura + "ºC" + " L:" + dados.luz + " U:" + dados.umidade +"%");

        //  ANALISE DAS MEDIÇOES 
        if (dados.temperatura < 18) System.out.println("A temperatura esta baixa. Ligue o aquecedor ou pegue um agasalho.");
        if (dados.temperatura >= 30) System.out.println("Temperatura alta! Ligue o ar condicionado!");
        if (dados.luz < 300) System.out.println("Ambiente escuro. Acenda as luzes!");
        if (dados.umidade <= 30) System.out.println("O ar esta muito seco. Ligue o umidificador e mantenha-se hidratado!");
        if (dados.temperatura >= 18 && dados.temperatura < 30 && dados.luz >= 300 && dados.umidade > 30) {System.out.println("Condicoes do ambiente equilibradas. Aproveite o dia!");
}
        
        System.out.println("----------------------------------------------");
    }
}