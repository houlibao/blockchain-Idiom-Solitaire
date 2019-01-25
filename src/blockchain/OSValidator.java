package blockchain;

/* *
Check which operating system the program is running. 
The program explains itself.
*/

public class OSValidator {
	public static boolean isWindows() {
		String os = System.getProperty("os.name").toLowerCase();
		return (os.indexOf("win") >= 0);
	}

	public static boolean isMac() {
		String os = System.getProperty("os.name").toLowerCase();
		return (os.indexOf("mac") >= 0);
	}

	public static boolean isUnix() {
		String os = System.getProperty("os.name").toLowerCase();
		return (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0);
	}

	public static boolean isSolaris() {
		String os = System.getProperty("os.name").toLowerCase();
		return (os.indexOf("sunos") >= 0);
	}


	public static void main(String[] args) {
		System.out.println(isWindows());
		System.out.println(isMac());
		System.out.println(isUnix());
		System.out.println(isSolaris());
	}

}
