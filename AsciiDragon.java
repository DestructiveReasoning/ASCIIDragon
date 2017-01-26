public class AsciiDragon {

	public static final int RIGHT	= 0;
	public static final int UP		= 1;
	public static final int LEFT	= 2;
	public static final int DOWN	= 3;

	public static final int WIDTH	= 160;
	public static final int HEIGHT	= 50;

	public static void main(String [] args) {
		if(args.length < 1) {
			System.out.println("Usage: java AsciiDragon <number of iterations>");
			System.exit(1);
		}
		int n = Integer.parseInt(args[0]);
		if(n < 1) {
			System.out.println("Error: number of iterations must be greater than 1.");
			System.exit(2);
		}

		String dragon = buildDragon(n);
		System.out.println(dragon);
	}

	public static String buildDragon(int n) {
		String canvas[] = new String[HEIGHT];
		int dir = RIGHT;
		for(int c = 0; c < HEIGHT; c++) {
			for(int j = 0; j < WIDTH; j++) {
				canvas[c] += " ";
			}
		}
		int x, y;
		x = WIDTH/2;
		y = HEIGHT/2;
		String sequence = getSequence(n);
		System.out.println("Sequence: " + sequence);
		char[] tail = canvas[y].toCharArray();
		tail[x] = '_';
		tail[x+1] = '_';
		canvas[y] = String.valueOf(tail);
		x += 2;
		int count = 0;

		for(int c = 0; c < sequence.length(); c++) {
			if(sequence.charAt(c) == '1') dir = (dir + 1) % 4;
			else dir = (dir -1) % 4;
			int oldx = x;
			int oldy = y;
			char [] array;
			if(dir < 0) dir += 4;
			switch(dir) {
				case RIGHT:
					x += 3;
					oldx += 1; //Avoid crashing into yourself in a loop
					if(y < 0  || y >= HEIGHT) {
						System.out.println("[RIGHT]: y out of bounds at turn " + c);
						continue;
					}
					array = canvas[y].toCharArray();
					if(oldx >= WIDTH) {
						System.out.println("[RIGHT]: x out of bounds at turn " + c);
						continue;
					}
					array[oldx] = '_';
					if(oldx + 1 >= WIDTH) {
						System.out.println("[RIGHT]: x out of bounds at turn " + c);
						continue;
					}
					array[oldx+1] = '_';
					canvas[y] = String.valueOf(array);
					count++;
					break;
				case LEFT:
					x -= 3;
					if(y < 0 || y >= HEIGHT) {
						System.out.println("[LEFT]: y out of bounds at turn " + c);
						continue;
					}
					array = canvas[y].toCharArray();
					if(oldx - 1 < 0) {
						System.out.println("[LEFT]: x out of bounds at turn " + c);
						continue;
					}
					array[oldx-1] = '_';
					if(oldx - 2 < 0) {
						System.out.println("[LEFT]: x out of bounds at turn " + c);
						continue;
					}
					array[oldx-2] = '_';
					canvas[y] = String.valueOf(array);
					count++;
//					x -= 2;
//					if(y < 0 || y >= HEIGHT) continue;
//					array = canvas[y].toCharArray();
//					if(oldx < 0) continue;
//					array[oldx] = '_';
//					if(oldx - 1 < 0) continue;
//					array[oldx-1] = '_';
//					canvas[y] = String.valueOf(array);
					break;
				case UP:
					y -= 1;
					if(x < 0 || x >= WIDTH) {
						System.out.println("[UP]: x out of bounds at turn " + c);
						continue;
					}
					if(oldy < 0) {
						System.out.println("[UP]: y out of bounds at turn " + c);
						continue;
					}
					array = canvas[oldy].toCharArray();
					array[x] = '|';
					canvas[oldy] = String.valueOf(array);
					count++;
					break;
				case DOWN:
					y += 1;
					if(x < 0 || x >= WIDTH) {
						System.out.println("[DOWN]: x out of bounds at turn " + c);
						continue;
					}
					if(oldy >= HEIGHT) {
						System.out.println("[DOWN]: y out of bounds at turn " + c);
						continue;
					}
					array = canvas[oldy+1].toCharArray();
					array[x] = '|';
					canvas[oldy+1] = String.valueOf(array);
					count++;
					break;
				default:
					System.out.println("BAD DIRECTION AT TURN " + c);
					System.out.println("\tdir = " + dir);
					break;
			}
		}
		String dragon = "";
		for(int c = 0; c < HEIGHT; c++) {
			dragon += canvas[c] + "\n";
		}
		System.out.println("Count: " + count + ", Sequence Length: " + sequence.length());
		return dragon;
	}

	public static String getSequence(int n) {
		String s = "1";
		for(int c = 1; c < n; c++) {
			String tail = "";
			for(int j =0; j < s.length(); j++) {
				if(j == s.length()/2) tail += '0';
				else tail += s.charAt(j);
			}
			s = s + "1" + tail;
		}
		return s;
	}
}
