package manager;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.output.DeferredFileOutputStream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@Slf4j
public class ManagedOutputStream extends DeferredFileOutputStream {

    public ManagedOutputStream(int threshold, File outputFile) {
        super(threshold, outputFile);
    }

    public void releaseResource() {
        var file = getFile();
        if (file != null) {
            try {
                FileUtils.forceDelete(file);
            } catch (FileNotFoundException ex) {
                log.warn(String.format("File %s not found", file.getName()), ex);
            } catch (IOException ex) {
                throw new ResourceException(ex);
            }
        }
    }
}
