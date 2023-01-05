package manager;

import lombok.NonNull;

import java.lang.ref.Cleaner;

public abstract class AbstractCleanableResource implements ManagedResource {

    private static final Cleaner CLEANER = Cleaner.create();
    private final Cleaner.Cleanable cleanable;

    protected AbstractCleanableResource(@NonNull Runnable onClose) {
        this.cleanable = CLEANER.register(this, onClose);
    }

    @Override
    public void close() throws Exception {
        cleanable.clean();
    }
}
