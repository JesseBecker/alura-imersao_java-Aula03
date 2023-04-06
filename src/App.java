import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class App {

    public static void main(String[] args) throws Exception {

        //fazer uma conexão HTTP e buscar os top filmes e/ou top  series

        //ExtratorDeConteudo extrator = new ExtratorDeConteudoDoIMDB();
        //String imdbKey = System.getenv("IMDB_API_KEY"); 
        //String url = imdbKey;                           

        ExtratorDeConteudo extrator = new ExtratorDeConteudoDaNasa();
        String nasaKey = System.getenv("NASA_API_KEY");
        String url = "https://api.nasa.gov/planetary/apod?api_key=" + nasaKey + "&start_date=2022-06-10&end_date=2022-06-14";                            
        
        var http = new ClienteHttp();
        String json = http.buscaDados(url);

        List<Conteudo> conteudos = extrator.extraiConteudos(json);

        var geradora = new GeradoraDeFigurinhas();

        var diretorio = new File("saida/"); 
        diretorio.mkdir();                           
        
        for (int i = 0; i < 3; i++) {

            Conteudo conteudo = conteudos.get(i);
            
            InputStream inputStream = new URL(conteudo.getUrlImage()).openStream();
            String nomeArquivo = "saida/" + conteudo.getTitulo().replace(":", " -") + ".png";

            geradora.cria(inputStream, nomeArquivo);

            System.out.println(conteudo.getTitulo());
            System.out.println();
        }
    }
}
