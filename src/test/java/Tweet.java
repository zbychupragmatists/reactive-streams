public class Tweet {
    private final String author;
    private final String comment;

    public Tweet(String author, String comment) {
        this.author = author;
        this.comment = comment;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Tweet{");
        sb.append("author='").append(author).append('\'');
        sb.append(", comment='").append(comment).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
