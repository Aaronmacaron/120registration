public class Reaction {
    private ReactionType reactionType;
    private String message;

    public Reaction(ReactionType reactionType, String message) {
        this.reactionType = reactionType;
        this.message = message;
    }

    public boolean isSuccess() {
        return this.reactionType == ReactionType.SUCCESS;
    }

    public boolean isFailure() {
        return this.reactionType == ReactionType.FAILURE;
    }

    public boolean isError() {
        return this.reactionType == ReactionType.ERROR;
    }

    public String getMessage() {
        return message;
    }

    public static Reaction success() {
        return new Reaction(ReactionType.SUCCESS, "");
    }

    public static Reaction success(String message) {
        return new Reaction(ReactionType.SUCCESS, message);
    }

    public static Reaction failure() {
        return new Reaction(ReactionType.FAILURE, "");
    }

    public static Reaction failure(String message) {
        return new Reaction(ReactionType.FAILURE, message);
    }

    public static Reaction error() {
        return new Reaction(ReactionType.ERROR, "");
    }

    public static Reaction error(String message) {
        return new Reaction(ReactionType.ERROR, message);
    }

    public Reaction succeed(ReactionExecutor reactionExecutor) {
        if (this.isSuccess()) {
            reactionExecutor.execute(this);
        }

        return this;
    }

    public Reaction fail(ReactionExecutor reactionExecutor) {
        if (this.isFailure()) {
            reactionExecutor.execute(this);
        }

        return this;
    }

    public Reaction resolve(ReactionExecutor reactionExecutor) {
        if (this.isError()) {
            reactionExecutor.execute(this);
        }

        return this;
    }

    private enum ReactionType {
        SUCCESS, FAILURE, ERROR
    }
}
