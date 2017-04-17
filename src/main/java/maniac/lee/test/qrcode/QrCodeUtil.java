package maniac.lee.test.qrcode;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import org.apache.commons.lang3.Validate;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;

/**
 * Created by lipeng on 17/4/17.
 */
public class QrCodeUtil {

    public static void createQrcode(String _text, File file) {
        try {
            int qrcodeWidth = 300;
            int qrcodeHeight = 300;
            String qrcodeFormat = "png";
            HashMap<EncodeHintType, String> hints = new HashMap<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            BitMatrix bitMatrix = new MultiFormatWriter().encode(_text, BarcodeFormat.QR_CODE, qrcodeWidth, qrcodeHeight, hints);

            BufferedImage image = new BufferedImage(qrcodeWidth, qrcodeHeight, BufferedImage.TYPE_INT_RGB);
            ImageIO.write(image, qrcodeFormat, file);
            MatrixToImageWriter.writeToPath(bitMatrix, qrcodeFormat, file.toPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String decodeQr(String filePath) throws Exception {
        if ("".equalsIgnoreCase(filePath) && filePath.length() == 0) {
            throw new IllegalArgumentException("file not existed");
        }
        return decodeQr(new FileInputStream(filePath));
    }

    public static String decodeQr(File file) throws Exception {
        if (file == null || !file.exists() || !file.isFile()) {
            throw new IllegalArgumentException("file not existed");
        }
        return decodeQr(new FileInputStream(file));
    }

    public static String decodeQr(InputStream inputStream) throws Exception {
        Validate.notNull(inputStream, "inputStream is null");
        BufferedImage bufferedImage = ImageIO.read(inputStream);
        LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        Binarizer binarizer = new HybridBinarizer(source);
        BinaryBitmap bitmap = new BinaryBitmap(binarizer);
        HashMap<DecodeHintType, Object> hintTypeObjectHashMap = new HashMap<>();
        hintTypeObjectHashMap.put(DecodeHintType.CHARACTER_SET, "UTF-8");
        Result result = new MultiFormatReader().decode(bitmap, hintTypeObjectHashMap);
        return result.getText();
    }

    public static void main(String[] args) throws Exception {
        File file = new File(System.getProperty("user.home"), "a.png");
        createQrcode("http://www.lvbby.com", file);
        System.out.println(decodeQr(file));

    }


}
