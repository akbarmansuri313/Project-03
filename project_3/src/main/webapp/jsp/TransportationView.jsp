<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.TransportationCtl"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Transportation View</title>
<meta name="viewport" content="width=device-width, initial-scale=1">

<style type="text/css">
i.css {
	border: 2px solid #8080803b;
	padding-left: 10px;
	padding-bottom: 11px;
	background-color: #ebebe0;
}

.input-group-addon {
	box-shadow: 9px 8px 7px #001a33;
}

.hm {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/user1.jpg');
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-size: cover;
	padding-top: 75px;
	background-size: 100%;
}
</style>

</head>

<body class="hm">

	<div class="header">
		<%@include file="Header.jsp"%>
		<%@include file="calendar.jsp"%>
	</div>

	<div>
		<main>

		<form action="<%=ORSView.TRANSPORTATION_CTL%>" method="post">

			<jsp:useBean id="dto"
				class="in.co.rays.project_3.dto.TransportationDTO" scope="request"></jsp:useBean>

			<div class="row pt-3">
				<div class="col-md-4 mb-4"></div>

				<div class="col-md-4 mb-4">
					<div class="card input-group-addon">
						<div class="card-body">

							<%
								long id = DataUtility.getLong(request.getParameter("id"));
							
								if (id > 0) {
							%>
							<h3 class="text-center text-primary">Update Transportation</h3>
							<%
								} else {
							%>
							<h3 class="text-center text-primary">Add Transportation</h3>
							<%
								}
							%>
							
							<%
							HashMap map = (HashMap) request.getAttribute("map");
							
							%>

							<!-- Success Message -->
							<h4 align="center">
								<%
									if (!ServletUtility.getSuccessMessage(request).equals("")) {
								%>
								<div class="alert alert-success alert-dismissible">
									<button type="button" class="close" data-dismiss="alert">&times;</button>
									<%=ServletUtility.getSuccessMessage(request)%>
								</div>
								<%
									}
								%>
							</h4>

							<!-- Error Message -->
							<h4 align="center">
								<%
									if (!ServletUtility.getErrorMessage(request).equals("")) {
								%>
								<div class="alert alert-danger alert-dismissible">
									<button type="button" class="close" data-dismiss="alert">&times;</button>
									<%=ServletUtility.getErrorMessage(request)%>
								</div>
								<%
									}
								%>
							</h4>

							<input type="hidden" name="id" value="<%=dto.getId()%>">
							<input type="hidden" name="createdBy"
								value="<%=dto.getCreatedBy()%>"> <input type="hidden"
								name="modifiedBy" value="<%=dto.getModifiedBy()%>"> <input
								type="hidden" name="createdDatetime"
								value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
							<input type="hidden" name="modifiedDatetime"
								value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">

							<!-- Description -->
							<span class="pl-sm-5"><b>Description</b><span
								style="color: red;">*</span></span>
							<div class="col-sm-12">
								<div class="input-group">
									<div class="input-group-prepend">
										<div class="input-group-text">
											<i class="fa fa-file-text grey-text"></i>
										</div>
									</div>
									<input type="text" class="form-control" name="description"
										placeholder="Enter Description"
										value="<%=DataUtility.getStringData(dto.getDescription())%>">
								</div>
							</div>
							<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("description", request)%>
							</font><br>

							<!-- Mode -->
							<span class="pl-sm-5"><b>Mode</b><span style="color: red;">*</span></span>
							<div class="col-sm-12">
								<div class="input-group">
									<div class="input-group-prepend">
										<div class="input-group-text">
											<i class="fa fa-bus grey-text"></i>
										</div>
									</div>
									<%=HTMLUtility.getList("Mode", dto.getMode(), map)%>
								</div>
							</div>
							<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("mode", request)%>
							</font><br>

							<!-- Order Date -->
							<span class="pl-sm-5"><b>Order Date</b><span
								style="color: red;">*</span></span>
							<div class="col-sm-12">
								<div class="input-group">
									<div class="input-group-prepend">
										<div class="input-group-text">
											<i class="fa fa-calendar grey-text"></i>
										</div>
									</div>
									<input type="text" id="datepicker2" name="orderDate"
										class="form-control" placeholder="Select Order Date" readonly
										value="<%=DataUtility.getDateString(dto.getOrderDate())%>">
								</div>
							</div>
							<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("orderDate", request)%>
							</font><br>

							<!-- Cost -->
							<span class="pl-sm-5"><b>Cost</b><span style="color: red;">*</span></span>
							<div class="col-sm-12">
								<div class="input-group">
									<div class="input-group-prepend">
										<div class="input-group-text">
											<i class="fa fa-money grey-text"></i>
										</div>
									</div>
									<input type="text" class="form-control" name="cost"
										placeholder="Enter Cost"
										value="<%=DataUtility.getStringData(dto.getCost())%>">
								</div>
							</div>
							<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("Cost", request)%>
							</font><br>

							<!-- Buttons -->
							<div class="text-center">
								<%
									if (dto.getId() != null && dto.getId() > 0) {
								%>
								<input type="submit" name="operation"
									class="btn btn-success btn-md"
									value="<%=TransportationCtl.OP_UPDATE%>"> <input
									type="submit" name="operation" class="btn btn-warning btn-md"
									value="<%=TransportationCtl.OP_CANCEL%>">
								<%
									} else {
								%>
								<input type="submit" name="operation"
									class="btn btn-success btn-md"
									value="<%=TransportationCtl.OP_SAVE%>"> <input
									type="submit" name="operation" class="btn btn-warning btn-md"
									value="<%=TransportationCtl.OP_RESET%>">
								<%
									}
								%>
							</div>

						</div>
					</div>
				</div>

				<div class="col-md-4 mb-4"></div>
			</div>

		</form>
		</main>
	</div>

</body>

<%@include file="FooterView.jsp"%>
</html>
