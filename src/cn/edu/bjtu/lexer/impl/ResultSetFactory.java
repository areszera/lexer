package cn.edu.bjtu.lexer.impl;

import cn.edu.bjtu.lexer.ResultSet;

/**
 * The {@code ResultSetFactory} class is an util class that provides static methods to
 * assemble result strings according to the {@code ResultSet} object.
 */
public final class ResultSetFactory {

    private static final int FIRST_TAB = 8;
    private static final int SECOND_TAB = FIRST_TAB + 12;
    private static final int THIRD_TAB = SECOND_TAB + 12;
    private static final int FOURTH_TAB = THIRD_TAB + 12;
    private static final int FIFTH_TAB = FOURTH_TAB + 20;

    private static final String STRING_HEADER = "NO      BEGIN       END         LENGTH      TOKEN_TYPE          TOKEN";

    private static final String HTML_PREFIX = "<!DOCTYPE html>\n<html lang=\"en\">\n<head>\n\t<meta charset=\"UTF-8\">\n\t<title>Lexical Scan Results</title>\n\t<style>*{margin:0;padding:0;}table{border-collapse:collapse;margin:2rem auto;text-align:center;}th,td{border: 1px solid black;padding:.5rem;}.code{font-family:monospace;font-size:1rem;}</style>\n</head>\n<body>\n\t<table>\n\t\t<tr><th>NO</th><th>BEGIN</th><th>END</th><th>LENGTH</th><th>TOKEN TYPE</th><th>TOKEN</th></tr>\n";
    private static final String HTML_SUFFIX = "\t</table>\n</body>\n</html>";

    private static final String MARKDOWN_HEADER = "| NO | BEGIN | END | LENGTH | TOKEN TYPE | TOKEN |";
    private static final String MARKDOWN_ALIGN = "|:---:|:---:|:---:|:---:|:---:|:---:|";

    /**
     * Convert a {@code ResultSet} to string with header and values.
     * <p>
     * An example of the result in string:
     * <pre>
     * NO      BEGIN       END         LENGTH      TOKEN_TYPE          TOKEN
     * 1       1:1         1:4         3           KEYWORD             int
     * 2       1:5         1:9         4           IDENTIFIER          main
     * 3       1:9         1:10        1           DELIMITER           (
     * 4       1:10        1:11        1           DELIMITER           )
     * ...
     * </pre>
     *
     * @param rs {@code ResultSet} object to be converted.
     * @return String in string.
     */
    public static String toString(ResultSet rs) {
        int no = 1;
        StringBuilder builder = new StringBuilder(STRING_HEADER);
        rs.first();
        while (rs.next()) {
            StringBuilder temp = new StringBuilder("\n");
            temp.append(no);
            while (temp.length() <= FIRST_TAB) {
                temp.append(" ");
            }
            temp.append(rs.getBeginPosition().getRow()).append(":").append(rs.getBeginPosition().getColumn());
            while (temp.length() <= SECOND_TAB) {
                temp.append(" ");
            }
            temp.append(rs.getEndPosition().getRow()).append(":").append(rs.getEndPosition().getColumn());
            while (temp.length() <= THIRD_TAB) {
                temp.append(" ");
            }
            temp.append(rs.getEndPosition().getIndex() - rs.getBeginPosition().getIndex());
            while (temp.length() <= FOURTH_TAB) {
                temp.append(" ");
            }
            temp.append(rs.getTokenType());
            while (temp.length() <= FIFTH_TAB) {
                temp.append(" ");
            }
            temp.append(rs.getToken());
            builder.append(temp);
            no++;
        }
        return builder.toString();
    }

    /**
     * Convert a {@code ResultSet} to HTML with header and values.
     * <p>
     * An example of the result in HTML (only the table part):
     * <pre>
     * &lt;table&gt;
     *     &lt;tr&gt;&lt;th&gt;NO&lt;/th&gt;&lt;th&gt;BEGIN&lt;/th&gt;&lt;th&gt;END&lt;/th&gt;&lt;th&gt;LENGTH&lt;/th&gt;&lt;th&gt;TOKEN TYPE&lt;/th&gt;&lt;th&gt;TOKEN&lt;/th&gt;&lt;/tr&gt;
     *     &lt;tr&gt;&lt;td&gt;1&lt;/td&gt;&lt;td&gt;1:1&lt;/td&gt;&lt;td&gt;1:4&lt;/td&gt;&lt;td&gt;3&lt;/td&gt;&lt;td&gt;KEYWORD&lt;/td&gt;&lt;td class="code"&gt;int&lt;/td&gt;&lt;/tr&gt;
     *     &lt;tr&gt;&lt;td&gt;2&lt;/td&gt;&lt;td&gt;1:5&lt;/td&gt;&lt;td&gt;1:9&lt;/td&gt;&lt;td&gt;4&lt;/td&gt;&lt;td&gt;IDENTIFIER&lt;/td&gt;&lt;td class="code"&gt;main&lt;/td&gt;&lt;/tr&gt;
     *     &lt;tr&gt;&lt;td&gt;3&lt;/td&gt;&lt;td&gt;1:9&lt;/td&gt;&lt;td&gt;1:10&lt;/td&gt;&lt;td&gt;1&lt;/td&gt;&lt;td&gt;DELIMITER&lt;/td&gt;&lt;td class="code"&gt;(&lt;/td&gt;&lt;/tr&gt;
     *     &lt;tr&gt;&lt;td&gt;4&lt;/td&gt;&lt;td&gt;1:10&lt;/td&gt;&lt;td&gt;1:11&lt;/td&gt;&lt;td&gt;1&lt;/td&gt;&lt;td&gt;DELIMITER&lt;/td&gt;&lt;td class="code"&gt;)&lt;/td&gt;&lt;/tr&gt;
     *     ...
     * &lt;/table&gt;
     * </pre>
     *
     * @param rs {@code ResultSet} object to be converted.
     * @return String in HTML.
     */
    public static String toHtml(ResultSet rs) {
        int no = 1;
        StringBuilder builder = new StringBuilder(HTML_PREFIX);
        rs.first();
        while (rs.next()) {
            builder.append("\t\t<tr>")
                .append("<td>").append(no).append("</td>")
                .append("<td>").append(rs.getBeginPosition().getRow()).append(":").append(rs.getBeginPosition().getColumn()).append("</td>")
                .append("<td>").append(rs.getEndPosition().getRow()).append(":").append(rs.getEndPosition().getColumn()).append("</td>")
                .append("<td>").append(rs.getEndPosition().getIndex() - rs.getBeginPosition().getIndex()).append("</td>")
                .append("<td>").append(rs.getTokenType()).append("</td>")
                .append("<td class=\"code\">").append(rs.getToken().replaceAll("<", "&lt;").replaceAll(">", "&gt;")).append("</td>")
                .append("<tr>\n");
            no++;
        }
        return builder.append(HTML_SUFFIX).toString();
    }

    /**
     * Convert a {@code ResultSet} to Markdown with header and values.
     * <p>
     * An example of the result in Markdown (only the table part):
     * <pre>
     * | NO | BEGIN | END | LENGTH | TOKEN TYPE | TOKEN |
     * |:---:|:---:|:---:|:---:|:---:|:---:|
     * | 1 | 1:1 | 1:4 | 3 | KEYWORD | int |
     * | 2 | 1:5 | 1:9 | 4 | IDENTIFIER | main |
     * | 3 | 1:9 | 1:10 | 1 | DELIMITER | ( |
     * | 4 | 1:10 | 1:11 | 1 | DELIMITER | ( |
     * ...
     * </pre>
     *
     * @param rs {@code ResultSet} object to be converted.
     * @return String in Markdown.
     */
    public static String toMarkdown(ResultSet rs) {
        int no = 1;
        StringBuilder builder = new StringBuilder(MARKDOWN_HEADER).append("\n").append(MARKDOWN_ALIGN);
        rs.first();
        while (rs.next()) {
            builder.append("\n|").append(no)
                .append("|").append(rs.getBeginPosition().getRow()).append(":").append(rs.getBeginPosition().getColumn())
                .append("|").append(rs.getEndPosition().getRow()).append(":").append(rs.getEndPosition().getColumn())
                .append("|").append(rs.getEndPosition().getIndex() - rs.getBeginPosition().getIndex())
                .append("|").append(rs.getTokenType())
                .append("|").append(rs.getToken().replaceAll("\\|", "&verbar;"))
                .append("|");
            no++;
        }
        return builder.toString();
    }

}