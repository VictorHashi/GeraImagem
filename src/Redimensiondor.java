import java.io.*;
import java.util.Scanner;

public class Redimensiondor {

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

            File dir = new File("Images" + File.separator + filename);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            saveNewResize("10xMenor",filename, interpolar(image,image[0].length/10,image.length/10),maxValue);
            saveNewResize("480x320",filename, interpolar(image,480,320),maxValue);
            saveNewResize("1280x720",filename, interpolar(image,1280,720),maxValue);
            saveNewResize("1920x1080",filename, interpolar(image,1920,1080),maxValue);
            saveNewResize("3840x2160",filename, interpolar(image,3840,2160),maxValue);
            saveNewResize("7680x4320",filename, interpolar(image,7680,4320),maxValue);

        } catch (Exception e) {
            System.out.println("Arquivo não encontrado ou fora do formato pgm.");
            throw new RuntimeException(e);
        }

    }

    public static void saveNewResize(String newName, String oldImage,int[][] image, int maxValue){
        //Como o metodo de interpolação retorna apenas a matriz de valores da imagem,
        // é necessário refazer o cabeçalho

        try {
            PrintWriter writer = new PrintWriter(new FileWriter("Images" + File.separator +
                    oldImage + File.separator + newName + ".pgm"));

            writer.println("P2");
            writer.println(image[0].length + " " + image.length);
            writer.println(maxValue);

            for (int i = 0; i < image.length; i++) {
                for (int j = 0; j < image[0].length; j++) {
                    writer.print(image[i][j] + " ");
                }
                writer.println();
            }

            writer.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public static int[][] interpolar(int[][] image,
                                     int newWidth, int newHeight){

        int[][] newImage = new int[newHeight][newWidth];

        for (int i = 0; i < newHeight; i++) {

            for (int j = 0; j < newWidth; j++) {

                int x = (int) ((j/(double)newWidth) * image[0].length);
                int y = (int) ((i/(double)newHeight) * image.length);

                newImage[i][j] = image[y][x];

            }
            
        }

        return newImage;
    }

}
