package com.wilkins.main;

import com.wilkins.crawler.Spider;
import com.wilkins.service.ProcessFile;
import com.wilkins.util.CommonUtils;

import java.io.IOException;
import java.util.concurrent.Semaphore;

public class Application {


    private static final String webUrl = "https://s3.amazonaws.com/fieldlens-public/urls.txt";
    private static CommonUtils common = new CommonUtils();
    private static ProcessFile processFile = new ProcessFile();






    public static void main(String[] args) throws IOException, InterruptedException {
        String fileName = common.downloadFile( webUrl );

        System.out.println("File name is "+fileName);
        processFile.processFiles( fileName );


    }
}
