package TreeFile;

import java.util.*;

public class TreeFile {

	public static void main(String[] args) {
		class Node {
			String name;
			List<Node> children;

			Node(String name) {
				this.name = name;
				this.children = new ArrayList<Node>();
			}

			void addChildren(String childName) {
				String[] path = childName.split("/");
				Node current = this;
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

			void display(int level) {
				System.out.print(name.indent(level * 2));
				for (Node child : children) {
					child.display(level + 1);
				}
			}

			class ComparebyName implements Comparator<Node> {
				public int compare(Node n1, Node n2) {
					return n1.name.compareTo(n2.name);
				}

			}

			public void deleteNode(String path) {
				String[] parts = path.split("/");
				Node current = this;
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

			public Boolean isSearchNode(String name) {
				Node current = this;
				if (current.name.equals(name)) {
					return true;
				} else {
					for (Node child : current.children) {
						if (child.isSearchNode(name)) {
							return true;
						}
					}

				}
				return false;
			}

			public void searchNode(String name) {
				if (this.isSearchNode(name)) {
					System.out.println("Node found. The path is: " + findPath(name));
				} else {
					System.out.println("Node not found");
				}
			}

			public String findPath(String fileName) {
				// Dosya adı verilen dosyayı içeren düğümün yolunu bulmak için derinlemesine
				// arama yapılır
				return findPathRecursive(this, fileName, "");
			}

			private String findPathRecursive(Node node, String fileName, String currentPath) {
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
			public void inOrderTraversal(Node node) {
				System.out.println("Inorder traversal is not possible because of the tree is not a BST.");
				System.out.println("The tree is ordered by alphabetical");
			}

			// Pre-order traversal: Root -> Left -> Right
			public void preOrderTraversal(Node node) {
				if (node != null) {
					System.out.println(node.name);
					for (Node child : node.children) {
						preOrderTraversal(child);
					}
				}
			}

			// Post-order traversal: Left -> Right -> Root
			public void postOrderTraversal(Node node) {
				if (node != null) {
					for (Node child : node.children) {
						postOrderTraversal(child);
					}
					System.out.println(node.name);
				}
			}

		}

		// main
		Node root = new Node("Media_Root");
		root.addChildren("Media_Root/Videos");
		root.addChildren("Media_Root/Music/Pop/track2.mp3");
		root.addChildren("Media_Root/Photos");
		root.addChildren("Media_Root/Music/Pop/track1.mp3");
		root.addChildren("Media_Root/Music/Jazz/track4.mp3");
		root.addChildren("Media_Root/Music/Jazz/track3.mp3");
		root.addChildren("Media_Root/Photos/Vacation/photo1.jpg");
		root.addChildren("Media_Root/Photos/Vacation/photo2.jpg");
		root.addChildren("Media_Root/Photos/Events/photo3.jpg");
		root.addChildren("Media_Root/Photos/Events/photo4.jpg");
		root.addChildren("Media_Root/Videos/Movies/movie1.mp4");
		root.addChildren("Media_Root/Videos/Documantaries/doc2.mp4");
		root.addChildren("Media_Root/Videos/Movies/movie2.mp4");
		root.addChildren("Media_Root/Videos/Documantaries/doc1.mp4");
		root.display(0);

		Scanner scanner = new Scanner(System.in);

		System.out.println("Enter the path to deleting file: ");
		String path = scanner.nextLine();

		root.deleteNode(path);
		root.display(0);

		System.out.println("Select traversal method:");
		System.out.println("1. In-Order");
		System.out.println("2. Pre-Order");
		System.out.println("3. Post-Order");
		int traversalType = scanner.nextInt();
		scanner.nextLine();

		switch (traversalType) {
		case 1:
			System.out.println("In-order traversal:");
			root.inOrderTraversal(root);
			break;
		case 2:
			System.out.println("Pre-order traversal:");
			root.preOrderTraversal(root);
			break;
		case 3:
			System.out.println("Post-order traversal:");
			root.postOrderTraversal(root);
			break;
		default:
			System.out.println("Invalid traversal type.");
			break;
		}

		scanner.close();

	}

}
