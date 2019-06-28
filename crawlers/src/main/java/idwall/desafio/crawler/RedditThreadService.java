package idwall.desafio.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucas on 25/06/19.
 */
public class RedditThreadService {

    private static final int SCORE_MINIMO = 5000;

    String topTreadsToString(List<RedditThreadInfo> topThreads) {
        StringBuilder sb = new StringBuilder();

        for (RedditThreadInfo redditThreadInfo : topThreads) {
            sb.append("Pontuação: ").append(redditThreadInfo.getPontuacao()).append("\n");
            sb.append("Subreddit: ").append(redditThreadInfo.getSubreddit()).append("\n");
            sb.append("Título da thread: ").append(redditThreadInfo.getTituloThread()).append("\n");
            sb.append("Link para os comentários: ").append(redditThreadInfo.getLinkComentarios()).append("\n");
            sb.append("Link da thread: ").append(redditThreadInfo.getLinkThread());
            sb.append("\n\n");
        }

        return sb.toString();
    }

    public void listaTopThreads(String subreddits) throws IOException {
        String[] subredditsSplit = subreddits.split(";");
        for (String subreddit : subredditsSplit) {
            List<RedditThreadInfo> redditThreadInfoList = buscaTopThreads(subreddit);
            System.out.println("==========");
            System.out.println(subreddit);
            System.out.println("==========");
            for (RedditThreadInfo redditThreadInfo : redditThreadInfoList) {
                System.out.println("Pontuação: " + redditThreadInfo.getPontuacao());
                System.out.println("Subreddit: " + redditThreadInfo.getSubreddit());
                System.out.println("Título da thread: " + redditThreadInfo.getTituloThread());
                System.out.println("Link para os comentários: " + redditThreadInfo.getLinkComentarios());
                System.out.println("Link da thread: " + redditThreadInfo.getLinkThread());
                System.out.println();
            }
        }
    }

    List<RedditThreadInfo> buscaTopThreads(String subreddit) throws IOException {
        List<RedditThreadInfo> redditThreadInfoList = new ArrayList<>();

        String urlBase = "https://old.reddit.com";
        String caminhoR = "/r/";
        String caminhoTopThreads = "/top";

        String url = urlBase + caminhoR + subreddit + caminhoTopThreads;

        Document document = Jsoup.connect(url).get();
        Element body = document.body();
        Element siteTable = body.getElementById("siteTable");
        Elements threads = siteTable.children();
        for (Element thread : threads) {
            if (!thread.className().equals("clearleft")) {
                String dataScore = !thread.attr("data-score").equals("") ?
                        thread.attr("data-score") : "0";
                String dataUrl = thread.attr("data-url");
                String dataPermaLink = thread.attr("data-permalink");
                String dataPromoted = thread.attr("data-promoted");

                if (exibeThread(dataScore, dataPromoted)) {
                    Elements entryUnvotedElements = thread.getElementsByClass("entry unvoted");
                    for (Element entryUnvotedElement : entryUnvotedElements) {
                        Elements topMatterElements = entryUnvotedElement.getElementsByClass("top-matter");
                        for (Element topMatterElement : topMatterElements) {
                            Elements titleElements = topMatterElement.getElementsByClass("title");
                            for (Element titleElement : titleElements) {
                                if (isElementQueContemTitulo(titleElement)) {
                                    RedditThreadInfo redditThreadInfo = criaRedditThreadInfo(subreddit, dataScore,
                                            urlBase, dataUrl, dataPermaLink, titleElement);
                                    redditThreadInfoList.add(redditThreadInfo);
                                }
                            }
                        }
                    }
                }
            }
        }

        return redditThreadInfoList;
    }

    private boolean exibeThread(String dataScore, String dataPromoted) {
        return Integer.parseInt(dataScore) > SCORE_MINIMO && !dataPromoted.equals("true");
    }

    private boolean isElementQueContemTitulo(Element titleElement) {
        return titleElement.className().equals("title may-blank")
                || titleElement.className().equals("title may-blank outbound");
    }

    private RedditThreadInfo criaRedditThreadInfo(String subreddit, String dataScore, String urlBase,
                                                  String dataUrl, String dataPermaLink, Element titleElement) {

        RedditThreadInfo redditThreadInfo = new RedditThreadInfo();

        redditThreadInfo.setPontuacao(Integer.parseInt(dataScore));
        redditThreadInfo.setSubreddit(subreddit);
        redditThreadInfo.setTituloThread(titleElement.text());
        redditThreadInfo.setLinkComentarios(urlBase + dataPermaLink);

        if (subreddit.equals("askreddit")) {
            redditThreadInfo.setLinkThread(urlBase + dataUrl);
        } else {
            redditThreadInfo.setLinkThread(dataUrl);
        }

        return redditThreadInfo;
    }

}
