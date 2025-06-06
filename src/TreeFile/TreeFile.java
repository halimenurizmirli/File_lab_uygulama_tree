package TreeFile;

import java.io.*;
import java.util.*;

public class TreeFile {
	static List<Node> roots = new ArrayList<Node>();

	public static Node singletion(String name) {
		for (Node node : roots) {
			if (node.name.equals(name)) {
				return node;
			}
		}

		Node newNode = new Node(name);
		roots.add(newNode);
		return newNode;

	}

	static void readFile() throws IOException {
		BufferedReader read = new BufferedReader(new FileReader("veriler_sirali.txt"));
		String line;
		while ((line = read.readLine()) != null) {
			System.out.println(line);

		}
		read.close();

	}

	public static void main(String[] args) {

		// main

		try {
			BufferedReader reader = new BufferedReader(new FileReader("veriler.txt"));
			String line;
			Node root = null;
			while ((line = reader.readLine()) != null) {
				String[] path = line.split("/");
				root = singletion(path[0]);

				Node.addChildren(line, root);
			}

			reader.close();
			
			BufferedWriter writer = new BufferedWriter(new FileWriter("veriler_sirali.txt"));
			//bütün kök düğümler yeni dosyaya sıralı bir şekilde eklenir.			
			for (Node n : roots) {
				Node.writeToFile(0, n, writer);
			}
			writer.close();
			
			//dosyayı ekrana bastır
			readFile();

			Scanner scanner = new Scanner(System.in);

			System.out.println("Enter the path to deleting file: ");
			String path = scanner.nextLine();
			for (Node root1 : roots) {
			Node.deleteNode(path, roots.get(0));
			}

			System.out.println("Enter the name to searching: ");
			String name = scanner.nextLine();
			boolean found = false;
			for (Node root2 : roots) {
				if (Node.searchNode(name, root2)) {
					System.out.println("Node found. The path is: " + Node.findPath(root2,name,"" ));
					found = true;
					break;
				}

			}
			if (!found) {
				System.out.println("Node not found");
			}

			System.out.println("Select traversal method:");
			System.out.println("1. In-Order");
			System.out.println("2. Pre-Order");
			System.out.println("3. Post-Order");
			int traversalType = scanner.nextInt();
			scanner.nextLine();
			for(Node root3: roots) {
			switch (traversalType) {
			case 1:
				System.out.println("In-order traversal:");
				Node.inOrderTraversal(root3);
				break;
			case 2:
				System.out.println("Pre-order traversal:");
				Node.preOrderTraversal(root3);
				break;
			case 3:
				System.out.println("Post-order traversal:");
				Node.postOrderTraversal(root3);
				break;
			default:
				System.out.println("Invalid traversal type.");
				break;
			}
			}

			scanner.close();
		} catch (IOException e) {
			System.err.println("Dosya okuma hatası: " + e.getMessage());
		}

	}

}
