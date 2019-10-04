package graphqlscope.graphql;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.Internal;
import graphql.spring.web.servlet.GraphQLInvocation;
import graphql.spring.web.servlet.GraphQLInvocationData;
import org.dataloader.DataLoaderRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.concurrent.CompletableFuture;

@Component
@Internal
@Primary
@Profile("global")
public class GlobalScopedGraphQLInvocation implements GraphQLInvocation {

    private final GraphQL graphQL;

    @Autowired(required = false)
    DataLoaderRegistry dataLoaderRegistry;

    public GlobalScopedGraphQLInvocation(GraphQL graphQL) {
        this.graphQL = graphQL;
    }

    @Override
    public CompletableFuture<ExecutionResult> invoke(GraphQLInvocationData invocationData, WebRequest webRequest) {
        ExecutionInput.Builder executionInputBuilder = ExecutionInput.newExecutionInput()
          .query(invocationData.getQuery())
          .operationName(invocationData.getOperationName())
          .variables(invocationData.getVariables());

        if (dataLoaderRegistry != null) {
            executionInputBuilder.dataLoaderRegistry(dataLoaderRegistry);
            executionInputBuilder.context(dataLoaderRegistry);
        }

        ExecutionInput executionInput = executionInputBuilder.build();
        return graphQL.executeAsync(executionInput);
    }

}