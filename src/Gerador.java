import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Gerador {

    public static void main(String[] args) {

        //Ainda incompleto para imagens formato binário
        Scanner scan = new Scanner(System.in);

        System.out.print("File name: ");
        String filename = scan.nextLine();
        System.out.println();

        System.out.print("Image Type: P");
        int type = scan.nextInt();

        int variant = 1;
        if (type != 1 && type != 4){
            System.out.print("\nColor Variants: ");
            variant = scan.nextInt();
        }

        System.out.print("\nImage Size Height:");
        int h = scan.nextInt();
        System.out.print("\nImage Size Width:");
        int w = scan.nextInt();

        String ext =(type==1||type==4?".pbm":(type==2||type==5?".pgm":".ppm"));


        File file = new File("Images" + File.separator + filename + ext);

        try {

            if (file.createNewFile()) {
                System.out.println("File created: " + filename);
            } else {
                System.out.println("File already exists.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileWriter writer = new FileWriter("Images" + File.separator + filename + ext);
            writer.write("P"+type+"\n");
            writer.write(h+" "+w+"\n");
            if (type!=1&&type!=4)
                writer.write(variant+"\n");

            Random rand = new Random(variant);

            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {
                    if (type != 3 && type != 6){
                        writer.write(rand.nextInt(variant+1)+" ");
                    }else {
                        writer.write(rand.nextInt(variant+1)+" "+rand.nextInt(variant+1)+" "+rand.nextInt(variant+1)+" ");
                    }
                }
                writer.write("\n");
            }

            writer.close();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }




    }

}
