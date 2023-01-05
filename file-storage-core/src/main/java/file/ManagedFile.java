package file;

import manager.ManagedResource;

import java.io.InputStream;

public class ManagedFile implements ManagedResource {

    private final String name;
    private final ManagedResource resource;

    public ManagedFile(String name, ManagedResource resource) {
        this.name = name;
        this.resource = resource;
    }

    public ManagedFile withName(String newName) {
        return new ManagedFile(newName, resource);
    }

    @Override
    public InputStream getInputStream() {
        return resource.getInputStream();
    }

    @Override
    public long getSize() {
        return resource.getSize();
    }

    @Override
    public void close() throws Exception {
        resource.close();
    }
}
