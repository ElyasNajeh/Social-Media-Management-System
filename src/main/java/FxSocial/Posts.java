package FxSocial;

public class Posts {
	private int postId;
	private User creatorId;
	private String content;
	private String creationDate;

	public Posts(int postId, User creatorId, String content, String creationDate) {
		this.postId = postId;
		this.creatorId = creatorId;
		this.content = content;
		this.creationDate = creationDate;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public User getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(User creatorId) {
		this.creatorId = creatorId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("User ID: ").append(creatorId.getUserId()).append("\n");
		sb.append("Post ID: ").append(postId).append("\n");
		sb.append("Post Date: ").append(creationDate).append("\n");
		sb.append("Content:\n").append(content).append("\n");

		return sb.toString();
	}

}
