package base.exceptions;

import java.util.UUID;

public class CommentNotFoundException extends NotFoundException {
    public CommentNotFoundException(UUID id) {
        super("Comment [id = "+id+"] was not found");
    }
}
