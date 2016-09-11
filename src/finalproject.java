import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 * Note!!! the structure of the question MUST 
 * remain the same but you could always change the keyword or synonym! 
 * Eg How many lectures are there? keyword lectures
 * how many topics are there?   synonym topics 
 * 
 * 
 * 
 * 
 * 
 * 
 */
public class finalproject {

	// this
	// creates
	// my
	public static class DicEntry {
		String key;
		String[] syns;
		Pattern pattern;

		public DicEntry(String key, String... syns) {
			this.key = key;
			this.syns = syns;
			pattern = Pattern.compile(".*(?:"
					+ Stream.concat(Stream.of(key), Stream.of(syns))
							.map(x -> "\\b" + Pattern.quote(x) + "\\b")
							.collect(Collectors.joining("|")) + ").*");
		}
	}

	public static void removedata(String s) throws IOException {

		File f = new File("data.txt");
		File f1 = new File("data2.txt");
		BufferedReader input = new BufferedReader(new InputStreamReader(
				System.in));
		BufferedReader br = new BufferedReader(new FileReader(f));
		PrintWriter pr = new PrintWriter(f1);
		String line;

		while ((line = br.readLine()) != null) {
			if (line.contains(s)) {

				System.out.println("Enter new Text :");
				String newText = input.readLine();
				line = newText;
			}

			pr.println(line);
		}
		br.close();
		pr.close();
		//input.close();
		Files.move(f1.toPath(), f.toPath(), StandardCopyOption.REPLACE_EXISTING);

	}

	public static void parseFile(String s) throws IOException {

		List<DicEntry> synonymMap = populateSynonymMap(); // populate the map

		File file = new File("data.txt");
		Scanner scanner = new Scanner(file);
		Scanner forget = new Scanner(System.in);

		int flag_found = 0;

		while (scanner.hasNextLine()) {
			final String lineFromFile = scanner.nextLine();

			for (DicEntry entry : synonymMap) { // iterate over each word of the
												// sentence.

				if (entry.pattern.matcher(s).matches()) { // if the keyword is
															// found

					if (lineFromFile.contains(entry.key)||entry.key.contains(lineFromFile)) { // if the line from
															// find has the
															// keyword

						for (String synonym : entry.syns) { // if the keyword is
															// not found but
															// the synonym is
															// found switch
															// synonym with
															// keyword
							if (s.contains(synonym)) {
								s = s.replace(synonym, entry.key);
								break;
							}
						}

						// String bat = entry.key;
						if (lineFromFile.contains(s)) {
							String temp = scanner.nextLine();
							System.out.println(temp);

							flag_found = 1;

							System.out
									.println(" Would you like to update this information ? ");
							String yellow = forget.nextLine();
							if (yellow.equals("yes")) {
								// String black = scanner.nextLine();
								removedata(temp);
							} else if (yellow.equals("no")) {

return;								// break;
							}

							// Add return statment to end the search
							return;
						}

					}
				}
			}
		}

		if (flag_found == 0) {// input is not found in the txt file so
			write2file(s);
			writer();
		}

	}

	public static void write2file(String g) { // / stores the question that is
												// not found into a text file
		File file = new File("data.txt");

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(
				file.getAbsoluteFile(), true))) {

			bw.write(g);
			bw.newLine();
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void getinput() throws IOException {

	    Scanner scanner = new Scanner(System.in);
	    String input = null;
	    /* End Initialization */

	    System.out.println("Welcome ");
	    System.out.println("What would you like to know?");
	    System.out.print("> ");
	    input = scanner.nextLine().toLowerCase();

	    while (!input.contains("no")) {
	        parseFile(input);

	        System.out.println("is there anything you want to know?");
	        input = scanner.nextLine().toLowerCase();
	    } 
	    System.out.println("have a good day");
	}

	
	
	
	public static void writer() {
		Scanner Keyboard = new Scanner(System.in);
		Scanner input = new Scanner(System.in);

		File file = new File("data.txt");
		try (BufferedWriter wr = new BufferedWriter(new FileWriter(
				file.getAbsoluteFile(), true))) { // Creates a writer object
													// called wr
													// file.getabsolutefile
													// takes the filename and
													// keeps on storing the old
			System.out.println("I Do not know, teach me!"
					+ "..."); // data
			while ((Keyboard.hasNext())) {

				String lines = Keyboard.nextLine();
				System.out.print(" is this correct ? ");
				String go = input.nextLine();

				if (go.equals("no")) {
					System.out.println("enter line again");
					lines = Keyboard.nextLine();
					System.out.print(" is this correct ? ");
					go = input.nextLine();
				}

				else if (go.equals("yes")) {
					wr.write(lines);
					wr.newLine();
					wr.close();
				}

				return;
			}
		} catch (IOException e) {
			System.out.println(" cannot write to file " + file.toString());
		}
	}

	private static List<DicEntry> populateSynonymMap() { // my dictionary which
															// contains synonyms
															// and keywords.
															// keywords being
															// first
		List<DicEntry> responses = new ArrayList<>();
		responses.add(new DicEntry("test", "exam", "quiz","evaluation"));
		responses.add(new DicEntry("textbook", "text","portfolio"));
		responses.add(new DicEntry("day", "time", "date"));
		responses.add(new DicEntry("current assignment", "homework","current work"));
		responses.add(new DicEntry("student", "pupil", "scholar"));
		responses.add(new DicEntry("office", "post", "place"));
		responses.add(new DicEntry("major", "discipline", "focus", "study"));
		responses.add(new DicEntry("lectures", "talk", "notes"));
		responses.add(new DicEntry("class", "homeroom", "teaching area"));
		responses.add(new DicEntry("due day", "hand in", "collection day"));
		responses.add(new DicEntry("id", "badge", "description","identity"));
		responses.add(new DicEntry("professor", "teacher", "instructor"));
		responses.add(new DicEntry("assignments", "work load", "tasks"));
		responses.add(new DicEntry("topics", "semester talk"));
		responses.add(new DicEntry("e-mail", "online mail", "electronic mail"));
		responses.add(new DicEntry("name", "tag", "alias"));
		responses.add(new DicEntry("group", "crew", "team"));
		responses.add(new DicEntry("number", "value", "amount"));


		return responses;
	}


	public static void main(String args[]) throws ParseException, IOException {
		/* Initialization */
		getinput();

	}

}

