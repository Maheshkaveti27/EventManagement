package com.event.management.resource;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import com.event.management.DAO.AuthorizationToken;
import com.event.management.entity.Event;
import com.event.management.entity.EventGuest;
import com.event.management.factory.SerializedSingleton;
import com.event.management.model.StatusResponse;
import com.event.management.service.EventService;
import com.event.management.service.GuestService;
import com.event.management.util.AuthenticationUtil;

@Path("/reports")
public class ReportResource {

	private static final String EVENT_DETAILS_FILE = "C:\\Users\\theza\\Desktop\\reports\\event_details.docx";
	private static final String EVENT_DETAILS_BY_GUEST_SEATING = "C:\\Users\\theza\\Desktop\\reports\\event_guest_details.docx";
	private static final String EVENT_DETAILS_BY_TABLE_SEATING = "C:\\Users\\theza\\Desktop\\reports\\event_guest_table_details.docx";

	@GET
	@Path("{eventid}")
	@Produces("application/json")
	public Response geteventDetails(@PathParam("eventid") int eventId, @HeaderParam("auth_token") String accessToken)
			throws IOException {
		XWPFDocument document = new XWPFDocument();

		EventService service = SerializedSingleton.getEventServiceInstance();
		AuthorizationToken authorizationToken = AuthenticationUtil.checkTokenValidOrNot(accessToken);
		if (authorizationToken == null)
			return Response.status(Response.Status.UNAUTHORIZED).entity(new StatusResponse("Invalid token/Token expired"))
					.build();

		Event event = service.getEventByEventId(1);
		// Write the Document in file system
		FileOutputStream out = new FileOutputStream(EVENT_DETAILS_FILE);
		XWPFParagraph paragraph = document.createParagraph();
		XWPFParagraph paragraph1 = document.createParagraph();
		paragraph.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun paragraphOneRunOne = paragraph.createRun();
		paragraphOneRunOne.setBold(true);
		paragraphOneRunOne.setTextPosition(20);
		paragraphOneRunOne.setText("EAGLE EVENT PLANNING");
		paragraphOneRunOne.setTextPosition(20);
		paragraphOneRunOne.addBreak();

		XWPFRun custPara = paragraph1.createRun();
		custPara.setText("Customer Name:  " + event.getCustomer().getFirstName() + " "
				+ event.getCustomer().getLastName());
		paragraphOneRunOne.setTextPosition(10);
		custPara.addBreak();

		XWPFRun custNoPara = paragraph1.createRun();
		custPara.setText("Customer NO:       " + event.getCustomerId());
		paragraphOneRunOne.setTextPosition(10);
		custPara.addBreak();

		XWPFRun eventNamePara = paragraph1.createRun();
		eventNamePara.setText("Event Name:          " + event.getEventName());
		paragraphOneRunOne.setTextPosition(10);
		eventNamePara.addBreak();

		XWPFRun totalGuestsPara = paragraph1.createRun();
		totalGuestsPara.setText("Total Guests:          " + event.getNoOfGuests());
		paragraphOneRunOne.setTextPosition(10);
		totalGuestsPara.addBreak();

		XWPFRun costPara = paragraph1.createRun();
		costPara.setText("Total Cost:              $" + event.getNoOfGuests() * 10);
		paragraphOneRunOne.setTextPosition(10);
		costPara.addBreak();

		document.write(out);
		out.close();
		System.out.println("createdocument.docx written successully");
		return null;
	}

	@GET
	@Path("{eventid}/guestsitting")
	@Produces("application/json")
	public Response prepareEventGuestSitting(@PathParam("eventid") int eventId,
			@HeaderParam("auth_token") String accessToken) throws IOException {
		XWPFDocument document = new XWPFDocument();

		EventService service = SerializedSingleton.getEventServiceInstance();
		AuthorizationToken authorizationToken = AuthenticationUtil.checkTokenValidOrNot(accessToken);
		if (authorizationToken == null)
			return Response.status(Response.Status.UNAUTHORIZED).entity(new StatusResponse("Invalid token/Token expired"))
					.build();

		Event event = service.getEventByEventId(eventId);
		// Write the Document in file system
		FileOutputStream out = new FileOutputStream(EVENT_DETAILS_BY_GUEST_SEATING);

		XWPFParagraph paragraph1 = document.createParagraph();
		XWPFRun paragraphOneRunOne = paragraph1.createRun();
		XWPFRun paragraphOneRunOne1 = paragraph1.createRun();
		paragraphOneRunOne1.setBold(true);
		paragraphOneRunOne1.setTextPosition(20);
		paragraphOneRunOne1.setText("EAGLE EVENT PLANNING");
		paragraphOneRunOne1.setTextPosition(20);
		paragraphOneRunOne1.addBreak();

		XWPFRun eventNamePara = paragraph1.createRun();
		eventNamePara.setText("Event Name:          " + event.getEventName());
		paragraphOneRunOne.setTextPosition(10);
		eventNamePara.addBreak();
		// create table
		XWPFTable table = document.createTable();

		// create first row
		XWPFTableRow tableRowOne = table.getRow(0);
		tableRowOne.getCell(0).setText("Guest Name");
		tableRowOne.addNewTableCell().setText("Guest Id");
		tableRowOne.addNewTableCell().setText("Table No");
		tableRowOne.addNewTableCell().setText("Same Table");
		tableRowOne.addNewTableCell().setText("Not Same Table");
		List<EventGuest> eventGuests = new ArrayList(event.getEventGuests());
		for (EventGuest guest : eventGuests) {
			XWPFTableRow tableRowTwo = table.createRow();
			tableRowTwo.getCell(0).setText(guest.getGuestName());
			tableRowTwo.getCell(1).setText(String.valueOf(guest.getGuestId()));
			tableRowTwo.getCell(2).setText(guest.getAllottedTableNo());
			tableRowTwo.getCell(3).setText(guest.getSameTable());
			tableRowTwo.getCell(4).setText(guest.getNotSameTable());
		}

		for (int x = 0; x < table.getNumberOfRows(); x++) {
			XWPFTableRow row = table.getRow(x);
			int numberOfCell = row.getTableCells().size();
			for (int y = 0; y < numberOfCell; y++) {
				XWPFTableCell cell = row.getCell(y);

				cell.getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(2000));
			}
		}

		document.write(out);
		out.close();
		System.out.println("createdocument.docx written successully");
		return null;
	}

	@GET
	@Path("{eventid}/tablesitting/{eventname}")
	@Produces("application/json")
	public Response prepareEventTableSitting(@PathParam("eventid") int eventId,
			@HeaderParam("auth_token") String accessToken, @PathParam("eventname") String eventName) throws IOException {
		XWPFDocument document = new XWPFDocument();

		GuestService service = SerializedSingleton.getGuestServiceInstance();

		AuthorizationToken authorizationToken = AuthenticationUtil.checkTokenValidOrNot(accessToken);
		if (authorizationToken == null)
			return Response.status(Response.Status.CREATED).entity(new StatusResponse("Invalid token/Token expired"))
					.build();

		Map<String, List<EventGuest>> guestTableDetails = service.getEventTablesData(eventId);
		// Write the Document in file system
		FileOutputStream out = new FileOutputStream(EVENT_DETAILS_BY_TABLE_SEATING);

		XWPFParagraph paragraph1 = document.createParagraph();
		XWPFRun paragraphOneRunOne = paragraph1.createRun();
		XWPFRun paragraphOneRunOne1 = paragraph1.createRun();
		paragraphOneRunOne1.setBold(true);
		paragraphOneRunOne1.setTextPosition(20);
		paragraphOneRunOne1.setText("EAGLE EVENT PLANNING");
		paragraphOneRunOne1.setTextPosition(20);
		paragraphOneRunOne1.addBreak();

		XWPFRun eventNamePara = paragraph1.createRun();
		eventNamePara.setText("Event Name:          " + eventName);
		paragraphOneRunOne.setTextPosition(10);
		eventNamePara.addBreak();
		// create table
		XWPFTable table = document.createTable();

		// create first row
		XWPFTableRow tableRowOne = table.getRow(0);
		tableRowOne.getCell(0).setText("Table No");
		tableRowOne.addNewTableCell().setText("Guest Id");
		for (String key : guestTableDetails.keySet()) {
			List<EventGuest> eventGuests = guestTableDetails.get(key);
			String tableId = key;
			StringBuilder guestIds = new StringBuilder("");
			for (EventGuest guest : eventGuests) {
				guestIds.append(guest.getGuestId()).append(",");
			}
			guestIds.deleteCharAt(guestIds.length() - 1);

			XWPFTableRow tableRowTwo = table.createRow();
			tableRowTwo.getCell(0).setText(key);
			tableRowTwo.getCell(1).setText(guestIds.toString());
		}

		for (int x = 0; x < table.getNumberOfRows(); x++) {
			XWPFTableRow row = table.getRow(x);
			int numberOfCell = row.getTableCells().size();
			for (int y = 0; y < numberOfCell; y++) {
				XWPFTableCell cell = row.getCell(y);

				cell.getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(2000));
			}
		}

		document.write(out);
		out.close();
		System.out.println("createdocument.docx written successully");
		return null;
	}
}