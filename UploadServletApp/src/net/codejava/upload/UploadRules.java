package net.codejava.upload;
import java.io.*; 
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
 
/**
 * A Java servlet that handles file upload from client.
 * @author www.codejava.net
 */


public class UploadRules extends HttpServlet {
    private static final long serialVersionUID = 1L;
 
    private static final String UPLOAD_DIRECTORY = "C:/upload/rules";
    private static final int THRESHOLD_SIZE     = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
 
    /**
     * handles file upload via HTTP POST method
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        // checks if the request actually contains upload file
        if (!ServletFileUpload.isMultipartContent(request)) {
            PrintWriter writer = response.getWriter();
            writer.println("Request does not contain upload data");
            writer.flush();
            return;
        }
         
        // configures upload settings
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(THRESHOLD_SIZE);
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
         
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(MAX_FILE_SIZE);
        upload.setSizeMax(MAX_REQUEST_SIZE);
         
        // constructs the directory path to store upload file
        //String uploadPath = getServletContext().getRealPath("")
        //    + File.separator + UPLOAD_DIRECTORY;
        // creates the directory if it does not exist
        String uploadPath = UPLOAD_DIRECTORY;
        
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
         
        try {
            // parses the request's content to extract file data
        	
            List formItems = upload.parseRequest(request);
            Iterator iter = formItems.iterator();
            FileItem itemRealFileName = (FileItem) iter.next();
            String realFileName = itemRealFileName.getString();
            String fName = null; 
            //String realFileName = request.getParameter("fname");
            // iterates over form's fields
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                // processes only fields that are not form fields
                if (!item.isFormField()) {
                	
                    String fileName = new File(item.getName()).getName();
                    fName  = fileName;
                    String[] ext2 = fName.split("\\.");	
                    String fileExt = realFileName.split("\\.")[0];
                    
                    String filePath = uploadPath + File.separator + fileExt + "Rules.txt";
                    File storeFile = new File(filePath);
                     
                    // saves the file on disk
                    //request.setAttribute("message", "Upload has been done successfully!");
                    //request.setAttribute("text",ext2[ext2.length-1]);
                    String ext1 = ext2[ext2.length-1];
                    if(ext1.equals("txt"))
                    {
                    	item.write(storeFile);
                    	request.setAttribute("message", "Rules Upload has been done successfully!");
                        //request.setAttribute("text", realFileName);
                    	getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
                    	
                    	//boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
                    	String[] command = {"cmd.exe","/c","D:\\Python 35\\python3","C:/upload/pdftest.py","--file",realFileName,"--rules",fileExt + "Rules.txt"};
                    	try {

                            // print a message
                            System.out.println("Executing Command");
                            String line;
                            // create a process and execute notepad.exe
                            ProcessBuilder pb = new ProcessBuilder(command);
                            Process process = pb.start();
                            int errCode = process.waitFor();

                            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
                            // print another message
                            System.out.println("Command Ends");
                            while ((line = input.readLine()) != null) {
                                System.out.println(line);
                              }
                              input.close();

                         } catch (Exception ex) {
                            ex.printStackTrace();
                         }
                    	
                    }
                    
                    else
                    {
                    	request.setAttribute("message", "Invalid Format! Redirecting you in 5 Seconds");
                    	//request.setAttribute("text", "none");
                    	response.setHeader("Refresh", "5;url=upload.jsp");
                    	//getServletContext().getRequestDispatcher("/upload.jsp").forward(request, response);
                    	getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
                    }
                    
                    
                    
                    
                }
            }
            
            
            
        } catch (Exception ex) {
            request.setAttribute("message", "There was an error: " + ex.getMessage());
        }
        //getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
    }
}