import edu.princeton.cs.algs4.In;

public class Day07 {
    // where we get input from
    private static final String FILE_PATH =
            "./input_files/day07ex.txt";

    // runs both solutions
    public static void main(String[] args) {
        In in = new In(FILE_PATH);
        System.out.print(part1I(in));
        // need new input cause we already used it
        in = new In(FILE_PATH);
        System.out.print(part1R(in));
    }

    // iterative solution
    public static long part1I(In in) {
        // total we're gonna return
        long answer = 0;

        // go through the file
        while (in.hasNextLine()) {
            String line = in.readLine();
            // split into the target and numbers we can use
            String[] splitLine = line.split(":");
            long target = Long.parseLong(splitLine[0]);
            // get rid of spaces and split numbers
            String[] nums = splitLine[1].trim().split(" ");
            long[] numbers = new long[nums.length];

            // turn strings into numbers
            for (int i = 0; i < nums.length; i++) {
                numbers[i] = Long.parseLong(nums[i]);
            }

            boolean foundMatch = false;

            // try using an array to keep track of operators
            // 0 means +, 1 means *
            int[] operators = new int[numbers.length - 1];

            // keep trying different operator combinations until we try them all
            while (true) {
                // calculate result using current operators
                long result = numbers[0];
                for (int i = 0; i < operators.length; i++) {
                    if (operators[i] == 0) {
                        result = result + numbers[i + 1];
                    } else {
                        result = result * numbers[i + 1];
                    }
                }

                // check if we got the target
                if (result == target) {
                    foundMatch = true;
                    break;
                }

                // change operators for next try
                // like counting in binary but easier to understand
                int pos = 0;
                while (pos < operators.length) {
                    if (operators[pos] == 0) {
                        operators[pos] = 1;
                        break;
                    } else {
                        operators[pos] = 0;
                        pos++;
                    }
                }

                // if we changed all operators back to 0, we're done
                if (pos == operators.length) {
                    break;
                }
            }

            // add to answer if we found a way
            if (foundMatch) {
                answer += target;
            }
        }
        return answer;
    }

    // recursive solution
    public static long part1R(In in) {
        // same as iterative but using recursion
        long answer = 0;

        // read the file
        while (in.hasNextLine()) {
            String line = in.readLine();
            // split into target and numbers again
            String[] splitLine = line.split(":");
            long target = Long.parseLong(splitLine[0]);
            // split up the numbers part
            String[] nums = splitLine[1].trim().split(" ");
            long[] numbers = new long[nums.length];

            // make them into real numbers
            for (int i = 0; i < nums.length; i++) {
                numbers[i] = Long.parseLong(nums[i]);
            }

            // try to get target with recursion
            // start at beginning with first number
            if (tryOperations(numbers, target, 0, numbers[0])) {
                answer += target;
            }
        }
        return answer;
    }

    // helper for recursion stuff
    // nums = our numbers
    // target = what we want
    // pos = where we are
    // current = what we got so far
    private static boolean tryOperations(long[] nums, long target, int pos, long current) {
        // base case - used all numbers
        if (pos == nums.length - 1) {
            // check if we got it
            return current == target;
        }

        // try adding first
        boolean add = tryOperations(nums, target, pos + 1, current + nums[pos + 1]);
        if (add) {
            return true;
        }

        // if adding didnt work try multiplying
        boolean multiply = tryOperations(nums, target, pos + 1, current * nums[pos + 1]);

        return multiply;
    }
}