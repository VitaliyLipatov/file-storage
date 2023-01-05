package manager;

import java.io.InputStream;

public interface ManagedResource extends AutoCloseable {

    InputStream getInputStream();

    long getSize();
}
