package com.aric.myfm.domain.util;


import java.io.Closeable;
import java.io.File;
import java.io.IOException;

import org.jclouds.ContextBuilder;
import org.jclouds.blobstore.BlobStore;
import org.jclouds.blobstore.BlobStoreContext;
import org.jclouds.blobstore.domain.Blob;
import org.jclouds.http.HttpRequest;
import org.jclouds.http.HttpResponse;
import org.jclouds.http.HttpResponseException;
import org.jclouds.util.Strings2;

import com.google.common.base.Charsets;
import com.google.common.io.Closeables;
import com.google.common.io.Files;

/**
 * The Temporary URL feature (TempURL) allows you to create limited-time Internet addresses which allow you to grant 
 * limited access to your Cloud Files account. Using TempURL, you may allow others to retrieve or place objects in 
 * your Cloud Files account for as long or as short a time as you wish. Access to the TempURL is independent of 
 * whether or not your account is CDN-enabled. And even if you don't CDN-enable a directory, you can still grant 
 * temporary public access through a TempURL.
 * 
 * This feature is useful if you want to allow a limited audience to download a file from your Cloud Files account or
 * website. You can give out the TempURL and know that after a specified time, no one will be able to access your 
 * object through the address. Or, if you want to allow your audience to upload objects into your Cloud Files account, 
 * you can give them a TempURL. After the specified time expires, no one will be able to upload to the address.
 * 
 * Additionally, you need not worry about time running out when someone downloads a large object. If the time expires 
 * while a file is being retrieved, the download will continue until it is finished. Only the link will expire.
 *  
 * @author Everett Toews
 */
public class GenerateTempURL implements Closeable {
   private static final String FILENAME = "object.txt";
   private static final int TEN_MINUTES = 10 * 60;
   
   public static final String PROVIDER = System.getProperty("provider.cf", "cloudfiles-us");
   public static final String ZONE = System.getProperty("zone", "IAD");

   public static final String CONTAINER_PUBLISH = "jclouds-example-publish";
   public static final String CONTAINER = "jclouds-example";
   public static final String SUFFIX = ".html";
   
   private final BlobStore blobStore;
   private final BlobStoreContext blobStoreContext;

   /**
    * To get a username and API key see http://www.jclouds.org/documentation/quickstart/rackspace/
    * 
    * The first argument (args[0]) must be your username
    * The second argument (args[1]) must be your API key
    */
   public static void main(String[] args) throws IOException {
      GenerateTempURL generateTempURL = new GenerateTempURL(args[0], args[1]);

      try {
         generateTempURL.generatePutTempURL();
         generateTempURL.generateGetTempURL();
         generateTempURL.generateDeleteTempURL();
      }
      catch (Exception e) {
         e.printStackTrace();
      }
      finally {
         generateTempURL.close();
      }
   }

   public GenerateTempURL(String username, String apiKey) {
      blobStoreContext = ContextBuilder.newBuilder(PROVIDER)
            .credentials(username, apiKey)
            .buildView(BlobStoreContext.class);
      blobStore = blobStoreContext.getBlobStore();
   }
   
   private void generatePutTempURL() throws IOException {
      System.out.format("Generate PUT Temp URL%n");

      String payload = "This object will be public for 10 minutes.";
      Blob blob = blobStore.blobBuilder(FILENAME).payload(payload).contentType("text/plain").build();
      HttpRequest request = blobStoreContext.getSigner().signPutBlob(CONTAINER, blob, TEN_MINUTES);
      
      System.out.format("  %s %s", request.getMethod(), request.getEndpoint());
      
      // PUT the file using jclouds
      HttpResponse response = blobStoreContext.utils().http().invoke(request);
      int statusCode = response.getStatusCode();
      
      if (statusCode >= 200 && statusCode < 299) {
         System.out.format("  PUT Success (%s)%n", statusCode);
      }
      else {
         throw new HttpResponseException(null, response);
      }
   }

   private void generateGetTempURL() throws IOException {
      System.out.format("Generate GET Temp URL%n");
      
      HttpRequest request = blobStoreContext.getSigner().signGetBlob(CONTAINER, FILENAME, TEN_MINUTES);
      
      System.out.format("  %s %s", request.getMethod(), request.getEndpoint());
      
      // GET the file using jclouds
      File file = File.createTempFile(FILENAME, ".tmp");
      String content = Strings2.toString(blobStoreContext.utils().http().invoke(request).getPayload());
      Files.write(content, file, Charsets.UTF_8);
      
      System.out.format("  GET Success (%s)%n", file.getAbsolutePath());
   }

   private void generateDeleteTempURL() throws IOException {
      System.out.format("Generate DELETE Temp URL%n");
      
      HttpRequest request = blobStoreContext.getSigner().signRemoveBlob(CONTAINER, FILENAME);
      
      System.out.format("  %s %s", request.getMethod(), request.getEndpoint());
      
      // DELETE the file using jclouds
      HttpResponse response = blobStoreContext.utils().http().invoke(request);
      int statusCode = response.getStatusCode();
      
      if (statusCode >= 200 && statusCode < 299) {
         System.out.format("  DELETE Success (%s)", statusCode);
      }
      else {
         throw new HttpResponseException(null, response);
      }
   }

   /**
    * Always close your service when you're done with it.
    */
   public void close() throws IOException {
      Closeables.close(blobStore.getContext(), true);
   }
}