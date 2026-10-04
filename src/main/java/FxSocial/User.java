package FxSocial;

public class User {
	private int userId;
	private String name;
	private int age;
	private LinkedList friend = new LinkedList();
	private LinkedList postCreated = new LinkedList();
	private LinkedList postShared = new LinkedList();

	public User() {

	}

	public User(int userId, String name, int age) {
		this.userId = userId;
		this.name = name;
		this.age = age;
	}

	public User(int userId, String name, int age, LinkedList friend, LinkedList postCreated, LinkedList postShared) {
		this.userId = userId;
		this.name = name;
		this.age = age;
		this.friend = friend;
		this.postCreated = postCreated;
		this.postShared = postShared;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public LinkedList getFriend() {
		return friend;
	}

	public void setFriend(LinkedList friend) {
		this.friend = friend;
	}

	public LinkedList getPostCreated() {
		return postCreated;
	}

	public void setPostCreated(LinkedList postCreated) {
		this.postCreated = postCreated;
	}

	public LinkedList getPostShared() {
		return postShared;
	}

	public void setPostShared(LinkedList postShared) {
		this.postShared = postShared;
	}

	public void addFriend(User friendUser) {
		this.friend.addLast(friendUser);
	}

	public void removeFriend(User friendUser) {
		for (int i = friend.getSize() - 1; i >= 0; i--) {
			User current = (User) friend.get(i);
			if (current == friendUser) {
				friend.remove(i);
			}
		}
	}

	public void createPost(Posts postUser) {
		this.postCreated.addLast(postUser);
	}

	public void addPostShared(Posts postSharedd) {
		this.postShared.addLast(postSharedd);
	}

	public void removeSharedPost(Posts post) {
		for (int i = postShared.getSize() - 1; i >= 0; i--) {
			if (postShared.get(i) == post) {
				postShared.remove(i);
			}
		}
	}

	public int getFriendsCount() {
		return friend.getSize();
	}

	public int getPostsCount() {
		return postCreated.getSize();
	}

	public int getPostsSharedCount() {
		return postShared.getSize();
	}

	@Override
	public String toString() {
		return "userId=" + userId + ", name=" + name + ", age=" + age + ", friend=" + friend + ", postCreated="
				+ postCreated + ", postShared=" + postShared;
	}

}
