package manager;

import disk.DiskResourceManager;
import lombok.extern.slf4j.Slf4j;
import memory.MemoryResourceManager;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class ResourceManager {

    private final DiskResourceManager diskResourceManager;
    private final MemoryResourceManager memoryResourceManager;
    private final long maxInMemoryResourceSize;
    private final long maxInMemoryTotal;

    public ResourceManager(DiskResourceManager diskResourceManager,
                           MemoryResourceManager memoryResourceManager,
                           long maxInMemoryResourceSize,
                           long maxInMemoryTotal) {
        this.diskResourceManager = diskResourceManager;
        this.memoryResourceManager = memoryResourceManager;
        this.maxInMemoryResourceSize = maxInMemoryResourceSize;
        this.maxInMemoryTotal = maxInMemoryTotal;
    }

    public ManagedResource newResource(InputStream inputStream) {
        try (var managedOutputStream = newOutputStream()) {
            try {
                IOUtils.copy(inputStream, managedOutputStream);
                return newResource(managedOutputStream);
            } catch (Exception ex) {
                managedOutputStream.close();
                managedOutputStream.releaseResource();
                throw new ResourceException(ex);
            }
        } catch (IOException ex) {
            throw new ResourceException(ex);
        }
    }

    public ManagedResource newResourceInMemory(byte[] data) {
        return memoryResourceManager.createInMemoryResource(data);
    }

    private ManagedResource newResource(ManagedOutputStream managedOutputStream) {
        try {
            managedOutputStream.close();
            if (managedOutputStream.isInMemory()) {
                return memoryResourceManager.createInMemoryResource(managedOutputStream.getData());
            }
            return diskResourceManager.createDiskFile(managedOutputStream.getFile());
        } catch (IOException ex) {
            managedOutputStream.releaseResource();
            throw new ResourceException(ex);
        }
    }

    private ManagedOutputStream newOutputStream() {
        var tempFile = diskResourceManager.createTempFile();
        return new ManagedOutputStream((int) getCurrentInMemorySizeLimit(), tempFile);
    }

    private long getCurrentInMemorySizeLimit() {
        var used = memoryResourceManager.getUsedMemory();
        return used < maxInMemoryTotal ? Math.min(maxInMemoryResourceSize, maxInMemoryTotal - used) : 0;
    }
}
