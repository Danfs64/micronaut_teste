package base.exceptions;

import java.util.UUID;

public class PostNotFoundException extends NotFoundException {
    public PostNotFoundException(UUID id) {
        super("Post[id = "+id+"] was not found");
    }
}
