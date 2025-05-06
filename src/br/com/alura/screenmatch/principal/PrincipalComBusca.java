package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.exceptions.ErroDeConversaoException;
import br.com.alura.screenmatch.modelos.Titulo;
import br.com.alura.screenmatch.modelos.TituloOMDb;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class PrincipalComBusca {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite o filme para ser pesquisado:");
        var busca = sc.nextLine();

        String endereco = "http://www.omdbapi.com/?t=" + busca.replace(" ", "+") + "&apikey=5282043b";

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endereco))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            String json = response.body();
            System.out.println(json);

            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
            //Titulo meuTitulo = gson.fromJson(json, Titulo.class);
            TituloOMDb meuTituloOmdb = gson.fromJson(json, TituloOMDb.class);
            System.out.println(meuTituloOmdb);

            Titulo meuTitulo = new Titulo(meuTituloOmdb);
            System.out.println("\nFilme já convertido");
            System.out.println(meuTitulo);
        } catch (NumberFormatException e) {
            System.out.println("HOUVE UM ERRO: -> " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Algum erro houve na busca, tente novamente");
        } catch (ErroDeConversaoException e) {
            System.out.println(e.getMensagem());
        }

        System.out.println("Programa finalizado com sucesso");
    }
}
