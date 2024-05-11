package TreeFile;

import java.io.*;
import java.util.*;

public class Node {
	String name = null;
	List<Node> children;

	Node(String name) {
		this.name = name;
		this.children = new ArrayList<Node>();
	}

	static void addChildren(String childName, Node node) {
		String[] path = childName.split("/");
		Node current = node;
		for (int i = 1; i < path.length; i++) {
			String directory = path[i];
			Node child = null;
			for (Node existingChild : current.children) {
				if (existingChild.name.equals(directory)) {
					child = existingChild;
					break;
				}
			}
			if (child == null) {
				child = new Node(directory);
				current.children.add(child);
				Collections.sort(current.children, new ComparebyName());
			}
			current = child;
		}

	}

	static class ComparebyName implements Comparator<Node> {
		public int compare(Node n1, Node n2) {
			return n1.name.compareTo(n2.name);
		}

	}

	static void writeToFile(int level, Node node, BufferedWriter writer) {
		try {

			writer.write(node.name.indent(level * 2));
			for (Node child : node.children) {
				writeToFile(level + 1, child, writer);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void deleteNode(String path, Node node) {
		String[] parts = path.split("/");
		Node current = node;
		boolean found = false;
		if (current.name.equals(parts[0])) {

			// Düğümleri yol boyunca takip et
			for (int i = 1; i < parts.length - 1; i++) {
				String nodeName = parts[i];
				found = false;

				// Çocukları ara
				for (Node child : current.children) {
					if (child.name.equals(nodeName)) {
						current = child;
						found = true;
						break;
					}
				}
				if (!found) {
					System.out.println("Node not found");
					return;
				}

			}

			String nodeName = parts[parts.length - 1];
			for (Node child : current.children) {
				if (child.name.equals(nodeName)) {
					found = true;
					break;
				}
			}
			if (!found) {
				System.out.println("Node not found");
				return;
			}

		}

		// Silinecek düğümün adı
		String nodeNameToDelete = parts[parts.length - 1];

		// Silinecek düğümü bul
		Iterator<Node> iterator = current.children.iterator();
		while (iterator.hasNext()) {
			Node child = iterator.next();
			if (child.name.equals(nodeNameToDelete)) {
				// Düğümü kaldır ve işlemi bitir
				iterator.remove();
				System.out.println("Deleting is successful");
				return;
			}
		}

		// Silinecek düğüm bulunamazsa hata mesajı yazdır
		System.out.println("Node not found");
	}

	private static Boolean isSearchNode(String name, Node node) {
		Node current = node;
		if (current.name.equals(name)) {
			return true;
		} else {
			for (Node child : current.children) {
				if (isSearchNode(name, child)) {
					return true;
				}
			}

		}
		return false;
	}

	public static void searchNode(String name, Node node) {
		if (isSearchNode(name, node)) {
			System.out.println("Node found. The path is: " + findPath(name, node));
		} else {
			System.out.println("Node not found");
		}
	}

	public static String findPath(String fileName, Node node) {
		// Dosya adı verilen dosyayı içeren düğümün yolunu bulmak için derinlemesine
		// arama yapılır
		return findPathRecursive(node, fileName, "");
	}

	private static String findPathRecursive(Node node, String fileName, String currentPath) {
		// Geçerli düğümün adı belirtilen dosya adıyla eşleşirse, dosyanın yolu bulunmuş
		// olur
		if (node.name.equals(fileName)) {
			return currentPath + node.name;
		}

		// Dosya adı verilen dosya bu düğümde bulunamazsa, alt düğümlere bakılır
		for (Node child : node.children) {
			// Yolun devamı olarak bu düğümün adı eklenir ve alt düğümlere doğru arama devam
			// eder
			String path = findPathRecursive(child, fileName, currentPath + node.name + "/");
			// Eğer dosyanın yolunu bulmuşsak, bu yolu döndürürüz
			if (path != null) {
				return path;
			}
		}

		// Dosya bu düğüm altında veya alt düğümlerde bulunamazsa null döndürülür
		return null;
	}

	// In-order traversal: Left -> Root -> Right
	public static void inOrderTraversal(Node node) {
		System.out.println("Inorder traversal is not possible because of the tree is not a BST.");
		System.out.println("The tree is ordered by alphabetical");
	}

	// Pre-order traversal: Root -> Left -> Right
	public static void preOrderTraversal(Node node) {
		if (node != null) {
			System.out.println(node.name);
			for (Node child : node.children) {
				preOrderTraversal(child);
			}
		}
	}

	// Post-order traversal: Left -> Right -> Root
	public static void postOrderTraversal(Node node) {
		if (node != null) {
			for (Node child : node.children) {
				postOrderTraversal(child);
			}
			System.out.println(node.name);
		}
	}

}
