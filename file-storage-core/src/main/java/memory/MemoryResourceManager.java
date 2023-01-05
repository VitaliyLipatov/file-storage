package memory;

import java.util.concurrent.atomic.AtomicInteger;

public class MemoryResourceManager {

    private final AtomicInteger usedMemory;

    public MemoryResourceManager() {
        this.usedMemory = new AtomicInteger();
    }

    public MemoryResource createInMemoryResource(byte[] data) {
        var dataSize = data.length;
        usedMemory.addAndGet(dataSize);
        return new MemoryResource(data, () -> usedMemory.addAndGet(-dataSize));
    }

    public long getUsedMemory() {
        return usedMemory.get();
    }
}
