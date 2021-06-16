package com.example.demo.gql;

import com.example.demo.gql.types.Author;
import com.example.demo.service.AuthorService;
import com.netflix.graphql.dgs.DgsDataLoader;
import lombok.RequiredArgsConstructor;
import org.dataloader.BatchLoader;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
@DgsDataLoader(name = "authorsLoader")
public class AuthorsBatchLoader implements BatchLoader<String, Author> {
    final AuthorService authorService;

    @Override
    public CompletionStage<List<Author>> load(List<String> keys) {
        return CompletableFuture.supplyAsync(() ->
                this.authorService.getAuthorByIdIn(keys)
        );
    }
}