import java.io.*;
import java.util.Scanner;

public class MudaIntensidade {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        System.out.println("Digite o nome do arquivo: ");
        String filename = scan.nextLine();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(
                    "Images" + File.separator + filename + ".pgm"));

            String line;
            int height = 0, width = 0, maxValue = 0;

            int lineCount = 0;

            while ((line = reader.readLine()) != null){
                line = line.trim();
                if (!line.startsWith("#") && !line.isEmpty()){
                    lineCount++;
                    if (lineCount==2){
                        //Separando os valores de largura e altura por espaçamento(regex \\s+)
                        width = Integer.parseInt(line.split("\\s+")[0]);
                        height = Integer.parseInt(line.split("\\s+")[1]);
                        lineCount++;//Adicionando para não reler valores em caso de comentário na próxima linha
                    }else if (lineCount==4){
                        maxValue = Integer.parseInt(line.split("\\s+")[0]);
                        break;
                    }

                }
            }



            int[][] image = new int[height][width];
            int row = 0;
            int col = 0;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.startsWith("#") && !line.isEmpty()) {
                    String[] pixelValues = line.split("\\s+");
                    for (String pixelValue : pixelValues) {
                        image[row][col] = Integer.parseInt(pixelValue);
                        col++;
                        if (col == width) {
                            col = 0;
                            row++;
                        }
                    }
                }
            }

            reader.close();

            File dir = new File("Images" + File.separator + filename);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            PrintWriter writer = new PrintWriter(new FileWriter("Images" + File.separator +
                    filename + File.separator + filename + "5bits.pgm" ));

            writer.println("P2");
            writer.println(image[0].length + " " + image.length);
            writer.println(31);

            int[][] img5bit = new int[height][width];

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    img5bit[i][j] = (int) ((double)(image[i][j] * 31)/(double)255);
                    writer.print(img5bit[i][j] + " ");
                }
                writer.println();
            }

        } catch (Exception e) {
            System.out.println("Arquivo não encontrado ou fora do formato pgm.");
            throw new RuntimeException(e);
        }

    }

}
