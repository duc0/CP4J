package codegen.generic;

import com.vb.io.FastWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class PrimitiveSpecializationGenerator {
    private void getAllGenericClassFiles(File folder, List<File> classFiles) {
        File[] fileList = folder.listFiles();
        if (fileList != null) {
            for (File file : fileList) {
                if (file.isFile()) {
                    String name = file.getName();
                    if (name.endsWith(".java") && name.startsWith("Generic")) {
                        classFiles.add(file);
                    }
                } else {
                    getAllGenericClassFiles(file, classFiles);
                }
            }
        }
    }

    private List<File> getAllGenericClassFiles() {
        List<File> files = new ArrayList<>();
        getAllGenericClassFiles(new File("src/main/java"), files);
        return files;
    }

    public void generate() throws IOException {
        List<File> classes = getAllGenericClassFiles();
        System.out.println(classes);
        for (File clz : classes) {
            String originalCode = Files.readString(clz.toPath());
            for (String type : new String[] {"Int", "Long", "Double"}) {
                String code = originalCode;
                code.replace("NumberGeneric", type.toLowerCase());
                code.replace("Generic", type);
                File newFile = new File(clz.getAbsolutePath().replace("Generic", type));
                FastWriter fastWriter = new FastWriter(new FileOutputStream(newFile));
                fastWriter.write(code);
            }

        }
    }

    public static void main(String[] args) throws IOException {
        PrimitiveSpecializationGenerator generator = new PrimitiveSpecializationGenerator();
        generator.generate();
    }
}
