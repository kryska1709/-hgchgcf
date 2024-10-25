import java.io.*;
import java.net.*;
import java.awt.Desktop;

public class DownloadAndOpen {

    public static void main(String[] args) {
        String songUrl = "https://storage4.lightaudio.ru/dm/39922595/21c1325d/Блич%20—%20Блич%2013%20опенинг%20%28фулл%29%20%28оригинал%29.mp3?play"; // Замените на URL Вашей песни
        String imageUrl = "https://avatars.mds.yandex.net/i?id=e2aec659db72f3fb298cc7f71677b48f5c9e0b6a-10555711-images-thumbs&n=13"; // Замените на URL Вашей картинки
        String songFileName = "downloaded_song.mp3";
        String imageFileName = "downloaded_image.jpg";

        Thread songThread = new Thread(() -> {
            try {
                downloadFile(songUrl, songFileName);
                openFile(songFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Thread imageThread = new Thread(() -> {
            try {
                downloadFile(imageUrl, imageFileName);
                openFile(imageFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        songThread.start();
        imageThread.start();
    }

    private static void downloadFile(String fileURL, String saveFile) throws IOException {
        URL url = new URL(fileURL);
        try (InputStream in = url.openStream();
             FileOutputStream out = new FileOutputStream(saveFile)) {
            byte[] buffer = new byte[2048];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }

    private static void openFile(String fileName) throws IOException {
        File file = new File(fileName);
        if (file.exists()) {
            Desktop.getDesktop().open(file);
        } else {
            System.out.println("Файл не найден: " + fileName);
        }
    }
}
