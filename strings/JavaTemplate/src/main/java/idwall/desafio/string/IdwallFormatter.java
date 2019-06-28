package idwall.desafio.string;

/**
 * Created by Rodrigo Catão Araujo on 06/02/2018.
 */
public class IdwallFormatter extends StringFormatter {

    public IdwallFormatter(Integer limit) {
        super(limit);
    }

    /**
     * Should format as described in the challenge
     *
     * @param text
     * @param justify
     * @return
     */
    @Override
    public String format(String text, boolean justify) {
        StringBuilder textoFormatado = new StringBuilder();

        String[] textosPorQuebraLinha = text.split("\n");
        for (String texto : textosPorQuebraLinha) {
            if (!texto.isEmpty()) {
                int qtdCaracteres = 0;
                String[] palavras = texto.split(" ");
                for (String palavra : palavras) {
                    if (qtdCaracteres + palavra.length() < getLimit()) {
                        anexaPalavraEspaco(textoFormatado, palavra);
                        qtdCaracteres = qtdCaracteres + palavra.length() + 1;
                    } else if (qtdCaracteres + palavra.length() == getLimit()) {
                        textoFormatado.append(palavra);
                        qtdCaracteres = qtdCaracteres + palavra.length();
                    } else {
                        if (justify) {
                            String[] textoFormatadoPorQuebraLinha = textoFormatado.toString().split("\n");
                            String linha = textoFormatadoPorQuebraLinha[textoFormatadoPorQuebraLinha.length - 1];
                            int qtdCaracteresLinha = linha.trim().length();
                            if (qtdCaracteresLinha < getLimit()) {
                                String[] palavrasLinha = linha.split(" ");
                                int qtdEspacosParaAdicionar = getLimit() - qtdCaracteresLinha;

                                adicionaEspacosAsPalvrasLinha(palavrasLinha, qtdEspacosParaAdicionar);

                                // apaga a linha para inserir a mesma justificada
                                apagaLinha(textoFormatado);

                                StringBuilder linhaStringBuilder = new StringBuilder(String.join(" ",
                                        palavrasLinha));
                                anexaLinhaStringBuilder(textoFormatado, textoFormatadoPorQuebraLinha,
                                        linhaStringBuilder);
                                pulaLinhaAnexaPalavraEspaco(textoFormatado, palavra);
                                qtdCaracteres = atualizaQtdCaracteresInicioLinha(palavra);
                            } else {
                                pulaLinhaAnexaPalavraEspaco(textoFormatado, palavra);
                                qtdCaracteres = atualizaQtdCaracteresInicioLinha(palavra);
                            }
                        } else {
                            pulaLinhaAnexaPalavraEspaco(textoFormatado, palavra);
                            qtdCaracteres = atualizaQtdCaracteresInicioLinha(palavra);
                        }
                    }
                }
            } else {
                textoFormatado.append("\n\n");
            }
        }

        return textoFormatado.toString();
    }

    private void anexaPalavraEspaco(StringBuilder textoFormatado, String palavra) {
        textoFormatado.append(palavra).append(" ");
    }

    private void adicionaEspacosAsPalvrasLinha(String[] palavrasLinha, int qtdEspacosParaAdicionar) {
        if (palavrasLinha.length == 1) {
            while (qtdEspacosParaAdicionar > 0) {
                palavrasLinha[0] = palavrasLinha[0] + " ";
                qtdEspacosParaAdicionar--;
            }
        } else {
            while (qtdEspacosParaAdicionar > 0) {
                for (int k = 0; k < palavrasLinha.length - 1; k++) {
                    palavrasLinha[k] = palavrasLinha[k] + " ";
                    qtdEspacosParaAdicionar--;
                    if (qtdEspacosParaAdicionar == 0) {
                        break;
                    }
                }
            }
        }
    }

    private void apagaLinha(StringBuilder textoFormatado) {
        int indicePrimeiraPalavraLinha = textoFormatado.lastIndexOf("\n");
        if (indicePrimeiraPalavraLinha > 0) {
            textoFormatado.delete(indicePrimeiraPalavraLinha, textoFormatado.length());
        } else {
            // primeira linha
            textoFormatado.delete(0, textoFormatado.length());
        }
    }

    private void anexaLinhaStringBuilder(StringBuilder textoFormatado, String[] textoFormatadoPorQuebraLinha,
                                         StringBuilder linhaStringBuilder) {

        if (textoFormatadoPorQuebraLinha.length > 1) {
            textoFormatado.append("\n").append(linhaStringBuilder);
        } else {
            textoFormatado.append(linhaStringBuilder);
        }
    }

    private void pulaLinhaAnexaPalavraEspaco(StringBuilder textoFormatado, String palavra) {
        textoFormatado.append("\n");
        anexaPalavraEspaco(textoFormatado, palavra);
    }

    private int atualizaQtdCaracteresInicioLinha(String palavra) {
        return palavra.length() + 1;
    }

}
