package idwall.desafio.string;

/**
 * Created by Rodrigo Catão Araujo on 06/02/2018.
 */
public abstract class StringFormatter {

    private Integer limit;

    StringFormatter(Integer limit) {
        this.limit = limit;
    }

    Integer getLimit() {
        return limit;
    }

    /**
     * It receives a text and should return it formatted
     *
     * @param text
     * @param justify
     * @return
     */
    public abstract String format(String text, boolean justify);
}
