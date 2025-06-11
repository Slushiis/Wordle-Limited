import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main
{
    static Scanner heyo;
    public static void main(String[] args)
    {
        List <Abcs> abcs = new ArrayList<>();

        abcs.add(new Abcs('A', 0));
        abcs.add(new Abcs('B', 0));
        abcs.add(new Abcs('C', 0));
        abcs.add(new Abcs('D', 0));
        abcs.add(new Abcs('E', 0));
        abcs.add(new Abcs('F', 0));
        abcs.add(new Abcs('G', 0));
        abcs.add(new Abcs('H', 0));
        abcs.add(new Abcs('I', 0));
        abcs.add(new Abcs('J', 0));
        abcs.add(new Abcs('K', 0));
        abcs.add(new Abcs('L', 0));
        abcs.add(new Abcs('M', 0));
        abcs.add(new Abcs('N', 0));
        abcs.add(new Abcs('O', 0));
        abcs.add(new Abcs('P', 0));
        abcs.add(new Abcs('Q', 0));
        abcs.add(new Abcs('R', 0));
        abcs.add(new Abcs('S', 0));
        abcs.add(new Abcs('T', 0));
        abcs.add(new Abcs('U', 0));
        abcs.add(new Abcs('V', 0));
        abcs.add(new Abcs('W', 0));
        abcs.add(new Abcs('X', 0));
        abcs.add(new Abcs('Y', 0));
        abcs.add(new Abcs('Z', 0));

        int wins = 0;
        int losses = 0;

        List <String> fiveLetters = getFiveLs();

        boolean run = true;
        while (run)
        {
            System.out.println("5 LETTER LIMITED WORDLE GAME");
            System.out.println("1 - New game (Easy)");
            System.out.println("2 - New game (Hard)");
            System.out.println("3 - Current game record");
            System.out.println("4 - Rules");
            System.out.println("5 - Exit");
            int choice = heyo.nextInt();
            switch (choice)
            {
                case 1  ->
                {
                    heyo.nextLine();
                    boolean temp = wordle(fiveLetters, abcs);
                    if (temp)
                    {
                        wins++;
                    }
                    else
                    {
                        losses++;
                    }
                }
                case 2 ->
                {
                    heyo.nextLine();
                    boolean temp = hardWordle(fiveLetters, abcs);
                    if (temp)
                    {
                        wins++;
                    }
                    else
                    {
                        losses++;
                    }
                }
                case 3 ->
                {
                    System.out.println("| Wins: " + wins + " | Losses: " + losses + " |");
                }
                case 4 ->
                {
                    System.out.println("""
                           
                           ||===================================================================================================================================================================================
                           || 0. All words are 5 letter long.                                                                                                                                                 ||
                           || 1. Letters that are in the answer and in the right place turn green.                                                                                                            ||
                           || 2. Letters that are in the answer but in the wrong place turn yellow.                                                                                                           ||
                           || 3. Letters that are not in the answer turn gray.                                                                                                                                ||
                           || 4. Letters can appear more than once. So if your guess includes two of one letter, they may both turn yellow, both turn green,                                                  ||
                           || or one could be yellow and the other green.                                                                                                                                     ||
                           || 5. Each guess must be a valid word in LIMITED WORDLE dictionary. You can't guess "ABCDE".                                                                                       ||
                           || 6. You have six guesses to solve the WORDLE.                                                                                                                                    ||
                           || 7. In HARD mode you have to use found letter in subsequent guesses.                                                                                                             ||
                           ||===================================================================================================================================================================================
                           """);

                }
                case 5 ->
                {
                    run = false;
                }
                default ->
                {
                    System.out.println("No such choice in the menu, try a different choice.");
                }
            }
        }
        heyo.close();
    }

    private static ArrayList<String> getFiveLs()
    {
        List<String> fiveLetters = new ArrayList<>();
        File fiveLs = new File("fiveLetter.txt");
        Scanner fileReader;

        try
        {
            fileReader = new Scanner(fiveLs);
        }
        catch (FileNotFoundException e)
        {
            throw new RuntimeException(e);
        }
        while (fileReader.hasNext())
        {
            fiveLetters.add(fileReader.next());
        }
        fileReader.close();
        return new ArrayList<>(fiveLetters);
    }

    private static boolean wordle(List<String> fiveLetters, List<Abcs> abcs)
    {
        // ANSI escape codes for color
        String greenColor = "\u001b[32m"; // Green
        String yellowColor = "\u001B[33m"; // Yellow
        String darkgrayColor = "\u001b[90m"; // Dark Gray
        String resetColor = "\u001B[0m"; // Reset to default color

        boolean win = false;

        Random random = new Random();
        int randomWord = random.nextInt(fiveLetters.size());
        String aim = fiveLetters.get(randomWord);
        char[] att = aim.toUpperCase().toCharArray();

        //Testing
        //System.out.println(aim);

        for (int inst = 1; inst <= 6; inst++)
        {
            String word = null;
            boolean run = true;
            while(run)
            {
                System.out.println();
                System.out.print("| ");
                for (Abcs abc : abcs)
                {
                    if (abc.getCode() == 0)
                    {
                        System.out.print(abc.getLetter() + " | ");
                    }
                    else if (abc.getCode() == 1)
                    {
                        System.out.print(darkgrayColor + abc.getLetter() + resetColor + " | ");
                    }
                    else if (abc.getCode() == 2)
                    {
                        System.out.print(yellowColor + abc.getLetter() + resetColor + " | ");
                    }
                    else
                    {
                        System.out.print(greenColor + abc.getLetter() + resetColor + " | ");
                    }
                }
                System.out.println();

                System.out.println("Guess Nr." + inst);
                word = heyo.nextLine();

                if (!(fiveLetters.contains(word.toUpperCase())))
                {
                    System.out.println("Word not in the list.");
                    continue;
                }
                run = false;
            }

            char[] letters = word.toUpperCase().toCharArray();

            for (int i = 0; i < 5; i++)
            {
                int index = 0;
                for (int j = 0; j < 5; j++)
                {
                    if (letters[i] == att[j] && i == j)
                    {
                        index = 2;
                        break;
                    }
                    else if (letters[i] == att[j])
                    {
                        index = 1;
                    }
                }
                if (index == 2)
                {
                    for (Abcs abc : abcs)
                    {
                        if(abc.getLetter() == letters[i])
                        {
                            abc.setCode(3);
                        }
                    }
                    System.out.print(greenColor + letters[i] + resetColor);
                }
                else if (index == 1)
                {
                    for (Abcs abc : abcs)
                    {
                        if(abc.getLetter() == letters[i] && abc.getCode() != 3)
                        {
                            abc.setCode(2);
                        }
                    }
                    System.out.print(yellowColor + letters[i] + resetColor);
                }
                else
                {
                    for (Abcs abc : abcs)
                    {
                        if(abc.getLetter() == letters[i] && abc.getCode() != 3)
                        {
                            abc.setCode(1);
                        }
                    }
                    System.out.print(letters[i]);
                }
            }
            System.out.println();
            if (aim.equalsIgnoreCase(word))
            {
                System.out.println("Congrats, you guessed the word!");
                win = true;
                break;
            }
            else if (inst == 6)
            {
                System.out.println("Unfortunately, you didn't guess the word, better luck next time. Word was: " + aim.toUpperCase());
            }
        }
        for (Abcs abc : abcs)
        {
            abc.setCode(0);
        }
        return win;
    }

    private static boolean hardWordle(List<String> fiveLetters, List<Abcs> abcs)
    {
        // ANSI escape codes for color
        String greenColor = "\u001b[32m"; // Green
        String yellowColor = "\u001B[33m"; // Yellow
        String darkgrayColor = "\u001b[90m"; // Dark Gray
        String resetColor = "\u001B[0m"; // Reset to default color

        boolean win = false;

        Random random = new Random();
        int randomWord = random.nextInt(fiveLetters.size());
        String aim = fiveLetters.get(randomWord);
        char[] att = aim.toUpperCase().toCharArray();

        //Testing
        //System.out.println(aim);

        for (int inst = 1; inst <= 6; inst++)
        {
            String word = null;
            boolean run = true;
            while(run)
            {
                System.out.println();
                System.out.print("| ");
                for (Abcs abc : abcs)
                {
                    if (abc.getCode() == 0)
                    {
                        System.out.print(abc.getLetter() + " | ");
                    }
                    else if (abc.getCode() == 1)
                    {
                        System.out.print(darkgrayColor + abc.getLetter() + resetColor + " | ");
                    }
                    else if (abc.getCode() == 2)
                    {
                        System.out.print(yellowColor + abc.getLetter() + resetColor + " | ");
                    }
                    else
                    {
                        System.out.print(greenColor + abc.getLetter() + resetColor + " | ");
                    }
                }
                System.out.println();

                System.out.println("Guess Nr." + inst);
                word = heyo.nextLine();

                if (!(fiveLetters.contains(word.toUpperCase())))
                {
                    System.out.println("Word not in the list, you may guess again.");
                    continue;
                }
                boolean checkLetters = true;
                for (Abcs abc : abcs)
                {
                    if ((abc.getCode() == 3 || abc.getCode() == 2) && !(word.toUpperCase().contains(String.valueOf(abc.getLetter()))))
                    {
                        checkLetters = false;
                        break;
                    }
                }
                if (!checkLetters)
                {
                    System.out.println("Not all found letters were used, you can try again.");
                    continue;
                }
                run = false;
            }

            char[] letters = word.toUpperCase().toCharArray();
            for (int i = 0; i < 5; i++)
            {
                int index = 0;
                for (int j = 0; j < 5; j++)
                {
                    if (letters[i] == att[j] && i == j)
                    {
                        index = 2;
                        break;
                    }
                    else if (letters[i] == att[j])
                    {
                        index = 1;
                    }
                }
                if (index == 2)
                {
                    for (Abcs abc : abcs)
                    {
                        if(abc.getLetter() == letters[i])
                        {
                            abc.setCode(3);
                        }
                    }
                    System.out.print(greenColor + letters[i] + resetColor);
                }
                else if (index == 1)
                {
                    for (Abcs abc : abcs)
                    {
                        if(abc.getLetter() == letters[i] && abc.getCode() != 3)
                        {
                            abc.setCode(2);
                        }
                    }
                    System.out.print(yellowColor + letters[i] + resetColor);
                }
                else
                {
                    for (Abcs abc : abcs)
                    {
                        if(abc.getLetter() == letters[i] && abc.getCode() != 3)
                        {
                            abc.setCode(1);
                        }
                    }
                    System.out.print(letters[i]);
                }
            }
            System.out.println();
            if (aim.equalsIgnoreCase(word))
            {
                System.out.println("Congrats, you guessed the word!");
                win = true;
                break;
            }
            else if (inst == 6)
            {
                System.out.println("Unfortunately, you didn't guess the word, better luck next time. Word was: " + aim.toUpperCase());
            }
        }
        for (Abcs abc : abcs)
        {
            abc.setCode(0);
        }
        return win;
    }

    static
    {
        heyo = new Scanner(System.in);
    }
}

class Abcs
{
    private final char letter;
    private int code;
    public Abcs(char letter, int code)
    {
        this.letter = letter;
        this.code = code;
    }
    public char getLetter()
    {
        return letter;
    }
    public int getCode()
    {
        return code;
    }
    public void setCode(int code)
    {
        this.code = code;
    }
}

