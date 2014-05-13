import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

public class AClassLoader {

	public static final String WEB_ROOT = System.getProperty("user.dir")
			+ File.separator + "webroot";

	
	public static void main(String[] args) {
		URLClassLoader loader = null;
		URL[] urls = new URL[1];
		URLStreamHandler streamHandler = null;
		File classPath = new File(WEB_ROOT);
		String repository;
		System.out.println(WEB_ROOT);
		System.out.println(System.getProperty("user.dir"));
		
		try {
			repository = (new URL("file", null, classPath.getCanonicalPath()
					+ File.separator)).toString();
			System.out.println(classPath.getCanonicalPath());
			urls[0] = new URL(null, repository, streamHandler);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		loader = new URLClassLoader(urls);
	}
}
