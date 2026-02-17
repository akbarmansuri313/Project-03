package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.annotation.JacksonInject.Value;
import com.google.inject.Key;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.TransportationDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.TransportationModelHibImpl;
import in.co.rays.project_3.model.TransportationModelHibInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "TransportationCtl", urlPatterns = { "/ctl/TransportationCtl" })
public class TransportationCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(TransportationCtl.class);

@Override
protected void preload(HttpServletRequest request) {
	
	
	HashMap map = new HashMap();
	
	map.put("Bus", "Bus");
	map.put("Train", "Train");
	map.put("Flight", "Flight");
	map.put("Bike", "Bike");
	map.put("Truck", "Truck");
	
	request.setAttribute("map", map);
}

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("description"))) {
			request.setAttribute("description", PropertyReader.getValue("error.require", "Description"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("mode"))) {
			request.setAttribute("Mode", PropertyReader.getValue("error.require", "Mode"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("orderDate"))) {
			request.setAttribute("orderDate", PropertyReader.getValue("error.require", "Order Date"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("orderDate"))) {
			request.setAttribute("orderDate", PropertyReader.getValue("error.date", "Order Date"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("cost"))) {
			request.setAttribute("Cost", PropertyReader.getValue("error.require", "Cost"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		TransportationDTO dto = new TransportationDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setDescription(DataUtility.getString(request.getParameter("Description")));
		
	
		dto.setMode(DataUtility.getString(request.getParameter("Mode")));
		dto.setOrderDate(DataUtility.getDate(request.getParameter("orderDate")));
		dto.setCost(DataUtility.getString(request.getParameter("Cost")));

		populateBean(dto, request);

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("TransportationCtl doGet Start");

		String op = DataUtility.getString(request.getParameter("operation"));
		TransportationModelHibInt model = ModelFactory.getInstance().getTransportationModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0 || op != null) {

			TransportationDTO dto;
			try {
				dto = model.findByPK(id);
				ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {
				ServletUtility.setErrorMessage(e.getMessage(), request);

				ServletUtility.forward(getView(), request, response);
				return;
			}
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("TransportationCtl doPost Start");

		String op = DataUtility.getString(request.getParameter("operation"));
		TransportationModelHibInt model = ModelFactory.getInstance().getTransportationModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			TransportationDTO dto = (TransportationDTO) populateDTO(request);

			try {
				if (id > 0) {
					model.update(dto);
					ServletUtility.setSuccessMessage("Data is successfully Updated", request);
				} else {
					model.add(dto);
					ServletUtility.setSuccessMessage("Data is successfully saved", request);
				}

				ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.setErrorMessage(e.getMessage(), request);

				ServletUtility.forward(getView(), request, response);
				return;
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			TransportationDTO dto = (TransportationDTO) populateDTO(request);

			model.delete(dto);
			ServletUtility.redirect(ORSView.TRANSPORTATION_LIST_CTL, request, response);
			return;

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.TRANSPORTATION_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.TRANSPORTATION_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);

		log.debug("TransportationCtl doPost End");
	}

	@Override
	protected String getView() {
		return ORSView.TRANSPORTATION_VIEW;
	}
}
