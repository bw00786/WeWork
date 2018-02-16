package com.wilkins.service;

import com.wilkins.crawler.Spider;
import com.wilkins.pool.TaskExecutor;
import com.wilkins.util.CommonUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Stream;

public class ProcessFile {


    private static final String searchPattern = "search";
    private static ThreadPool pool = new ThreadPool( 100, 100 );
    //private static ExecutorService pool = Executors.newCachedThreadPool();


    public void processFiles(String fileName) throws IOException, InterruptedException {

        Spider spider = new Spider();



        try (BufferedReader br = new BufferedReader( new FileReader( fileName ) )) {
            br.readLine();
            String line = null;
            while ((line = br.readLine()) != null ) {
                String[] lines = line.split( "," );
                String urlN = lines[1];

              //  System.out.println( "value of url is " + urlN );
                String newUri = urlN.replace( "\"", "" );
               // System.out.println( "newURI is " + newUri );

                pool.submitTask( new Runnable() {
                                     @Override
                                     public void run() {
                                         try {
                                             spider.search( newUri, searchPattern );
                                         } catch (IOException e) {
                                             e.printStackTrace();
                                         }
                                     }
                                 });
                                // pool.shutdown();
                                 //pool.awaitTermination( 1,TimeUnit.DAYS );




            }
        }
    }
}

