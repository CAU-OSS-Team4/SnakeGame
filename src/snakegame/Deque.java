package snakegame;

class Deque {
	private int max_size;
	private Pair arr[];
	private int s, e;
	
	public Deque(int N) {
		max_size = N;
		arr = new Pair[N];
		s = 0; e = 0;
	}
	
	public void push_front(Pair x) {
		s = (s + max_size - 1) % max_size;
		arr[s] = x;
	}
	public void push_back(Pair x) {
		arr[e] = x;
		e = (e + 1) % max_size;
	}
	public Pair pop_front() {
		Pair ret = arr[s];
		s = (s + 1) % max_size;
		return ret;
	}
	public Pair pop_back() {
		e = (e + max_size - 1) % max_size;
		return arr[e];
	}
	public Pair front() {
		return arr[s];
	}
	public Pair rear() {
		return arr[(e + max_size - 1) % max_size];
	}
	
	public int size() {
		return (e - s + max_size) % max_size;
	}
	
	public Pair[] list() {
		int N = size();
		Pair[] ret = new Pair[N];
		for (int n = 0; n < N; n++) {
			int idx = (s + n) % max_size;
			ret[n] = new Pair(arr[idx].x, arr[idx].y);
		}
		return ret;
	}
}
