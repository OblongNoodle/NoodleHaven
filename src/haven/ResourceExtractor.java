package haven;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class ResourceExtractor {
    public static void main(String[] args) {
        // List of button resources to extract
        String[] buttons = {"rbtn-inv", "rbtn-equ", "rbtn-chr", "rbtn-bud", "rbtn-opt"};
        String[] states = {"", "-d", "-h", "-dh"};

        String outputDir = "extracted_images/";
        new File(outputDir).mkdirs();

        for(String button : buttons) {
            for(String state : states) {
                String resName = "gfx/hud/" + button + state;
                try {
                    System.out.println("Extracting: " + resName);
                    Resource res = Resource.local().loadwait(resName);
                    Resource.Image img = res.layer(Resource.imgc);
                    BufferedImage bi = img.img;

                    File outputFile = new File(outputDir + button + state + ".png");
                    ImageIO.write(bi, "PNG", outputFile);
                    System.out.println("  Saved to: " + outputFile.getPath());
                    System.out.println("  Size: " + bi.getWidth() + "x" + bi.getHeight());
                } catch(Exception e) {
                    System.err.println("  Failed: " + e.getMessage());
                }
            }
        }

        System.out.println("\nDone! Check the extracted_images folder.");
    }
}