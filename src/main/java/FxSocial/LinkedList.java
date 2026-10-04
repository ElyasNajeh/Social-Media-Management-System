package FxSocial;

public class LinkedList {

	private Node Front;
	private Node Back;
	private int Size;
	LinkedList() {
		this.Front = null;
		this.Back = null;
	}

	public Node getFront() {
		return Front;
	}

	public void setFront(Node front) {
		Front = front;
	}

	public Node getBack() {
		return Back;
	}

	public void setBack(Node back) {
		Back = back;
	}

	public int getSize() {
		return Size;
	}

	public void setSize(int size) {
		Size = size;
	}

	public Object getFirst() {
		if (getSize() == 0) {
			return null;
		} else {
			return getFront().getElement();
		}
	}

	public Object getLast() {
		if (getSize() == 0) {
			return null;
		} else {
			return getBack().getElement();
		}
	}

	public void addLast(Object User) {
		Node newNode = new Node(User);
		if (Size == 0) {
			setFront(newNode);
			setBack(newNode);
		} else {
			getBack().setNext(newNode);
			setBack(newNode);
		}
		Size++;
	}

	public boolean removeFirst() {
		if (getSize() == 0) {
			return false;
		} else if (getSize() == 1) {
			setFront(null);
			setBack(null);
		} else {
			setFront(getFront().getNext());
		}
		Size--;
		return true;
	}

	public boolean removeLast() {
		if (getSize() == 0) {
			return false;
		} else if (getSize() == 1) {
			setFront(null);
			setBack(null);
		} else {
			Node current = getFront();
			for (int i = 0; i < getSize() - 2; i++) {
				current = current.getNext();
			}
			current.setNext(null);
			setBack(current);
		}
		Size--;
		return true;
	}

	public boolean remove(int index) {
		if (getSize() == 0) {
			return false;
		} else if (index == 0) {
			return removeFirst();
		} else if (index == getSize() - 1) {
			return removeLast();
		} else if (index > 0 && index < getSize() - 1) {
			Node current = getFront();
			for (int i = 0; i < index - 1; i++) {
				current = current.getNext();
			}
			current.setNext(current.getNext().getNext());
			Size--;
			return true;

		}
		return false;
	}

	public Object get(int index) {
		if (getSize() == 0) {
			return null;
		} else if (index == 0) {
			return getFirst();
		} else if (index == getSize() - 1) {
			return getLast();
		} else if (index > 0 && index < getSize() - 1) {
			Node current = getFront();
			for (int i = 0; i < index; i++) {
				current = current.getNext();
			}
			return current.getElement();
		} else {
			return null;
		}
	}

	public void clear() {
		setFront(null);
		setBack(null);
		setSize(0);
	}
}
