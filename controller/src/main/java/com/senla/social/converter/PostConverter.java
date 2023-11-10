package com.senla.social.converter;

import com.senla.social.dto.post.PostDto;
import com.senla.social.entity.Post;
import org.springframework.stereotype.Component;

/**
 * @author Victor Lukyanchik
 * @version 1.0
 * @since 01.06.2022
 */
@Component
public class PostConverter {

    public PostDto convert(Post post) {
        PostDto postDto = new PostDto();
        postDto.setTitle(post.getTitle());
        postDto.setText(post.getText());
        return postDto;
    }
}
