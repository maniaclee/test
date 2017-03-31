package maniac.lee.tools;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Created by lipeng on 17/3/31.
 */
public class MavenCleaner {


    public void run(File root, Consumer<File> fileConsumer) {
        getTarget(root).ifPresent(fileConsumer);
        Optional.ofNullable(root)
                .filter(f -> f.isDirectory() && f.exists())
                .map(f -> f.listFiles((dir, name) -> dir.isDirectory()))
                .ifPresent(fs -> {
                    for (File f : fs)
                        run(f, fileConsumer);
                });
    }

    public void removeDir(File file) {
        try {
            FileUtils.deleteDirectory(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Optional<File> getTarget(File file) {
        return getSubFile(file, "pom.xml").flatMap(f -> getSubDir(file, "target"));
    }

    private Optional<File> getSubDir(File file, String dest) {
        return Optional.ofNullable(file)
                .filter(File::isDirectory)
                .map(re -> new File(re, dest))
                .filter(re -> re.isDirectory() && re.exists());
    }

    private Optional<File> getSubFile(File file, String dest) {
        return Optional.ofNullable(file)
                .filter(File::isDirectory)
                .map(re -> new File(re, dest))
                .filter(re -> re.isFile() && re.exists());
    }

    @Test
    public void dsf() {
        run(new File("/Users/psyco/workspace/"), file -> {
            System.out.println(file.getAbsolutePath());
            removeDir(file);
        });
    }
}
