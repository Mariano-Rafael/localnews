package com.localnews.localnews.controllers.userControllers;

import com.localnews.localnews.models.BooleanResponseModel;
import com.localnews.localnews.models.commentsAndLikesExceptions.CommentOrLikeNotFound;
import com.localnews.localnews.models.commentsAndLikesExceptions.NewsOrUserNotFoundException;
import com.localnews.localnews.services.newsServices.NewsService;
import com.localnews.localnews.services.userServices.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/likes")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @Autowired
    private NewsService newsService;

    @PostMapping("/register/{newsId}")
    public ResponseEntity<?> createLike(@PathVariable Long newsId) {
        try {
            likeService.addLike(newsId);
            return ResponseEntity.status(HttpStatus.CREATED).body(new BooleanResponseModel(true,
                    "Like registrado."));
        }
        catch (NewsOrUserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BooleanResponseModel(false,
                    e.getMessage()));
        }
        catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BooleanResponseModel(false,
                    "Erro interno."));
        }
    }

    @DeleteMapping("/unlike/{newsId}")
    public ResponseEntity<?> unlike(@PathVariable Long newsId) {
        try {
            likeService.removeLike(newsId);
            return ResponseEntity.ok(new BooleanResponseModel(true,
                    "Like removido."));
        }
        catch (NewsOrUserNotFoundException | CommentOrLikeNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BooleanResponseModel(false,
                    e.getMessage()));
        }
        catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BooleanResponseModel(false,
                    "Erro interno."));
        }
    }

    @GetMapping("/info/{newsId}")
    public ResponseEntity<?> countLikesByNewsId(@PathVariable Long newsId) {
        try {
            int likeCount = likeService.countLikesByNewsId(newsId);
            return ResponseEntity.ok(new BooleanResponseModel(true,
                    "Quantidade de likes: " + likeCount));
        }
        catch (NewsOrUserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BooleanResponseModel(false,
                    e.getMessage()));
        }
        catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BooleanResponseModel(false,
                    "Erro interno."));
        }
    }
}
