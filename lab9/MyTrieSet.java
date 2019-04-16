import java.util.*;

public class MyTrieSet implements TrieSet61B {
	private class Node {
		private char currentCh;
		private boolean isKey;
		private Map<Character, Node> map = new HashMap<>();

		private Node() {
		}

		private Node(char c, boolean b) {
			currentCh =c;
			isKey = b;
		}
	}

	private Node root;

	public MyTrieSet() {
		root = new Node();
	}

	/** Clears all items out of Trie */
	@Override
	public void clear() {
		root.map.clear();
	}

	/** Returns true if the Trie contains KEY, false otherwise */
	@Override
	public boolean contains(String key) {
		Node cur = root;

		for (char c : key.toCharArray()) {
			if (!cur.map.containsKey(c)) {
				return false;
			} else {
				cur = cur.map.get(c);
			}
		}
		return (cur != null && cur.isKey);
	}

	/** Inserts string KEY into Trie */
	@Override
	public void add(String key) {
		if (key == null || key.length() < 1) {
			return;
		}
		Node curr = root;
		for (int i = 0, n = key.length(); i < n; i++) {
			char c = key.charAt(i);
			if (!curr.map.containsKey(c)) {
				curr.map.put(c, new Node(c, false));
			}
			curr = curr.map.get(c);
		}
		curr.isKey = true;
	}


	/** Returns a list of all words that start with PREFIX
	 *  How to do this:
	 *    1. Search for given query.
	 *    2. If query prefix itself is not present, return result to indicate the same.
	 *    3. If query is present and is end of word in Trie, store query.
	 *    4. If last matching node of query has no children, return.
	 *    5. Else recursively store all nodes under subtree of last matching node.
	 */
	@Override
	public List<String> keysWithPrefix(String prefix) {
		List<String> result = new ArrayList<>();    // Store words
		Node cur = root;

		// Point 1 & 2
		for (char c : prefix.toCharArray()) {
			if (!cur.map.containsKey(c)) {
				return result;      // If there is any prefix char not in trie, directly return result
			} else {
				cur = cur.map.get(c);
			}
		}
		// Point 3
		if (cur.isKey) {
			result.add(prefix);
		}
		// Point 4 & 5
		if (cur.map.isEmpty()) {
			return result;
		} else {
			for (char c : cur.map.keySet()) {
				colHelp(prefix + c, result, cur.map.get(c));
			}
		}
		return result;
	}

	private void colHelp(String s, List<String> l, Node n) {
		if (n.isKey) {
			l.add(s);
		}
		if (n.map.isEmpty()) {
			return;
		} else {
			for (char c : n.map.keySet()) {
				colHelp(s + c, l, n.map.get(c));
			}
		}
	}

	/** Returns the longest prefix of KEY that exists in the Trie
	 * Not required for Lab 9. If you don't implement this, throw an
	 * UnsupportedOperationException.
	 */
	public String longestPrefixOf(String key) {
		Node cur = root;

		// If there is any prefix char not in trie, directly return ""
		for (char c : key.toCharArray()) {
			if (!cur.map.containsKey(c)) {
				return "";
			} else {
				cur = cur.map.get(c);
			}
		}

		if (cur.map.size() == 1) {
			for (char c : cur.map.keySet()) {
			 	cur = cur.map.get(c);
			}
			return longestPrefixOf(key + cur.currentCh);
		} else {
			return key;
		}
	}

	public static void main(String[] args) {
		MyTrieSet t = new MyTrieSet();
		t.add("hello");
		t.add("hi");
		t.add("help");
		t.add("her");
		t.add("hey");
		t.add("heritage");
		t.add("heap");
		t.add("zebra");
		System.out.println("Is the trie contain 'hell'? " + t.contains("hell"));

		for (String s : t.keysWithPrefix("he")) {
			System.out.print(s + " ");
		}
		System.out.println();
		System.out.println(t.longestPrefixOf("hel"));
	}
}
