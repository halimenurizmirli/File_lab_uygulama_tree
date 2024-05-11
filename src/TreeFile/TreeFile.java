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

	public static void main(String[] args) {

		// main

		try {
			BufferedReader reader = new BufferedReader(new FileReader("veriler.txt"));
			String line;
			Node root = null;
			while ((line = reader.readLine()) != null) {
				String[] path = line.split("/");
				root =singletion(path[0]);

				
					Node.addChildren(line,root);
				

			}

			for (Node n : roots) {
				Node.display(0,n);
			}

			reader.close();
		

		Scanner scanner = new Scanner(System.in);

		System.out.println("Enter the path to deleting file: ");
		String path = scanner.nextLine();

		Node.deleteNode(path,root);
		Node.display(0,root);

		System.out.println("Select traversal method:");
		System.out.println("1. In-Order");
		System.out.println("2. Pre-Order");
		System.out.println("3. Post-Order");
		int traversalType = scanner.nextInt();
		scanner.nextLine();

		switch (traversalType) {
		case 1:
			System.out.println("In-order traversal:");
			Node.inOrderTraversal(root);
			break;
		case 2:
			System.out.println("Pre-order traversal:");
			Node.preOrderTraversal(root);
			break;
		case 3:
			System.out.println("Post-order traversal:");
			Node.postOrderTraversal(root);
			break;
		default:
			System.out.println("Invalid traversal type.");
			break;
		}

		scanner.close();
		} catch (IOException e) {
			System.err.println("Dosya okuma hatası: " + e.getMessage());
		}

	}

}
