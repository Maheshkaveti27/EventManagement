package com.event.management.resource;

import java.io.FileOutputStream;
import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.event.management.entity.Event;
import com.event.management.factory.SerializedSingleton;
import com.event.management.service.EventService;

@Path("/files")
public class HelloResource {

	private static final String PDF_FILE = "C:\\Users\\VRSuramsetti\\Desktop\\test.docx";

	@GET
	@Path("/pdf")
	@Produces("application/json")
	public Response getPDF() throws IOException {
		XWPFDocument document= new XWPFDocument(); 

		EventService service = SerializedSingleton.getEventServiceInstance();
		/*
		 * AuthorizationToken authorizationToken =
		 * AuthenticationUtil.checkTokenValidOrNot(accessToken);
		 * if(authorizationToken == null) return
		 * Response.status(Response.Status.CREATED) .entity(new
		 * StatusResponse("Invalid token/Token expired")).build();
		 */

		Event event = service.getEventByEventId(1);
		 //Write the Document in file system
	      FileOutputStream out = new FileOutputStream(PDF_FILE);
	      XWPFParagraph paragraph = document.createParagraph();
	      XWPFRun paragraphOneRunOne = paragraph.createRun();
	      paragraphOneRunOne.setBold(true);
	      paragraphOneRunOne.setText("EAGLE EVENT PLANNING");
	      paragraphOneRunOne.setTextPosition(100);
	      paragraphOneRunOne.addBreak();
	      
	      XWPFRun custPara = paragraph.createRun();
	      custPara.setBold(true);
	      custPara.setText("Customer Name:"+event.getCustomer().getFirstName()+" "+event.getCustomer().getLastName());
	      custPara.setTextPosition(100);
	      custPara.addBreak();
	      
	      XWPFRun custNoPara = paragraph.createRun();
	      custPara.setBold(true);
	      custPara.setText("Customer NO:"+ event.getCustomerId());
	      custPara.setTextPosition(100);
	      custPara.addBreak();
	      
	      XWPFRun eventNamePara = paragraph.createRun();
	      eventNamePara.setBold(true);
	      eventNamePara.setText("Event Name: "+ event.getEventName());
	      eventNamePara.setTextPosition(100);
	      eventNamePara.addBreak();
	      
	      XWPFRun totalGuestsPara = paragraph.createRun();
	      totalGuestsPara.setBold(true);
	      totalGuestsPara.setText("Total Guests"+event.getNoOfGuests());
	      totalGuestsPara.setTextPosition(100);
	      totalGuestsPara.addBreak();
	      
	      XWPFRun costPara = paragraph.createRun();
	      costPara.setBold(true);
	      costPara.setText("Total Cost: $"+event.getNoOfGuests()*10);
	      costPara.setTextPosition(100);
	      costPara.addBreak();
	      
	      
				
	      document.write(out);
	      out.close();
	      System.out.println("createdocument.docx written successully");
		return null;
		}
}
