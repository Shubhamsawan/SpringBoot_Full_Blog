package com.sprinboot.blog.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {

    private long id;

    //name not be null or empty
    @NotEmpty(message = "Name not be empty")
    private String name;

    //name not be null or empty
    //em,ail validation
    @NotEmpty(message = "email not be empty")
    @Email
    private String email;

    //Comment not be null or empty
    //name body should ber minimumm 10 character
    @NotEmpty
    @Size(min = 10, message = "Comment body must be minimum  10 character")
    private String body;
}
