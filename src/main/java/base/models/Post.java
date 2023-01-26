package base.models;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "posts")
public class Post implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    UUID id;
    String title;
    String content;

    // @Builder.Default
    // Status status = Status.DRAFT;

    @Builder.Default
    LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "post")
    @Builder.Default
    @OrderColumn(name = "comment_idx")
    List<Comment> comments = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        Post post = (Post) o;
        return getTitle().equals(post.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle());
    }

    @Override
    public String toString() {
        return "Post {" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                // ", status=" + status +
                ", createdAt=" + createdAt +
                '}';
    }
}
