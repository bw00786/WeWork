package com.wilkins.service;

import com.wilkins.crawler.Spider;

import java.io.*;

public class ProcessFile {


    private static final String searchPattern = "search";
    private static ThreadPool pool;

    static {
        try {
            pool = new ThreadPool( 1000, 100 );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
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
                                     ;
                                     @Override
                                     public void run() {
                                         try {
                                             String name = Thread.currentThread().getName();
                                             System.out.println("Task Started by Thread :" + name);
                                             spider.search( newUri, searchPattern );
                                             String name2 = Thread.currentThread().getName();
                                             System.out.println("Task Completed by Thread :" + name);
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

