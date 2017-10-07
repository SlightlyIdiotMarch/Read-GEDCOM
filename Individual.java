import java.awt.List;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class Individual {
	public static void main(String[] args) {
		try
		{
			if(args.length==0)
				throw new RuntimeException("Usage : <Filename>");
			try(FileReader fr = new FileReader(args[0]);
				BufferedReader bufferedReader = new BufferedReader(fr))
			{
				String string;
				ArrayList<String> all = new ArrayList<String>();
				while ((string = bufferedReader.readLine()) != null) { // save all information to the list
					all.add(string);
				}
				ArrayList<String> individual = new ArrayList<String>();
				for (int i = 0; i < all.size();) { // create individuals collection
					if (all.get(i).split(" ").length > 2 && all.get(i).split(" ")[2].equals("INDI")) {
						String ss = all.get(i).split(" ")[1];
						while (i < all.size()) {
							if (all.get(i).split(" ").length > 2 && all.get(i).split(" ")[1].equals("NAME")) {
								ss += all.get(i).substring(6, all.get(i).length());
								individual.add(ss);
								break;
							}
							++i;
						}
					}
					++i;
				}
				for (int i = 0; i < individual.size() - 1; ++i) { // sort
					for (int j = i + 1; j < individual.size(); ++j) {
						if (Integer.parseInt(individual.get(i).split(" ")[0].substring(1)) > Integer.parseInt(individual.get(j).split(" ")[0].substring(1))) {
							String replace = individual.get(i);
							individual.set(i, individual.get(j));
							individual.set(j, replace);
						}
					}
				}
				for (String s: individual) {
					System.out.println(s);
				}
				
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
