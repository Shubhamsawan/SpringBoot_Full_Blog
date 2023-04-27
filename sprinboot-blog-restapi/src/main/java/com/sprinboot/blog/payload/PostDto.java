package com.sprinboot.blog.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
@Schema(
        description = "Post Dto model imformation"
)
public class PostDto {

    private long id;

    //title should not be null or empty
    // title should have at least 2 character
    @Schema(description = "Blog Post Title")
    @NotEmpty(message = "Title may not be empty")
    @Size(min = 2, message = "Post title should have at least 2 character")
    private String title;

    //post description should not be notnull or empty
    // post description should have at least 2 character
    @Schema(description = "Blog Post Description")
    @NotEmpty (message = "Description may not be empty")
    @Size(min = 10, message = "Post description should have at least 2 character")
    private String description;

    //post description should not be notnull or empty
    @Schema(description = "Blog Post Content")
    @NotEmpty(message = "Content may not be empty")
    private String content;

    private Set<CommentDto> comments;

    @Schema(description = "Blog Post Category")
    private Long categoryId;

}
