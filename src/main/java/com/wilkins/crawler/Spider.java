package com.wilkins.crawler;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.*;

public class Spider  {
    private static final int MAX_PAGES_TO_SEARCH = 10000;



    private ConcurrentHashMap <Object, Object> pagesVisited1 = new ConcurrentHashMap<>();
    private Set pagesVisited = ConcurrentHashMap.newKeySet();
    //private List <String> pagesToVisit = new LinkedList <String>();
    private Queue<String> pagesToVisit = new ConcurrentLinkedQueue<>();
     private PrintWriter f0 = new PrintWriter(new FileWriter("/tmp/results.txt"));


    public Spider() throws IOException {
    }


    /**
     * Our main launching point for the Spider's functionality. Internally it creates spider legs
     * that make an HTTP request and parse the response (the web page).
     *
     * @param urlT       - The starting point of the spider
     * @param searchWord - The word or string that you are searching for
     *
     */

    public  void  search(String urlT, String searchWord) throws IOException {



               boolean success;

               try {


                while (this.pagesVisited.size() < MAX_PAGES_TO_SEARCH) {
                    String currentUrl;
                    SpiderLeg leg = new SpiderLeg();
                    if (this.pagesToVisit.isEmpty()) {
                        currentUrl = urlT;
                        //System.out.println( "currentURL is " + currentUrl );
                        if (urlT== null) {
                            System.out.println( "System halting at thread " + Thread.currentThread().getName() );
                           //System.exit( 255 );
                       }
                        this.pagesVisited.add( urlT );
                    } else {
                        currentUrl = this.nextUrl();
                    }
                    try {
                        leg.crawl( currentUrl ); // Lots of stuff happening here. Look at the crawl method in
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // SpiderLeg

                         success = leg.searchForWord( searchWord );

                    if (success) {


                            StringBuilder results = new StringBuilder( "**Success Word " ).append( String.valueOf( searchWord ) ).append( " found at  " ).append( String.valueOf( currentUrl ) );
                            System.out.println(results.toString());
                             synchronized (this) {
                                  f0.println( results.toString() );
                             }
                            break;

                    }else {
                        System.out.println("Adding all links");
                        this.pagesToVisit.addAll( leg.getLinks() );
                        return;
                    }
                }
                System.out.println( "\n**Done** Visited " + this.pagesVisited.size() + " web page(s)" );
                System.out.println("Thread: "+Thread.currentThread().getName());
                pagesVisited.clear() ;
                pagesToVisit.clear();



           } catch (final RejectedExecutionException e) {
               System.out.println( "Task Rejected" );
               //semaphore.release();
                throw e;
            }
            finally {

               }

    }


    /**
     * Returns the next URL to visit (in the order that they were found). We also do a check to make
     * sure this method doesn't return a URL that has already been visited.
     *
     * @return
     */
    private String nextUrl() {
        String nextUrl;
        do {
            nextUrl = String.valueOf( this.pagesToVisit.remove( 0 ) );
        } while (this.pagesVisited.contains( nextUrl ));
        this.pagesVisited.add( nextUrl );
        return nextUrl;
    }
}
