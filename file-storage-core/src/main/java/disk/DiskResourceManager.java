package disk;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class DiskResourceManager {

    private final File tempDir;
    private final AtomicLong usedDiskSpace;

    public DiskResourceManager(File tempDir) {
        this.tempDir = tempDir;
        this.usedDiskSpace = new AtomicLong();
    }

    public File createTempFile() {
        return new File(tempDir, UUID.randomUUID().toString() + ".tmp");
    }

    public DiskResource createDiskFile(File file) {
        var fileSize = file.length();
        var diskFile = new DiskResource(file, () -> usedDiskSpace.addAndGet(-fileSize));
        usedDiskSpace.addAndGet(fileSize);
        log.debug("File {} was successfully created on disk with size {}", file.getName(), fileSize);
        return diskFile;
    }

    public long getUsedDiskSpace() {
        return usedDiskSpace.get();
    }
}
