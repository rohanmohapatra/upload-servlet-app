package net.codejava.upload;
 
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
public class UploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
 
    private static final String UPLOAD_DIRECTORY = "C:/upload/pdf";
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
        //String uploadPath = getServletContext().getRealPath(UPLOAD_DIRECTORY);
        
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
         
        try {
            // parses the request's content to extract file data
            List formItems = upload.parseRequest(request);
            Iterator iter = formItems.iterator();
            String fName = null; 
            // iterates over form's fields
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                // processes only fields that are not form fields
                if (!item.isFormField()) {
                    String fileName = new File(item.getName()).getName();
                    fName  = fileName;
                    String filePath = uploadPath + File.separator + fileName;
                    File storeFile = new File(filePath);
                     
                    // saves the file on disk
                    
                    
                    
                    String[] ext2 = fName.split("\\.");	
                    //request.setAttribute("message", "Upload has been done successfully!");
                    //request.setAttribute("text",ext2[ext2.length-1]);
                    String fileExt = ext2[ext2.length-1];
                    if(fileExt.equals("txt") | fileExt.equals("csv") | fileExt.equals("pdf") | fileExt.equals("docx") | fileExt.equals("doc") | fileExt.equals("pptx"))
                    {
                    	item.write(storeFile);
                    	request.setAttribute("message", "Upload has been done successfully!");
                        request.setAttribute("text", "block");
                        request.setAttribute("dis", "disabled");
                        request.setAttribute("fileName", fName);
                        
                    }
                    
                    else
                    {
                    	request.setAttribute("message", "Invalid Format! Redirecting you in 5 Seconds");
                    	request.setAttribute("text", "");
                    	//request.setAttribute("disable", "disabled");
                    	response.setHeader("Refresh", "5;url=upload.jsp");
                    	//getServletContext().getRequestDispatcher("/upload.jsp").forward(request, response);
                    }
                    
                    
                    
                    
                }
            }
            
            
            
        } catch (Exception ex) {
            request.setAttribute("message", "There was an error: " + ex.getMessage());
        }
        getServletContext().getRequestDispatcher("/upload.jsp").forward(request, response);
    }
}