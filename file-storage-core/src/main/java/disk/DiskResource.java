package disk;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import manager.AbstractCleanableResource;
import manager.ResourceException;
import org.apache.commons.io.FileUtils;

import java.io.*;

@Slf4j
public class DiskResource extends AbstractCleanableResource {

    private final File file;
    private final long size;

    public DiskResource(@NonNull File file, Runnable onClose) {
        super(() -> cleanResources(file, onClose));
        this.file = file;
        this.size = file.length();
        file.deleteOnExit();
    }

    @Override
    public InputStream getInputStream() {
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            throw new ResourceException(String.format("File %s not found", file.getName()), ex);
        }
    }

    @Override
    public long getSize() {
        return size;
    }

    private static void cleanResources(File file, Runnable onClose) {
        try {
            FileUtils.forceDelete(file);
            onClose.run();
            log.debug("Temp file {} was successfully deleted", file.getName());
        } catch (IOException ex) {
            log.error("Can't delete temp file {}", file.getName());
        }
    }
}
