package memory;

import manager.AbstractCleanableResource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class MemoryResource extends AbstractCleanableResource {

    private final byte[] body;

    public MemoryResource(byte[] body, Runnable onClose) {
        super(onClose);
        this.body = body;
    }

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(body);
    }

    @Override
    public long getSize() {
        return body.length;
    }
}
