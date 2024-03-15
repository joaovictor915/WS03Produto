package service;

import java.util.Scanner;
import java.time.LocalDate;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import dao.ProductDAO;
import model.Product;
import spark.Request;
import spark.Response;


public class ProductService {

	private ProductDAO productDAO = new ProductDAO();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_ID = 1;
	private final int FORM_ORDERBY_DESCRIPTION = 2;
	private final int FORM_ORDERBY_PRICE = 3;
	
	
	public ProductService() {
		makeForm();
	}

	
	public void makeForm() {
		makeForm(FORM_INSERT, new Product(), FORM_ORDERBY_DESCRIPTION);
	}

	
	public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new Product(), orderBy);
	}

	
	public void makeForm(int type, Product product, int orderBy) {
		String fileName = "form.html";
		form = "";
		try{
			Scanner input = new Scanner(new File(fileName));
		    while(input.hasNext()){
		    	form += (input.nextLine() + "\n");
		    }
		    input.close();
		}  catch (Exception e) { System.out.println(e.getMessage()); }
		
		String aProduct = "";
		if(type != FORM_INSERT) {
			aProduct += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			aProduct += "\t\t<tr>";
			aProduct += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/product/list/1\">New Product</a></b></font></td>";
			aProduct += "\t\t</tr>";
			aProduct += "\t</table>";
			aProduct += "\t<br>";			
		}
		
		if(type == FORM_INSERT || type == FORM_UPDATE) {
			String action = "/product/";
			String name, description, buttonLabel;
			if (type == FORM_INSERT){
				action += "insert";
				name = "Insert Product";
				description = "milk, bread, ...";
				buttonLabel = "Insert";
			} else {
				action += "update/" + product.getID();
				name = "Update Product (ID " + product.getID() + ")";
				description = product.getDescription();
				buttonLabel = "Update";
			}
			aProduct += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
			aProduct += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			aProduct += "\t\t<tr>";
			aProduct += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + name + "</b></font></td>";
			aProduct += "\t\t</tr>";
			aProduct += "\t\t<tr>";
			aProduct += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			aProduct += "\t\t</tr>";
			aProduct += "\t\t<tr>";
			aProduct += "\t\t\t<td>&nbsp;Description: <input class=\"input--register\" type=\"text\" name=\"description\" value=\""+ description +"\"></td>";
			aProduct += "\t\t\t<td>Price: <input class=\"input--register\" type=\"text\" name=\"price\" value=\""+ product.getPrice() +"\"></td>";
			aProduct += "\t\t\t<td>Quantity: <input class=\"input--register\" type=\"text\" name=\"quantity\" value=\""+ product.getQuantity() +"\"></td>";
			aProduct += "\t\t</tr>";
			aProduct += "\t\t<tr>";
			aProduct += "\t\t\t<td>&nbsp;Manufacture Date: <input class=\"input--register\" type=\"text\" name=\"manufactureDate\" value=\""+ product.getManufactureDate().toString() + "\"></td>";
			aProduct += "\t\t\t<td>Expiration Date: <input class=\"input--register\" type=\"text\" name=\"expirationDate\" value=\""+ product.getExpirationDate().toString() + "\"></td>";
			aProduct += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\""+ buttonLabel +"\" class=\"input--main__style input--button\"></td>";
			aProduct += "\t\t</tr>";
			aProduct += "\t</table>";
			aProduct += "\t</form>";		
		} else if (type == FORM_DETAIL){
			aProduct += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			aProduct += "\t\t<tr>";
			aProduct += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Product Detail (ID " + product.getID() + ")</b></font></td>";
			aProduct += "\t\t</tr>";
			aProduct += "\t\t<tr>";
			aProduct += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			aProduct += "\t\t</tr>";
			aProduct += "\t\t<tr>";
			aProduct += "\t\t\t<td>&nbsp;Description: "+ product.getDescription() +"</td>";
			aProduct += "\t\t\t<td>Price: "+ product.getPrice() +"</td>";
			aProduct += "\t\t\t<td>Quantity: "+ product.getQuantity() +"</td>";
			aProduct += "\t\t</tr>";
			aProduct += "\t\t<tr>";
			aProduct += "\t\t\t<td>&nbsp;Manufacture Date: "+ product.getManufactureDate().toString() + "</td>";
			aProduct += "\t\t\t<td>Expiration Date: "+ product.getExpirationDate().toString() + "</td>";
			aProduct += "\t\t\t<td>&nbsp;</td>";
			aProduct += "\t\t</tr>";
			aProduct += "\t</table>";		
		} else {
			System.out.println("ERROR! Unidentified type " + type);
		}
		form = form.replaceFirst("<A-PRODUCT>", aProduct);
		
		String list = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
		list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Product List</b></font></td></tr>\n" +
				"\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
    			"\n<tr>\n" + 
        		"\t<td><a href=\"/product/list/" + FORM_ORDERBY_ID + "\"><b>ID</b></a></td>\n" +
        		"\t<td><a href=\"/product/list/" + FORM_ORDERBY_DESCRIPTION + "\"><b>Description</b></a></td>\n" +
        		"\t<td><a href=\"/product/list/" + FORM_ORDERBY_PRICE + "\"><b>Price</b></a></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Detail</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Update</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Delete</b></td>\n" +
        		"</tr>\n";
		
		List<Product> products;
		if (orderBy == FORM_ORDERBY_ID) {                 	products = productDAO.getOrderByID();
		} else if (orderBy == FORM_ORDERBY_DESCRIPTION) {		products = productDAO.getOrderByDescription();
		} else if (orderBy == FORM_ORDERBY_PRICE) {			products = productDAO.getOrderByPrice();
		} else {											products = productDAO.get();
		}

		int i = 0;
		String bgcolor = "";
		for (Product p : products) {
			bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
			list += "\n<tr bgcolor=\""+ bgcolor +"\">\n" + 
            		  "\t<td>" + p.getID() + "</td>\n" +
            		  "\t<td>" + p.getDescription() + "</td>\n" +
            		  "\t<td>" + p.getPrice() + "</td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/product/" + p.getID() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/product/update/" + p.getID() + "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"javascript:confirmDeleteProduct('" + p.getID() + "', '" + p.getDescription() + "', '" + p.getPrice() + "');\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "</tr>\n";
		}
		list += "</table>";		
		form = form.replaceFirst("<LIST-PRODUCT>", list);				
	}
	
	
	public Object insert(Request request, Response response) {
		String description = request.queryParams("description");
		float price = Float.parseFloat(request.queryParams("price"));
		int quantity = Integer.parseInt(request.queryParams("quantity"));
		LocalDateTime manufactureDate = LocalDateTime.parse(request.queryParams("manufactureDate"));
		LocalDate expirationDate = LocalDate.parse(request.queryParams("expirationDate"));
		
		String resp = "";
		
		Product product = new Product(-1, description, price, quantity, manufactureDate, expirationDate);
		
		if(productDAO.insert(product)) {
            resp = "Product (" + description + ") inserted!";
            response.status(201); // 201 Created
		} else {
			resp = "Product (" + description + ") not inserted!";
			response.status(404); // 404 Not found
		}
			
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Product product = productDAO.get(id);
		
		if (product != null) {
			response.status(200); // success
			makeForm(FORM_DETAIL, product, FORM_ORDERBY_DESCRIPTION);
        } else {
            response.status(404); // 404 Not found
            String resp = "Product " + id + " not found.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}

	
	public Object getToUpdate(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Product product = productDAO.get(id);
		
		if (product != null) {
			response.status(200); // success
			makeForm(FORM_UPDATE, product, FORM_ORDERBY_DESCRIPTION);
        } else {
            response.status(404); // 404 Not found
            String resp = "Product " + id + " not found.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}
	
	
	public Object getAll(Request request, Response response) {
		int orderBy = Integer.parseInt(request.params(":orderby"));
		makeForm(orderBy);
	    response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
		return form;
	}			
	
	public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
		Product product = productDAO.get(id);
        String resp = "";       

        if (product != null) {
        	product.setDescription(request.queryParams("description"));
        	product.setPrice(Float.parseFloat(request.queryParams("price")));
        	product.setQuantity(Integer.parseInt(request.queryParams("quantity")));
        	product.setManufactureDate(LocalDateTime.parse(request.queryParams("manufactureDate")));
        	product.setExpirationDate(LocalDate.parse(request.queryParams("expirationDate")));
        	productDAO.update(product);
        	response.status(200); // success
            resp = "Product (ID " + product.getID() + ") updated!";
        } else {
            response.status(404); // 404 Not found
            resp = "Product (ID \" + product.getId() + \") not found!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object delete(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Product product = productDAO.get(id);
        String resp = "";       

        if (product != null) {
            productDAO.delete(id);
            response.status(200); // success
            resp = "Product (" + id + ") deleted!";
        } else {
            response.status(404); // 404 Not found
            resp = "Product (" + id + ") not found!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
}
