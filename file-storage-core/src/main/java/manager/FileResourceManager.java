package manager;

import disk.DiskResourceManager;
import file.ManagedFile;
import memory.MemoryResourceManager;

import java.io.InputStream;

public class FileResourceManager extends ResourceManager {

    public FileResourceManager(DiskResourceManager diskResourceManager,
                               MemoryResourceManager memoryResourceManager,
                               long maxInMemoryResourceSize,
                               long maxInMemoryTotal) {
        super(diskResourceManager, memoryResourceManager, maxInMemoryResourceSize, maxInMemoryTotal);
    }

    public ManagedFile newFile(String name, InputStream inputStream) {
        return new ManagedFile(name, newResource(inputStream));
    }

    public ManagedFile newFile(String name, byte[] data) {
        return new ManagedFile(name, newResourceInMemory(data));
    }
}
