package com.wilkins.util;

import com.oracle.tools.packager.Log;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class CommonUtils {
/**
 * Download a file from url
 *
 * @param url - File to be downloaded
 * @return name of file, it download fails return null
 */
public  String downloadFile(String url)
{
	final String urlF = url;
	String returnThread;
	try
	{
		String[] urlPart = urlF.split("/");
		String fileName = urlPart[urlPart.length - 1];
		URL link = new URL(urlF);
        org.apache.commons.io.FileUtils.copyURLToFile(link, new File("/tmp/" + fileName));
		//org.apache.commons.io.Log.info("Download Finished");
		returnThread = "/tmp/" + fileName;
	}
	catch (IOException e)
	{
		//Log.error("Download Error");
		//Log.stackTrace(e.getStackTrace());
		returnThread = null;
	}
	return returnThread;
}


}
