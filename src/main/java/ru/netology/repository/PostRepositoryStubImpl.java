package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepositoryStubImpl implements PostRepository {
  private final Map<Long, Post> posts = new ConcurrentHashMap<>();
  private final AtomicLong index = new AtomicLong(1);
  public List<Post> all() {
    return posts.isEmpty() ? Collections.emptyList() : new ArrayList<>(posts.values());
  }

  public Optional<Post> getById(long id) {
    Post post = posts.getOrDefault(id, null);
    System.out.println("get id" + id);
    return Optional.ofNullable(post);
  }

  public Post save(Post post) {
    if (post.getId() == 0) {
      for (Map.Entry<Long, Post> map: posts.entrySet()){
        if (map.getKey() == post.getId())
          post.setId(index.incrementAndGet());
      }
      posts.put(post.getId(), post);
      System.out.println("saved " + post);
    } else {
      posts.put(post.getId(), post);
      System.out.println("post " + post.getId() + " was change");
    }
    return post;
  }

  public void removeById(long id) {
    posts.remove(id);
    System.out.println("remove post id" + id);
  }
}